package fonctions

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext, SparkConf}

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
  def traitementPost(cheminSource: String, cheminCible: String, nomFichier: String, attribut: String, segment: Int): Array[String] = {
    var tab = new Array[(String, Int)](0)
    var stats = ""
    var nbRow:Long = 0

    if (Fichier.extension(nomFichier)=="csv" || Fichier.extension(nomFichier)=="tsv") { //Si c'est un csv ou tsv...
    val textFile = sc.textFile(cheminSource + nomFichier) //On charge le fichier avec spark, le type de textFile est RDD[Array[String]]
    val sep = Csv.separateur(textFile) //On récupère le séparateur du csv/tsv

      var data = textFile.map(_.split(sep)) //On crée un RDD[Array[String]] qui correspond, en fonction du separateur
      val col = Colonne.indiceAttribut(data, attribut) //On récupère le numero de colonne

      val header = data.first()
      data = data.filter(line => !line.apply(0).equals(header.apply(0)))

      nbRow = data.count()

      if (Colonne.numerique(data,col)) {
        tab = StatsAttribut.numerique(segment, col, data) // <- Si c'est un Réel on execute segmentNum
        stats = Statistiques.otherStats(data, col)
      }
      else
        tab = StatsAttribut.chaine(segment,col,data) // <- Si c'est un String on execute segmentStringArray
    }
    else if (Fichier.extension(nomFichier)=="json") {
      val textFile = sqlContext.jsonFile(cheminSource + nomFichier)//On charge le fichier avec spark, le type de textFile est SchemaRDD
      textFile.registerTempTable("textFile") //On enregistre le SchemaRDD comme une table SQL
      nbRow = Statistiques.nbTuples(sqlContext, "textFile")

      if (Colonne.numerique(sqlContext, textFile, attribut)) {
        tab = StatsAttribut.numerique(sqlContext, segment, attribut, "textFile") // <- Si c'est un Réel on execute segmentNum
        stats = Statistiques.otherStats(sqlContext, "textFile", attribut)
      }
      else
        tab = StatsAttribut.chaine(sqlContext,segment,attribut,"textFile") // <- Si c'est un String on execute segmentStringArray
    }
    else
      throw new Exception()
    val nomFichierStats = Csv.creer(nomFichier + "_" + attribut, cheminCible, tab) //On crée le fichier CSV à renvoyer à la webApp

    return Array[String](nomFichierStats, nbRow.toString, stats)
  }
}