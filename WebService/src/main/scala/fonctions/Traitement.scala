package fonctions

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.catalyst.expressions.Row
import org.apache.spark.{sql, SparkContext, SparkConf}

class Traitement {
  val conf = new SparkConf().setAppName("test").setMaster("local")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  /*
  Fonction qui traite la demande de la liste des attributs d'un fichier
  @args :
  file: String      -> nom du fichier
  @returns: String  -> liste des atributs séparés par des virgules
  */
  def listeAttributs(file: String):String = {
    var ret = "";
    val extension = Fichier.extension(file)

    if ( extension=="csv" || extension=="tsv" ) { //Si c'est un csv ou tsv
      ret = listeAttributsCsvTsv(file)
    }
    else if (extension == "json") { //Si c'est un json
      ret = listeAttributsJson(file)
    }
    else
      throw new Exception()

    return ret;
  }

  /*
  Fonction qui retourne la liste des attributs d'un csv/tsv
  @args :
  file: String      -> nom du fichier
  @returns: String  -> liste des atributs séparés par des virgules
  */
  private def listeAttributsCsvTsv(file: String): String = {
    val textfile = sc.textFile("scripts/" + file);
    val sep = Csv.separateur(textfile)
    return textfile
      .map(r => r.split(sep))
      .first()
      .mkString(",")
  }

  /*
  Fonction qui retourne la liste des attributs d'un json
  @args :
  file: String      -> nom du fichier
  @returns: String  -> liste des atributs séparés par des virgules
  */
  private def listeAttributsJson(file: String): String = {
    val textFile = sqlContext.jsonFile("scripts/" + file)
    var tabR = new Array[String](0)
    textFile.schema.fieldNames.foreach(r => tabR :+= r) //On récupère les attributs
    return tabR.mkString(",") //On les associe avec un separateur
  }

  /*
  Fonction qui traite la demande de statistiques de l'attribut d'un fichier
  @args :
  cheminSource: String    -> chemin du repertoire du fichier lu
  cheminCible: String     -> chemin du repertoire du fichier csv généré
  nomFichier: String      -> nom du fichier
  attribut: String        -> attribut sur lequel les statistiques sont générées
  segment: Int            -> le nombre de segment demandé
  filtre: String          -> les filtres sur le fichier
  @returns: Array[String] -> le nom du fichier généré
                             le nombre de lignes
                             les stats (min,max,moyenne) (si colonne de valeurs numeriques)
  */
  def traitementStats(cheminSource: String, cheminCible: String, nomFichier: String, attribut: String, segment: Int, filtre: String): Array[String] = {
    var tab = new Array[(String, Int)](0)
    var stats = ""
    var nbRow:Long = 0
    var moyenneSegment = ""
    var mediane = ""

    if (Fichier.extension(nomFichier)=="csv" || Fichier.extension(nomFichier)=="tsv") { //Si c'est un csv ou tsv...
      val textFile = sc.textFile(cheminSource + nomFichier) //On charge le fichier avec spark, le type de textFile est RDD[Array[String]]
      val sep = Csv.separateur(textFile) //On récupère le séparateur du csv/tsv

      var data = textFile.map(_.split(sep)) //On crée un RDD[Array[String]] qui correspond, en fonction du separateur
      val col = Colonne.indiceAttribut(data, attribut) //On récupère le numero de colonne

      if (!filtre.equals("")) data = Filtre.filtreCSV(data, filtre)
      else {
        val header = data.first()
        data = data.filter(line => !line.apply(0).equals(header.apply(0)))
      }

      nbRow = data.count()
      mediane = Statistiques.mediane(data.map(r => r(col)))

      if (Colonne.numerique(data,col)) {
        tab = StatsAttribut.numerique(segment, col, data) // <- Si c'est un Réel on execute segmentNum
        stats = Statistiques.otherStats(data, col)
        moyenneSegment = Statistiques.StringMoyenneSegment(Statistiques.moyenneSegment(segment, col, data))
      }
      else
        tab = StatsAttribut.chaine(segment,col,data) // <- Si c'est un String on execute segmentStringArray
    }
    else if (Fichier.extension(nomFichier)=="json") {
      val textFile = sqlContext.jsonFile(cheminSource + nomFichier)//On charge le fichier avec spark, le type de textFile est SchemaRDD
      textFile.registerTempTable("textFile") //On enregistre le SchemaRDD comme une table SQL

      val filtreTable = if(filtre.equals("")) "" else Filtre.filtreJson(sqlContext, textFile, filtre)

      nbRow = Statistiques.nbTuples(sqlContext, "textFile", filtreTable)

      if (Colonne.numerique(sqlContext, textFile, attribut)) {
        tab = StatsAttribut.numerique(sqlContext, segment, attribut, "textFile", filtreTable) // <- Si c'est un Réel on execute segmentNum
        stats = Statistiques.otherStats(sqlContext, "textFile", attribut, filtreTable)
      }
      else
        tab = StatsAttribut.chaine(sqlContext,segment,attribut,"textFile", filtreTable) // <- Si c'est un String on execute segmentStringArray
    }
    else
      throw new Exception()
    val nomFichierStats = Csv.creerStats(nomFichier + "_" + attribut, cheminCible, tab) //On crée le fichier CSV à renvoyer à la webApp

    return Array[String](nomFichierStats, nbRow.toString, stats, moyenneSegment, mediane)
  }

