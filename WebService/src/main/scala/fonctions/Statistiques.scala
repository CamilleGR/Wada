package fonctions

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext

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
/*
  private def mediane(tab: RDD[String]): Double = {
    tab
  }*/

  /*
  Fonction qui retourne le nombre de ligne d'une table SQL en fonction de ses filtres
  @args :
  sQLContext: SQLContext  -> le SQLContext pour l'execution de requete SQL
  tab: String             -> nom de la table sql
  filtre: String          -> ligne de filtre à ajouter dans une clause where
  @returns: Long          -> nombre de ligne de la table
  */
  def nbTuples(sQLContext: SQLContext, tab: String): Long = {
    return sQLContext.sql("SELECT count(*) FROM " + tab).map(t => t(0).toString).first().toLong
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
  def otherStats(sQLContext: SQLContext, tab: String, col: String): String = {
    val min = sQLContext.sql("SELECT min(" + col + ") FROM " + tab).map(t => t(0).toString).first().toDouble //On calcule la valeur minimale
    val max = sQLContext.sql("SELECT max(" + col + ") FROM " + tab).map(t => t(0).toString).first().toDouble //et maximale
    val moy = sQLContext.sql("SELECT avg(" + col + ") FROM " + tab).map(t => t(0).toString).first().toDouble

    return min + "," + max + "," + moy
  }
}
