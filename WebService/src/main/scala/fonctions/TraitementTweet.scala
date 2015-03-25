package fonctions

import org.apache.spark.{SparkContext}
import org.apache.spark.rdd.RDD
import java.io._
import scala.collection.mutable.ArrayBuffer
import java.util.Date


class TraitementTweet(sparkContext:org.apache.spark.SparkContext){

	val sc = sparkContext

	def evoTweet(path:String,inter:Int):Array[(String,Int)] = {
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
			var couple: (String,Int) = ((new Date(min+i*interval)).toString , array.filter(x => x< min+i*interval && x>=min+(i-1)*interval).count.toInt)
			temp += couple
			i+=1
		}
		return temp.toArray
}

	/*
	Lol des barres ma lignes est complètement deygueux 
	
	
	def associatedHashtag(path:String):org.apache.spark.rdd.RDD[(String,Int)] = sc.textFile(path).filter(!_.equals("")).filter(_.split(";")(0).trim matches """[0-9]*""").flatMap(_.split(";")(1).toUpperCase.split(" ")).filter(!_.equals("")).map(x => (x,1)).reduceByKey((x,y)=>x+y)


	def traitementHashTag(path:String,target:String):Boolean ={
		try{
			var ar = associatedHashtag(path)
			var sum = ar.map(x=> x._2).sum
			var array = ar.map(x => (x._1,(x._2/sum)*100)).collect
			var pw = new java.io.PrintWriter(new java.io.File(target))
			pw.write("label,value\n")
			for(i<- 0 to ar.length -1){
				pw.write(array(i)._1 + "," + array(i)._2 + "\n")
				}
		}catch{
			case e : Exception => return false
		
		}
	}
*/
	def traitement(path:String,inter:Int):String = {
	/*
		try{
			var file = new java.io.File(target)
			var pw = new java.io.PrintWriter(file)
			pw.write("label, value\n")
			val array = evoTweet(path,inter)
				for( i <- 0 to array.length-1){
					pw.write(array(i)._1+","+array(i)._2+"\n")
				}
			pw.close
			return true
		}catch{
		case e : Exception => return false
		}
	*/
    var json = new JSONcontainer
    json.addTabs("evoTweet",evoTweet(path,inter).toArray[(String,Any)])
    return json.toString()
  }


	
	
	def ArrayToJsonString(ar:Array[String],separateur:Char,label:String,value:String):String = {
		 var str ="{\n\"evolution\" : [\n"

		 var val1 = ar(0).split(separateur)(0)
		 var val2 = ar(0).split(separateur)(1)

		 str += "{\n\""+label+"\":\""+val1+"\",\n\""+value+"\":\""+val2+"\"\n}\n"

		 for( i<- 1 to ar.length-1){
			var temp = ar(i).split(separateur)
			str += ",{\n\""+label+"\":\""+temp(0)+"\",\n\""+value+"\":\""+temp(1)+"\"\n}\n"
		  }
		  	str += "]}"
    		return str
	 }



}
