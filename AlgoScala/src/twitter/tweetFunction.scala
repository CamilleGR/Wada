import org.apache.spark._
import org.apache.spark.rdd._

	def jsonToArrayString(chaine:String, fields:Array[String]): Array[String] = {
	
	var tab = new Array[String](fields.length)
	
	for(f<- 0 to fields.length-1 ){
		var debut= chaine.indexOf(fields(f))
		var fin = chaine.indexOf(",",debut)
			for(i<- debut to fin){
			tab(f)+=chaine(i)
			}
	}
	return tab
	}
