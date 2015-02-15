package fonctions

import java.text.DecimalFormat

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

  def convertRddToArrayStringDouble(rdd: RDD[Array[String]]): RDD[(Array[String], Array[Double])] = {
    val nbCol = rdd.first.length
    var rddR = rdd.map(r => (r,new Array[Double](0)))
    for(i <- 0 to nbCol-1) {
      if (!Colonne.numerique(rdd,i)) {
        val tab = rdd.map(r => r(i)).distinct().collect()
        rddR = rddR.map(r => (r._1, remplaceString(r._1, tableauDistinct(tab, r._1(i)), i).map(x => x.toDouble)))
      }
    }
    return rddR
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

  def simplificationVal(indice: Int, div: Double, add: Double, array: Array[Double]): Array[Double] = {
    val tab = array.clone
    val formatter = new DecimalFormat("#.###")
    val newVal = formatter.format((add + array(indice))/div).replace(',','.').toDouble
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
      val valeurAdd = (max-min)/2
      array = array.map(r => (r._1, simplificationVal(i, valeurAdd/10, valeurAdd-max, r._2)))
    }

    return array.collect()
  }

  def kmeansArrayNumerique(rdd: RDD[Array[String]], numClusters: Int, numIterations: Int, seg: Int, col: Int): Array[Array[(String, Int)]] = {
    val data = convertRddToDouble(rdd)

    val centres = centresClusters(data.map(r => Vectors.dense(r)), numClusters, numIterations)

    var array = new Array[Array[(String, Int)]](0)

    for (i <- 0 to numClusters-1) {
      array :+= StatsAttribut.numerique(seg, col, data.filter(r => centres.predict(Vectors.dense(r)) == i).map(s => s.map(x => x.toString)))
    }

    return array
  }

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
