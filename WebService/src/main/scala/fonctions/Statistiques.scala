package fonctions

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkContext._

import scala.collection.mutable.ArrayBuffer

object Statistiques {
  /*
  Fonction qui retourne la moyenne d'un RDD[String]
  @args :
  tab: RDD[String] -> le RDD
  @returns: Double -> la moyenne
  */
  private def moyenne(tab: RDD[String]): Double = {
    tab.reduce((x,y) => (x.toDouble + y.toDouble).toString).toDouble/tab.count()
  }

  def mediane(tab: RDD[String]) : String = {
    val med = tab.map(x => (x,1))
                 .reduceByKey((x,y) => x+y)
                 .map(x => (x._2,x._1))
                 .sortByKey(false)
                 .first()._2
    return med
  }

  /*
  Fonction qui calcul la moyenne par segment
  @args :
  seg: Int                                -> nombre de segments
  col: Int                                -> colonne de l'attribut
  tab: RDD[Array[String]]                 -> rdd de tableaux de string
  @returns: ArrayBuffer[(String,Double)]  -> tableau de moyennes par segment
  */
  def moyenneSegment(seg: Int, col: Int, tab: RDD[Array[String]]):ArrayBuffer[(String,Double)] = {

    val min = tab.map(r => r(col)).reduce((a,b)=> Math.min(a.toDouble,b.toDouble).toString).toDouble
    val max = tab.map(r => r(col)).reduce((a,b)=> Math.max(a.toDouble,b.toDouble).toString).toDouble
    val segment = (max-min)/seg
    var array = tab.map(r => r(col))
    var ret = new ArrayBuffer[(String,Double)];

    for(i:Int<-0 to seg-1){
      ret :+= (min+segment*i +" - "+ (min+segment*(i+1)),tab.map(r=>r(col).toDouble).filter(r => r>min+segment*i && r<min+segment*(i+1)).mean)}

    return ret
  }

  def StringMoyenneSegment(arrayBuffer: ArrayBuffer[(String,Double)]): String = {
    var ret = ""

    for (i <- 0 to arrayBuffer.length-1) {
      ret += arrayBuffer(i)._1 + "," + arrayBuffer(i)._2
      if (i < arrayBuffer.length-1) ret += ";"
    }

    return ret
  }

  /*
  Fonction qui retourne le nombre de ligne d'une table SQL en fonction de ses filtres
  @args :
  sQLContext: SQLContext  -> le SQLContext pour l'execution de requete SQL
  tab: String             -> nom de la table sql
  filtre: String          -> ligne de filtre à ajouter dans une clause where
  @returns: Long          -> nombre de ligne de la table
  */
  def nbTuples(sQLContext: SQLContext, tab: String, filtre: String): Long = {
    val where = if(filtre=="") "" else " WHERE " + filtre
    return sQLContext.sql("SELECT count(*) FROM " + tab + where).map(t => t(0).toString).first().toLong
  }

  /*
  Fonction qui retourne plusieurs stats pour un fichier csv/tsv (minimum, maximum, moyenne) !!!A Faire : la mediane!!!
  @args :
  tab: RDD[Array[String]] -> le RDD sur lequel on calcul les statistiques
  col: Int                -> l'indice sur la colonne sur laquel on calcul les statistiques
  @returns: String        -> liste des statisitques séparés par des virgules
  */
  def otherStats(tab: RDD[Array[String]], col: Int): String = {
    var ar = tab.map(r => r(col))
    val min = ar.reduce((a, b) => Math.min(a.toDouble, b.toDouble).toString).toDouble //On calcule la valeur minimale
    val max = ar.reduce((a, b) => Math.max(a.toDouble, b.toDouble).toString).toDouble //et maximale
    val moy = moyenne(ar)

    return min + "," + max + "," + moy
  }

  /*
  Fonction qui retourne plusieurs stats pour un fichier json (minimum, maximum, moyenne) !!!A Faire : la mediane!!!
  @args :
  sQLContext: SQLContext  -> le SQLContext pour l'execution de requete SQL
  tab: String             -> nom de la table sql
  col: String             -> nom de la colonne
  filtre: String          -> String de filtre à ajouter dans la clause where
  @returns: String        -> liste des statisitques séparés par des virgules
  */
  def otherStats(sQLContext: SQLContext, tab: String, col: String, filtre: String): String = {
    val where = if(filtre=="") "" else " WHERE " + filtre
    val min = sQLContext.sql("SELECT min(" + col + ") FROM " + tab + where).map(t => t(0).toString).first().toDouble //On calcule la valeur minimale
    val max = sQLContext.sql("SELECT max(" + col + ") FROM " + tab + where).map(t => t(0).toString).first().toDouble //et maximale
    val moy = sQLContext.sql("SELECT avg(" + col + ") FROM " + tab + where).map(t => t(0).toString).first().toDouble

    return min + "," + max + "," + moy
  }
}
