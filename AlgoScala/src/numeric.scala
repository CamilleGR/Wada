def isNumeric(str: String): Boolean = {
  str.matches("(-?[0-9]+(?:\\.[0-9]+)?)")
}
 
//"(-?[0-9]+(?:\\.[0-9]+)?)"

/*
* Fonction sur un Tableau 2D
*

def isNumericAttr(col:Int,array:Array[Array[String]]):Boolean = {
	var test = true;
		for(i <- 1 to array.length-1 ) {
			test &= isNumeric(array(i)(col))
		}
	return test;
}
*/



/*
*	Fonction qui marche sur un RDD déjà splité 
*	Prend en paramètre la colonne sur laquelle il faut faire le test
*/
def isNumericAttr(array:org.apache.spark.rdd.RDD[Array[String]], col:Int):Boolean ={
	return array.map(r => r(col)).filter(line => !isNumeric(""+line)).count() <= 1	
}
