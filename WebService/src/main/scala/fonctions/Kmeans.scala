package fonctions

import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.{Vector, Vectors, Matrix}
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD

object Kmeans {
  def convertRddToDouble(rdd: RDD[Array[String]]): RDD[Array[Double]] = {
    val nbCol = rdd.first.length
    var rddR = rdd
    for(i <- 0 to nbCol-1) {
      if (!Colonne.numerique(rdd,i)) {
        val tab = rdd.map(r => r(i)).distinct().collect()
        rddR = rddR.map(r => remplaceString(r, tableauDistinct(tab, r(i)), i))
      }
    }
    return rddR.map(r => r.map(s => s.toDouble))
  }

  def tableauDistinct(tab: Array[String], valeur: String): String = {
    for (i <- 0 to tab.length-1) {
      if (valeur.equals(tab(i))) return i + ""
    }
    return "-1"
  }

  def remplaceString(tab: Array[String], valeur: String, indice: Int): Array[String] = {
    var tabR = tab.clone()
    tabR.update(indice, valeur)
    return tabR
  }

  def projectionData(rdd: RDD[Array[Double]]): RDD[Vector] = {
    val parsedData = rdd.map(s => Vectors.dense(s)).cache()
    val mat: RowMatrix = new RowMatrix(parsedData)
    val pc: Matrix = mat.computePrincipalComponents(2)
    val projected: RowMatrix = mat.multiply(pc)
    return projected.rows
  }

  def centresClusters(data:RDD[Vector], numClusters: Int, numIterations: Int): KMeansModel = {
    val clusters = KMeans.train(data, numClusters, numIterations)
    return clusters
  }

  def translation(indice: Int, nb: Double, array: Array[Double]): Array[Double] = {
    val tab = array.clone
    val newVal = nb + array(indice)
    tab.update(indice, newVal)
    return tab
  }

  def kmeans(rdd: RDD[Array[String]], numClusters: Int, numIterations: Int): Array[(Int, Array[Double])]= {
    val data = convertRddToDouble(rdd)

    val dataProjected = projectionData(data)
    val centres = centresClusters(dataProjected, numClusters, numIterations)

    var array = dataProjected.map(r => (centres.predict(r),r.toArray)).sortBy(r => r._1)
    for (i <- 0 to 1) {
      val min = array.map(r => r._2(i)).reduce((x,y) => Math.min(x,y))
      val max = array.map(r => r._2(i)).reduce((x,y) => Math.max(x,y))
      val valeur = (max-min)/2 -max
      array = array.map(r => (r._1, translation(i, valeur, r._2)))
    }

    return array.collect()
  }
}
