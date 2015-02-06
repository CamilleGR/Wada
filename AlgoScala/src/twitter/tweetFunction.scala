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
	
	//var test ="Thu Feb 05 22:20:02 CET 2015"
	
	
	/*
	*return = une chaine traite
	*@chaine:String = Chaine à traiter
	*@prec:Char = 'h' pour heure, 'm' pour minute, 's' pour secondes
	*/
	def timeFormat(chaine:String, prec:Char):String ={
	var i =0
	var chaineRet =""
	var nbDD= if(prec=='h')0 else if(prec=='m') 1 else 2
		for(j<-0 to chaine.length-1){

				if(i>nbDD)return chaineRet
				else{
					if(chaine.charAt(j)==':'){i+=1}
					
					if(chaine.charAt(j)!=':' || i<=nbDD)chaineRet+=chaine.charAt(j)
				}
		}
	
	return chaineRet
	}
	
	
	
def startStreamingFor(jssc:JavaStreamingContext,t:Int):Unit = {
	jssc.start 	//-> Il faut rediriger le flux vers un fichier ( on pourrait même enregistrer sur le hdfs ... 
	Thread.sleep(t) // On attend t seconde ...
	jssc.stop(stopSparkContext=false)	//-> On arrête le flux	*/
	}
	
