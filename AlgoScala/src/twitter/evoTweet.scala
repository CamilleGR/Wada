import java.util._
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.rdd._
import java.util.Calendar._
import scala.collection.mutable._

def evolutionParMinute(sc:SparkContext, fileName:String): RDD[(String,Int)] = return sc.textFile(fileName).map(x => x.split(";")(0)).filter(x=> x matches """[0-9]{2}:[0-9]{2}""").map(x => (x,1)).reduceByKey((x,y) => x+y)



def evolutionParTranche(sc:SparkContext, tranche:Int,  array:RDD[(String,Int)]): Array[(String,Int)] = {


	var c = Calendar.getInstance();
	var tab = ArrayBuffer.empty[(String,Int)]
	var min = array.map(x => x._1).min
	var max = array.map(x => x._1).max
	c.set(Calendar.HOUR_OF_DAY,min.split(":")(0).toInt)
	c.set(Calendar.MINUTE,min.split(":")(1).toInt)
	
		while(c.get(HOUR_OF_DAY)+":"+c.get(MINUTE)<=max){
			var inf = c.get(HOUR_OF_DAY)+":"+c.get(MINUTE)
			c.add(MINUTE,tranche)
			var sup = c.get(HOUR_OF_DAY)+":"+c.get(MINUTE)
			tab+= array.filter(x=> x._1 >= inf && x._1<=sup).map(x => (inf , x._2)).reduceByKey((x , y) => x+y).first
		}
	return tab.toArray
}

