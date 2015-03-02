package fonctions

import java.text.DecimalFormat

import org.apache.spark.SparkContext
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.{Vector, Vectors, Matrix}
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD

object Kmeans {

  /*
  Fonction qui convertit un RDD de tableaux de string, en un RDD de tableaux de Double
  @args :
  rdd: RDD[Array[String]]       -> rdd à convertir
  @returns: RDD[Array[Double]]  -> rdd convertit
  */
  def convertRddToDouble(rdd: RDD[Array[String]]): RDD[Array[Double]] = {
    val nbCol = rdd.first.length
    var rddR = rdd
    for(i <- 0 to nbCol-1) {
      if (!Colonne.numerique(rdd,i)) {
        val tab = rdd.map(r => r(i)).distinct().collect()
        val max = tab.length-1
        rddR = rddR.map(r => remplaceString(r, indiceTableauDistinct(tab, r(i)), i, max))
      }
    }
    return rddR.map(r => r.map(s => s.toDouble))
  }

  /*
  Tout comme la fonction precedente, convertit le rdd de string en double, et renvoit un rdd de couple de tableau de string et de double, le tableau de double étant l'equivalent du tableau de string
  @args :
  rdd: RDD[Array[String]]                       -> rdd à convertir
  @returns: RDD[(Array[String], Array[Double])] -> rdd convertit
  */
  def convertRddToArrayStringDouble(rdd: RDD[Array[String]]): RDD[(Array[String], Array[Double])] = {
    val nbCol = rdd.first.length
    var rddR = rdd.map(r => (r,r))
    for(i <- 0 to nbCol-1) {
      if (!Colonne.numerique(rdd,i)) {
        val tab = rdd.map(r => r(i)).distinct().collect()
        val max = tab.length-1
        rddR = rddR.map(r => (r._1, remplaceString(r._2, indiceTableauDistinct(tab, r._2(i)), i, max)))
      }
    }
    return rddR.map(r => (r._1,r._2.map(s => s.toDouble)))
  }

  /*
  Retourne l'indice d'une valeur dans un tableau de valeurs distincts de string
  @args :
  tab: Array[String]  -> tableau de valeurs distincts
  valeur: String      -> valeur à chercher
  @returns: String    -> indice casté en string
  */
  def indiceTableauDistinct(tab: Array[String], valeur: String): String = {
    for (i <- 0 to tab.length-1) {
      if (valeur.equals(tab(i))) return i + ""
    }
    return "-1"
  }

  def remplaceString(tab: Array[String], valeur: String, indice: Int, max: Int): Array[String] = {
    var tabR = tab.clone()
    tabR.update(indice, if(valeur==max.toString) 1.toString else 0.toString)
    val nb = valeur.toInt
    for(i <- 0 to max-1) {
      if (i==nb) tabR :+= 1.toString
      else tabR :+=0.toString
    }
    return tabR
  }

  /*
  Projete un rdd de tableau de double en un rdd de vecteur à deux dimensions
  @args :
  rdd: RDD[Array[Double]] -> rdd à projeter
  @returns: RDD[Vector]   -> rdd de vecteurs à deux dimensions
  */
  def projectionData(rdd: RDD[Array[Double]]): RDD[Vector] = {
    val parsedData = rdd.map(s => Vectors.dense(s)).cache()
    val mat: RowMatrix = new RowMatrix(parsedData)
    val pc: Matrix = mat.computePrincipalComponents(2)
    val projected: RowMatrix = mat.multiply(pc)
    return projected.rows
  }

  /*
  Retourne les centres des clusters d'un rdd de vecteurs
  @args :
  data: RDD[Vector]     -> le rdd de vecteurs
  numClusters: Int      -> Nombre de clusters
  numIterations: Int    -> Nombre d'itérations
  @returns: KMeansModel -> Coordonnées des centres des clusters
  */
  def centresClusters(data:RDD[Vector], numClusters: Int, numIterations: Int): KMeansModel = {
    val clusters = KMeans.train(data, numClusters, numIterations)
    return clusters
  }

