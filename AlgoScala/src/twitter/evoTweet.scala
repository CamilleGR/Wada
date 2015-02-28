import java.util.Date
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import scala.collection.mutable.ArrayBuffer



def evoTweet(sc:org.apache.spark.SparkContext,inter:Int,path:String):Array[(String,Int)] = {
	var interval = 60000*inter
	var file = sc.textFile(path)
	var array = file.filter(x => !x.trim.equals(""))
	.filter(x => x.split(";")(0).trim matches """[0-9]*""")
	.map(x => x.split(";")(0).trim.toLong)
	var temp = ArrayBuffer.empty[(String,Int)]
	var min = array.min
	var max = array.max
	var i =1
	while( min + i*interval < max ){
		temp :+= ((new Date(min+i*interval)).toString,array.filter(x => x< min+i*interval && x>=min+(i-1)*interval).count.toInt)
		i+=1
	}
	return temp.toArray
}