  def traitementGraphe(cheminSource: String, cheminCible: String, nomFichier: String, attribut1: String, attribut2: String, filtre: String): Array[String] = {
    var tab = new Array[(String, (Double, Double, Double))](0)
    var stats = ""
    var nbRow:Long = 0
    var mediane = ""

    if (Fichier.extension(nomFichier)=="csv" || Fichier.extension(nomFichier)=="tsv") {
      val textFile = sc.textFile(cheminSource + nomFichier) //On charge le fichier avec spark, le type de textFile est RDD[Array[String]]
      val sep = Csv.separateur(textFile) //On récupère le séparateur du csv/tsv
      var data = textFile.map(_.split(sep)) //On crée un RDD[Array[String]] qui correspond, en fonction du separateur
      val col1 = Colonne.indiceAttribut(data, attribut1) //On récupère le numero de colonne
      val col2 = Colonne.indiceAttribut(data, attribut2) //On récupère le numero de colonne

      if (!filtre.equals("")) data = Filtre.filtreCSV(data, filtre)
      else {
        val header = data.first()
        data = data.filter(line => !line.apply(0).equals(header.apply(0)))
      }

      nbRow = data.count()
      mediane = Statistiques.mediane(data.map(r => r(col2)))

      tab = StatsAttribut.grapheMinMaxMoy(col1, col2, data)
      stats = Statistiques.otherStats(data, col2)
    }
    else if (Fichier.extension(nomFichier)=="json") {
      val textFile = sqlContext.jsonFile(cheminSource + nomFichier)//On charge le fichier avec spark, le type de textFile est SchemaRDD
      textFile.registerTempTable("textFile") //On enregistre le SchemaRDD comme une table SQL

      val filtreTable = if(filtre.equals("")) "" else Filtre.filtreJson(sqlContext, textFile, filtre)

      nbRow = Statistiques.nbTuples(sqlContext, "textFile", filtreTable)

      tab = StatsAttribut.grapheMinMaxMoy(sqlContext, attribut1, attribut2, "textFile", filtreTable)
      stats = Statistiques.otherStats(sqlContext, "textFile", attribut2, filtreTable)
    }
    else
      throw new Exception()
    val nomFichierStats = Csv.creerGraphe(nomFichier + "_" + attribut1 + "_" + attribut2, cheminCible, tab) //On crée le fichier CSV à renvoyer à la webApp

    return Array[String](nomFichierStats, nbRow.toString, stats, mediane)
  }

  def traitementKmeans(cheminSource: String, cheminCible: String, nomFichier: String, nbClusters: Int, filtre: String): String = {
    var textFile: RDD[Array[String]] = null

    if (Fichier.extension(nomFichier)=="csv" || Fichier.extension(nomFichier)=="tsv") {
      val data = sc.textFile(cheminSource + nomFichier)
      val sep = Csv.separateur(data)
      textFile = data.map(_.split(sep))
      if (!filtre.equals("")) textFile = Filtre.filtreCSV(textFile, filtre)
      else {
        val header = textFile.first()
        textFile = textFile.filter(line => !line.apply(0).equals(header.apply(0)))
      }
    }
    else if (Fichier.extension(nomFichier)=="json") {
      var data = sqlContext.jsonFile(cheminSource + nomFichier)
      if (!filtre.equals("")) {
        val filtreTable = Filtre.filtreJson(sqlContext, data, filtre)
        data.registerTempTable("data")
        data = sqlContext.sql("SELECT * FROM data WHERE " + filtreTable)
      }
      textFile = data.map(r => {
        val array = new Array[String](r.length)
        for(i <- 0 to r.length) {
          array.update(i, r.getString(i))
        }
        array
      })
    }
    else
      throw new Exception()
    val tab = Kmeans.kmeans(textFile, nbClusters, 0)
    val nomFichierKmeans = Csv.creerKmeans(nomFichier + "_kmeans", cheminCible, tab)
    return nomFichierKmeans
  }
}