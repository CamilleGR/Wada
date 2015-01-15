
def kmean(seg: Int, col: Int, tab: org.apache.spark.rdd.RDD[Array[String]]): Array[(String,Int)] = {

val min = tab.map(r => r(col)).reduce((a,b)=> Math.min(a.toDouble,b.toDouble)).toDouble
val max = tab.map(r => r(col)).reduce((a,b)=> Math.min(a.toDouble,b.toDouble)).toDouble




}
