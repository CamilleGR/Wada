package fonctions

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.catalyst.expressions.Row
import org.apache.spark.{sql, SparkContext, SparkConf}

class Traitement {
  val conf = new SparkConf().setAppName("test").setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLocalProperty("spark.driver.allowMultipleContexts", "true")
  val sqlContext = new SQLContext(sc)

  /*
  Fonction qui traite la demande de la liste des attributs d'un fichier
  @args :
  file: String      -> nom du fichier
  @returns: String  -> liste des atributs séparés par des virgules
  */
  def listeAttributs(file: String):String = {
    var ret = Array[Array[String]]()
    val extension = Fichier.extension(file)
    if ( extension=="csv" || extension=="tsv" ) { //Si c'est un csv ou tsv
      ret = listeAttributsCsvTsv(file)
    }
    else if (extension == "json") { //Si c'est un json
      ret = listeAttributsJson(file)
    }
    else
      throw new Exception()

    var json = "{\"attributs\": ["
    for (i<- 0 to ret.length-1) {
      if (i>0) json +=","
      json += "\n\t{\"label\":\""+ret(i)(0)+"\",\"numerique\":"+ ret(i)(1) +"}"
    }
    json += "]}"

    return json;
  }

  /*
  Fonction qui retourne la liste des attributs d'un csv/tsv
  @args :
  file: String      -> nom du fichier
  @returns: String  -> liste des atributs séparés par des virgules
  */
  private def listeAttributsCsvTsv(file: String): Array[Array[String]] = {
    val textfile = sc.textFile(file);
    val sep = Csv.separateur(textfile)
    val tab = textfile
      .map(r => r.split(sep))
      .first()
    val header = textfile.first()
    val data = textfile.filter(r => !r.equals(header)).map(r => r.split(sep))
    var tabR = Array[Array[String]]()
    for (i <- 0 to tab.length-1) {
      tabR :+= Array(tab(i), if (Colonne.numerique(data, i)) "true" else "false")
    }
    return tabR
  }

  /*
  Fonction qui retourne la liste des attributs d'un json
  @args :
  file: String      -> nom du fichier
  @returns: String  -> liste des atributs séparés par des virgules
  */
  private def listeAttributsJson(file: String): Array[Array[String]] = {
    val textFile = sqlContext.jsonFile(file)
    var tab = new Array[String](0)
    textFile.schema.fieldNames.foreach(r => tab :+= r) //On récupère les attributs
    var tabR = Array[Array[String]]()
    for (i <- 0 to tab.length-1) {
      tabR :+= Array(tab(i), if (Colonne.numerique(textFile, tab(i))) "true" else "false")
    }
    return tabR
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
  def traitementStats(cheminSource: String, cheminCible: String, nomFichier: String, attribut: String, segment: Int, filtre: String): String = {
    var tab = new Array[(String, Int)](0)
    var stats = Array[(String,Double)]()
    var nbRow:Long = 0
    var moyenneSegment = Array[(String,Double)]()
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
        moyenneSegment = Statistiques.moyenneSegment(segment, col, data)
        //moyenneSegment = Statistiques.StringMoyenneSegment(Statistiques.moyenneSegment(segment, col, data))
      }
      else
        tab = StatsAttribut.chaine(segment,col,data) // <- Si c'est un String on execute segmentStringArray
    }
    else if (Fichier.extension(nomFichier)=="json") {
      val textFile = sqlContext.jsonFile(cheminSource + nomFichier)//On charge le fichier avec spark, le type de textFile est SchemaRDD
      textFile.registerTempTable("textFile") //On enregistre le SchemaRDD comme une table SQL

      val filtreTable = if(filtre.equals("")) "" else Filtre.filtreJson(sqlContext, textFile, filtre)

      nbRow = Statistiques.nbTuples(sqlContext, "textFile", filtreTable)

      if (Colonne.numerique(textFile, attribut)) {
        tab = StatsAttribut.numerique(sqlContext, segment, attribut, "textFile", filtreTable) // <- Si c'est un Réel on execute segmentNum
        stats = Statistiques.otherStats(sqlContext, "textFile", attribut, filtreTable)
      }
      else
        tab = StatsAttribut.chaine(sqlContext,segment,attribut,"textFile", filtreTable) // <- Si c'est un String on execute segmentStringArray
    }
    else
      throw new Exception()
    