  /*
  Simplifie une valeur d'un tableau de double, de tel façon à ce qu'elle soit comprise entre -10 et 10, et qu'elle est au maximum 3 chiffres après la virgule
  @args :
  indice: Int             -> indice de la valeur à modifier
  div: Double             -> valeur à diviser
  add: Double             -> valeur à ajouter
  array: Array[Double]    -> tableau de doublesà simplifier
  @returns: Array[Double] -> tableau avec la valeur à l'indice donnée simplifié
  */
  def simplificationVal(indice: Int, div: Double, add: Double, array: Array[Double]): Array[Double] = {
    val tab = array.clone
    val formatter = new DecimalFormat("#.###")
    val newVal = formatter.format((add + array(indice))/div).replace(',','.').toDouble
    tab.update(indice, newVal)
    return tab
  }

  /*
  Retourne un tableau de (Int, Array[Double]), le int correspond au centre le plus proche, le tableau de double correspond aux coordonnées du point
  @args :
  rdd: RDD[Array[String]]               -> un rdd de tableau de string
  numClusters: Int                      -> le nombre de clusters
  numIterations; Int                    -> le nombre d'itérations
  @returns: Array[(Int, Array[Double])] -> renvoit le tableau de kmeans
  */
  def kmeans(sc: SparkContext, rdd: RDD[Array[String]], numClusters: Int, numIterations: Int): Array[(Int, Array[Double])]= {
    val data = convertRddToDouble(rdd)

    val dataProjected = projectionData(data)
    val centres = centresClusters(dataProjected, numClusters, numIterations)
    val centresRDD = sc.parallelize(centres.clusterCenters.map(r => (-1, r.toArray)))

    var array = dataProjected.map(r => (centres.predict(r),r.toArray)).union(centresRDD).sortBy(r => r._1)
    for (i <- 0 to 1) {
      val min = array.map(r => r._2(i)).reduce((x,y) => Math.min(x,y))
      val max = array.map(r => r._2(i)).reduce((x,y) => Math.max(x,y))
      val valeurAdd = (max-min)/2
      array = array.map(r => (r._1, simplificationVal(i, valeurAdd/10, valeurAdd-max, r._2)))
    }

    return array.collect
  }

  /*
  Calcul de stats numérique sur un attribut sur plusieurs clusters
  @args :
  rdd: RDD[Array[String]]               -> un rdd de tableau de string
  numClusters: Int                      -> le nombre de clusters
  numIterations; Int                    -> le nombre d'itérations
  seg: Int                              -> nombre de segments
  col: Int                              -> colonne de l'attribut
  @returns: Array[Array[(String, Int)]] -> renvoit le tableau de kmeans
  */
  def kmeansArrayNumerique(rdd: RDD[Array[String]], numClusters: Int, numIterations: Int, seg: Int, col: Int): Array[Array[(String, Int)]] = {
    val data = convertRddToDouble(rdd)

    val centres = centresClusters(data.map(r => Vectors.dense(r)), numClusters, numIterations)

    var array = new Array[Array[(String, Int)]](0)

    for (i <- 0 to numClusters-1) {
      array :+= StatsAttribut.numerique(seg, col, data.filter(r => centres.predict(Vectors.dense(r)) == i).map(s => s.map(x => x.toString)))
    }

    return array
  }

  /*
  Calcul de stats non numérique sur un attribut sur plusieurs clusters
  @args :
  rdd: RDD[Array[String]]               -> un rdd de tableau de string
  numClusters: Int                      -> le nombre de clusters
  numIterations; Int                    -> le nombre d'itérations
  seg: Int                              -> nombre de segments
  col: Int                              -> colonne de l'attribut
  @returns: Array[Array[(String, Int)]] -> renvoit le tableau de kmeans
  */
  def kmeansArrayChaine(rdd: RDD[Array[String]], numClusters: Int, numIterations: Int, seg: Int, col: Int): Array[Array[(String, Int)]] = {
    val data = convertRddToArrayStringDouble(rdd)

    val centres = centresClusters(data.map(r => Vectors.dense(r._2)), numClusters, numIterations)

    var array = new Array[Array[(String, Int)]](0)

    for (i <- 0 to numClusters-1) {
      array :+= StatsAttribut.chaine(seg, col, data.filter(r => centres.predict(Vectors.dense(r._2)) == i).map(x => x._1))
    }

    return array
  }
}
