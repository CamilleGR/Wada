import scala.collection.mutable.ArrayBuffer
import java.io.File
import java.io.FileWriter
import java.io.BufferedWriter

class JSON{

	var stats = ArrayBuffer.empty[(String,Any)]
	var tabs = ArrayBuffer.empty[(String,Array[(String,Any)])]
	
	def addTabs(name:String,array:Array[(String,Any)]):Unit = tabs.append((name,array))
	def addStats(name:String,stat:Any):Unit = stats.append((name,stat))
	
	def statsToString():String ={
		var temp ="\"stats\" = "+tabToString(this.stats.toArray[(String,Any)])
		return temp
	}


	def tabsToString():String ={
		var temp =""
		for(i<- 0 to this.tabs.length-1){
			if(i>0) temp+=",\n"
			temp+="\""+this.tabs(i)._1+"\" : "+ tabToString(this.tabs(i)._2)+"\n"
		}
		return temp
	}


	override def toString():String = "{\n"+statsToString+",\n"+tabsToString+"}"

	def tabToString(array:Array[(String,Any)]):String = {
		var temp ="["
		for( i <- 0 to array.length-1){
			var sep ="\""
			if ( array(i)._2.toString matches """[0-9]+\.?[0-9]?""") sep =""
			if( i > 0 ) temp += ","
			temp += "\n\t{\"label\":\""+array(i)._1+"\",\"value\":"+sep+array(i)._2+sep+"}"
		}
		temp+="]"
		return temp;
	}

	def varListToString(array:Array[(String,Any)]):String ={
		var temp =""
		for( i <- 0 to array.length-1){
			var sep ="\""
			if ( array(i)._2.toString matches """[0-9]+\.?[0-9]?""") sep =""
			if( i > 0 ) temp += ","
			temp += "\n\""+array(i)._1+"\":"+sep+array(i)._2+sep
		}
		return temp
	}

}
/*
var arrayTest = Array(("TestA",25.6),("TestB",2.5),("TestC",2.5),("TestD",76.6),("TestE",12.6),("TestF",21.6),("TestG",09.6),("TestH",89.6));
var StatTest1 = 25.6
var StatTest2 = 12
var StatTest3 = true
var StatTest4 = "Tête de harang"




var json = new JSON
json.addTabs("Tableau_Test",arrayTest.toArray[(String,Any)]) // Le cast est important car si c'est un type unique cela ne compile pas
json.addStats("StatDouble",StatTest1)
json.addStats("StatInt",StatTest2)
json.addStats("StatBoolean",StatTest3)
json.addStats("StatString",StatTest4)*/


var json = new JSON
var str = "Sat Feb 28 22:04:02 CET 2015,63\nSat Feb 28 22:06:02 CET 2015,96\nSat Feb 28 22:08:02 CET 2015,61\nSat Feb 28 22:10:02 CET 2015,42\nSat Feb 28 22:12:02 CET 2015,44\nSat Feb 28 22:14:02 CET 2015,49\nSat Feb 28 22:16:02 CET 2015,103\nSat Feb 28 22:18:02 CET 2015,91\nSat Feb 28 22:20:02 CET 2015,48"

var array = str.split("\n").map(x => x.split(",")).map(x => (x(0),x(1))).toArray[(String,Any)]

json.addTabs("EvoTweet",array)
json.addStats("Max",53)
json.addStats("Min",42)


var f= new File("exempleJSON.json")
var fw = new FileWriter(f)
var bw = new BufferedWriter(fw)

var ar = json.toString.split("\n")
for(line <- ar ) {
	bw.write(line)
	bw.newLine()
}
bw.close
fw.close



