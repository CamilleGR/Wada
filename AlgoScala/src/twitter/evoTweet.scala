

def evolutionParMinute(sc:SparkContext, fileName:String): RDD[(String,Int)] = return sc.textFile(fileName).map(x => x.split(";")(0)).filter(x=> x matches """[0-9]{2}:[0-9]{2}""").map(x => (x,1)).reduceByKey((x,y) => x+y)



def evolutionParTranche(sc:SparkContext, tranche:Int,  array:RDD[(String,Int)]): Array[Array[String]] = {


	c = Calendar.getInstance();
	var tab = ArrayBuffer.empty[Array[String]]
	var min = array.map(x => x._1).min

	c.set(Calendar.HOUR,c.split(":")(0).toInt)
	c.set(Calendar.MINUTE,c.split(":")(1).toInt)

		// à continuer
}
