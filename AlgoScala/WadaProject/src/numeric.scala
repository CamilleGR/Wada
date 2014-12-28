def isNumeric(str: String): Boolean = {
  str.matches("(-?[0-9]+(?:\\.[0-9]+)?)")
}
 
//"(-?[0-9]+(?:\\.[0-9]+)?)"


def isNumericAttr(col:Int,array:Array[Array[String]]):Boolean = {
	var test = true;
		for(i <- 1 to array.length-1 ) {
			test &= isNumeric(array(i)(col))
		}
	return test;
}
