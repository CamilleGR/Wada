package fonctions

import java.text.DecimalFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkContext._

object StatsAttribut {
  /*
  Fonction à placer dans une map, permet de trier les lignes d'un RDD en tranches
  @args :
  line; Double                    -> Ligne du RDD
  min: Double                     -> valeur minimum
  max: Double                     -> valeur maximun
  segments: Int                   -> le nombre de tranche
  @returns: ((Int, String), Int)  -> ((indice pour le tri, chaine de caractères de la tranche), 1 pour reduceByKey)
   */
  def tranche(line: Double, min: Double, max: Double, segments: Int): ((Int, String), Int) = {
    val temp = (max-min)/segments
    val formatter = new DecimalFormat("#.##")
    for (i <- 1 to segments) {
      if (line.toDouble >= temp*(i-1) && line.toDouble < temp*i) return ((i, formatter.format(min + temp*(i-1)).replace(',','.') + " à " + formatter.format(min + temp*i).replace(',','.')),1)
    }

    return ((segments, formatter.format(min + temp*(segments-1)).replace(',','.') + " à " + formatter.format(min + temp*segments).replace(',','.')), 1)
  }
  /*
  Fonction POUR LES FICHIERS CSV retournant un Array[(String,Int)] qui regroupe les valeurs NUMERIQUE
  d'une colonne d'un autre tableau en plusieurs segments (ex : 0 à 10, 10 à 20 ...)
  @args :
  seg: Int                      -> Nombre de segments demandé par l'utilisateur.
  col: Int                      -> Numero de la colonne sur laquelle on veut faire le traitement
  tab: RDD[Array[String]]       -> RDD à 2 dimensions de String ( qui sont en réalité des entiers )
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def numerique(seg: Int, col: Int, tab: RDD[Array[String]]): Array[(String,Int)] = {

    if(seg<=0)return null;

    var data = tab.map(r => r(col))  //On ne garde que la colonne en question

    val min = data.reduce((a, b) => Math.min(a.toDouble, b.toDouble).toString).toDouble //On calcule la valeur minimale
    val max = data.reduce((a, b) => Math.max(a.toDouble, b.toDouble).toString).toDouble //et maximale

    val tabR = data.map(r => tranche(r.toDouble, min, max, seg))
      .reduceByKey((x,y) => x + y)
      .sortByKey()
      .map(r => (r._1._2,r._2))
      .collect()

    return tabR
  }

  /*
  Equivalent de la fonction précedente pour les fichiers de type JSON
  @args :
  sqlContext: SQLContext        -> le SQLContext pour l'execution de requete SQL
  seg: Int                      -> Nombre de segments demandé par l'utilisateur.
  col: String                   -> Nom de la colonne sur laquelle on veut faire le traitement
  tab: String                   -> Nom de la table SQL
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def numerique(sqlContext: SQLContext, seg: Int, col: String, tab: String, filtre: String): Array[(String,Int)] = {

    if(seg<=0)return null;

    var where = if(filtre=="") "" else " WHERE " + filtre

    val min = sqlContext.sql("SELECT min(" + col + ") FROM " + tab + where).map(t => t(0).toString).first().toDouble //On calcule la valeur minimale
    val max = sqlContext.sql("SELECT max(" + col + ") FROM " + tab + where).map(t => t(0).toString).first().toDouble //et maximale

    //En JSON, l'entête ne se trouve pas dans le SchemaRDD, donc pas besoin de le retirer

    var nb:Int = 0
    val temp = (max-min)/seg
    val formatter = new DecimalFormat("#.##")
    var valMin:Double = 0
    var valMax:Double = 0

    where = if(filtre=="") "" else " AND " + filtre

    var tabR = new Array[(String,Int)](0)

    for(i <- 1 to seg) {
      //On regroupe les valeurs en seg parties distincts, elles sont donc regroupés par parties comprises entre min+(max/seg)*(j-1) et min+(max/seg)*(i)
      val sign = if (i < seg) "<" else "<="

      valMin = min+temp*(i-1)
      valMax = min+temp*(i)

      nb = sqlContext.sql("SELECT count(*) FROM " + tab + " WHERE " + col + ">=" + (if (valMin>1000000) valMin.toLong else valMin) + " AND " + col + sign + (if (valMax>1000000) valMax.toLong else valMax) + where).map(t => t(0).toString).first().toInt //On execute la requete SQL qui correspond
      tabR :+= (formatter.format(valMin).replace(',','.') + " à " + formatter.format(valMax).replace(',','.'), nb)
    }

    return tabR
  }

  /*
  Fonction POUR LES FICHIERS CSV retournant un Array[(String,Int)] qui regroupe les chaines de caractères
  @args :
  seg:Int                       -> Nombre de segments demandé par l'utilisateur.
  col:Int                       -> Numero de la colonne sur laquelle on veut faire le traitement
  tab:RDD[Array[String]]        -> RDD à 2 dimensions de String
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def chaine(seg: Int, col: Int, array: RDD[Array[String]]): Array[(String,Int)] = {

    val tempTab = array.map(r => r(col)) //On ne garde que la colonne sur laquel on veut effectuer nos stats
    //val header = tempTab.first()
    val data = tempTab
        //.filter(line => !line.equals(header)) //On retire l'entête...
        .map(r => (r,1))  //On convertit le RDD[String] en RDD[(String,Int)] pour l'execution des methodes tel que reduceByKey ou sortByKey
        .reduceByKey((x, y) => x + y) //On regroupe les valeurs egales dans la première colonne et on entre dans la deuxième le nombre de fois qu'elles aparaissent
        .map(t => (t._2, t._1)) //On inverse les colonnes pour trier en fonction de la deuxième colonne (sortByKey ne trie qu'en fonction de la première colonne)
        .sortByKey(false) //On trie en fonction des valeurs correspondant au nombre d'occurences afin de ne garder que les plus grandes

    val dataReduced = data.map(t => (t._2, t._1)) //On réinverse les colonnes pour avoir l'affichage normal du tableau

    if (seg >= data.count()) { //Si le nombre de segments est plus grand ou egal au nombre de ligne du tableau généré, on peut directement renvoyer toutes ses lignes
      return dataReduced.take(data.count().toInt)
    }
    else {                    //Sinon on prend les seg premières lignes et on en ajoute une pour toutes les autres
    var sum = 0
      dataReduced
        .map(t => t._2)
        .take(seg)
        .foreach(r => sum += r) //On calcule la somme des valeurs des seg permières lignes

      var tab = dataReduced.take(seg)
      tab :+= ("Reste",(array.count()-1-sum).toInt) //On ajoute une ligne avec pour valeur total-(sommes des seg premières lignes)

      return tab;
    }
  }

  /*
  Equivalent de la fonction précedente pour les fichiers de type JSON
  @args :
  sqlContext: SQLContext        -> le SQLContext pour l'execution de requete SQL
  seg: Int                      -> Nombre de segments demandé par l'utilisateur.
  col: String                   -> Nom de la colonne sur laquelle on veut faire le traitement
  tab: String                   -> Nom de la table SQL
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def chaine(sqlContext: SQLContext, seg: Int, col: String, array: String, filtre: String): Array[(String,Int)] = {
    val where = if(filtre=="") "" else " WHERE " + filtre
    val total = sqlContext.sql("SELECT COUNT(*) FROM " + array + where).map(t => t(0).toString).first().toInt //On calcul le nombre total de ligne pour calculer la valeur de la dernière ligne
    val data = sqlContext.sql("SELECT " + col + ", COUNT(" + col + ") AS C FROM " + array + where + " GROUP BY " + col + " ORDER BY C DESC").map(t => (t(0).toString, t(1).toString.toInt)) //On regroupe les valeurs egales dans la première colonne et on entre dans la deuxième le nombre de fois qu'elles aparaissent

    if (seg >= data.count()) { //Si le nombre de segments est plus grand ou egal au nombre de ligne du tableau généré, on peut directement renvoyer toutes ses lignes
      return data.take(total.toInt)
    }
    else { //Sinon on prend les seg premières lignes et on en ajoute une pour toutes les autres
    var sum = 0
      data
        .map(t => t._2)
        .take(seg)
        .foreach(r => sum += r) //On calcule la somme des valeurs des seg permières lignes

      var tab = data.take(seg)
      tab :+= ("Reste",(total-sum)) //On ajoute une ligne avec pour valeur total-(sommes des seg premières lignes)

      return tab;
    }
  }
}
