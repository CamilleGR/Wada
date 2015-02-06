package fonctions

import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.{Vector, Vectors, Matrix}
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD

/**
 * Created by rtasbishop on 06/02/15.
 */
object Kmeans {
  def convertRddToDouble(rdd: RDD[Array[String]]): RDD[Array[Double]] = {
    val nbCol = rdd.first.length
    var rddR = rdd
    for(i <- 0 to nbCol-1) {
      if (!Colonne.numerique(rdd,i)) {
        val tab = rdd.map(r => r(i)).distinct().collect()
        println("\n\n\n\n\n\n\n\n\n\n\n")
        tab.foreach(x => print(x + " | "))
        println("\n\n\n\n\n\n\n\n\n\n\n")
        rddR = rddR.map(r => remplaceString(r, tableauDistinct(tab, r(i)), i))
      }
    }
    println("\n\n\n\n\n\n\n\n\n\n\n")
    println(rddR.first().foreach(x => print(x + " | ")))
    println("\n\n\n\n\n\n\n\n\n\n\n")
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
    println("\n\n\n\n\n\n\n\n\n\n\n")
    tabR.foreach(x => print(x + " | "))
    println("\n\n\n\n\n\n\n\n\n\n\n")
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

  def kmeans(rdd: RDD[Array[String]], numClusters: Int, numIterations: Int): Array[(Int, Array[Double])]= {
    val data = convertRddToDouble(rdd)

    val dataProjected = projectionData(data)
    val centres = centresClusters(dataProjected, numClusters, numIterations)

    val array = dataProjected.map(r => (centres.predict(r),r.toArray)).sortBy(r => r._1)

    return array.collect()
  }
}