    val json = new JSONcontainer
    json.addTabs("tab", tab.toArray[(String,Any)])
    json.addStats("count", nbRow)
    if (mediane != "") json.addStats("med", mediane)
    if (stats.length != 0) {
      for (i <- 0 to stats.length-1) {
        json.addStats(stats(i)._1, stats(i)._2)
      }
    }
    if (moyenneSegment.length != 0)
      json.addTabs("moySeg", moyenneSegment.toArray[(String,Any)])
    //val nomFichierStats = Csv.creerStats(nomFichier + "_" + attribut, cheminCible, tab) //On crée le fichier CSV à renvoyer à la webApp
    return json.toString()
    //return Array[String](nomFichierStats, nbRow.toString, stats, moyenneSegment, mediane)
  }

  /*
  Fonction qui traite la demande de statistiques deux attributs numérique d'un fichier
  @args :
  cheminSource: String    -> chemin du repertoire du fichier lu
  cheminCible: String     -> chemin du repertoire du fichier csv généré
  nomFichier: String      -> nom du fichier
  attribut1: String       -> attribut en ordonnée du graphe
  attribut2: String       -> attribut en abscisse du graphe
  filtre: String          -> les filtres sur le fichier
  @returns: Array[String] -> le nom du fichier généré
                             le nombre de lignes
                             les stats (min,max,moyenne) (si colonne de valeurs numeriques)
  */
  def traitementGraphe(cheminSource: String, cheminCible: String, nomFichier: String, attribut1: String, attribut2: String, filtre: String): String = {
    var tab = new Array[(String, (Double, Double, Double))](0)
    var stats = Array[(String,Double)]()
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

    val json = new JSONcontainer
    json.addTabsCourbe("tab", tab)
    json.addStats("count", nbRow)
    if (mediane != "") json.addStats("med", mediane)
    if (stats.length != 0) {
      for (i <- 0 to stats.length-1) {
        json.addStats(stats(i)._1, stats(i)._2)
      }
    }
    //val nomFichierStats = Csv.creerGraphe(nomFichier + "_" + attribut1 + "_" + attribut2, cheminCible, tab) //On crée le fichier CSV à renvoyer à la webApp

    return json.toStringCourbe()
    //return Array[String](nomFichierStats, nbRow.toString, stats, mediane)
  }

  /*
  Fonction qui traite la demande de representation du kmeans sur un fichier
  @args :
  cheminSource: String    -> chemin du repertoire du fichier lu
  cheminCible: String     -> chemin du repertoire du fichier csv généré
  nomFichier: String      -> nom du fichier
  nbClusters: Int         -> le nombre de clusters
  filtre: String          -> les filtres sur le fichier
  @returns: String -> le nom du fichier généré
  */
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
        for(i <- 0 to r.length-1) {
          array.update(i, r.apply(i).toString)
        }
        array
      })
    }
    else
      throw new Exception()
    val tab = Kmeans.kmeans(sc, textFile, nbClusters, 0)
    val json = new JSONcontainer
    json.addTabsKmeans("centres", tab._1)
    json.addTabsKmeans("points", tab._2)
    json.addStats("count", textFile.count())
    return json.toStringKmeans()
    //val nomFichierKmeans = Csv.creerKmeans(nomFichier + "_kmeans", cheminCible, tab)
    //return nomFichierKmeans
  }

  /*
  Fonction qui traite la demande de stats sur plusieurs clusters d'un fichier à l'aide du kmeans
  @args :
  cheminSource: String    -> chemin du repertoire du fichier lu
  cheminCible: String     -> chemin du repertoire des fichiers csv générés
  nomFichier: String      -> nom du fichier
  nbClusters: Int         -> le nombre de clusters
  attribut: String        -> attribut sur lequel faire le calcul de stats
  segment: Int            -> le nombre de segment
  filtre: String          -> les filtres sur le fichier
  @returns: String        -> le nom du repertoire contenant les fichiers générés
  */
  def traitementKmeansStats(cheminSource: String, cheminCible: String, nomFichier: String, nbClusters: Int, attribut: String, segment: Int, filtre: String): String = {
    var textFile: RDD[Array[String]] = null
    var col = 0

    if (Fichier.extension(nomFichier)=="csv" || Fichier.extension(nomFichier)=="tsv") {
      val data = sc.textFile(cheminSource + nomFichier)
      val sep = Csv.separateur(data)
      textFile = data.map(_.split(sep))
      col = Colonne.indiceAttribut(textFile, attribut)
      if (!filtre.equals("")) textFile = Filtre.filtreCSV(textFile, filtre)
      else {
        val header = textFile.first()
        textFile = textFile.filter(line => !line.apply(0).equals(header.apply(0)))
      }
    }
    else if (Fichier.extension(nomFichier)=="json") {
      var data = sqlContext.jsonFile(cheminSource + nomFichier)
      col = Colonne.indiceAttribut(data, attribut)
      if (!filtre.equals("")) {
        val filtreTable = Filtre.filtreJson(sqlContext, data, filtre)
        data.registerTempTable("data")
        data = sqlContext.sql("SELECT * FROM data WHERE " + filtreTable)
      }
      textFile = data.map(r => {
        val array = new Array[String](r.length)
        for(i <- 0 to r.length-1) {
          array.update(i, r.apply(i).toString)
        }
        array
      })
    }
    else
      throw new Exception()

    var tab:Array[Array[(String, Int)]] = null
    if (Colonne.numerique(textFile, col)) {
      tab = Kmeans.kmeansArrayNumerique(textFile, nbClusters, 0, segment, col)
    }
    else {
      tab = Kmeans.kmeansArrayChaine(textFile, nbClusters, 0, segment, col)
    }
    val json = new JSONcontainer
    json.addStats("count", textFile.count())
    for (i <- 0 to tab.length-1) json.addTabs("tab" + i, tab(i).toArray[(String, Any)])
    //val nom = Csv.creerMultiplesStats(nomFichier + "_kmeans_" + attribut, cheminCible, tab)
    return json.toString()
    //return nom
  }
}