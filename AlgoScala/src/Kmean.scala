def kmean(seg: Int, col: Int, tab: org.apache.spark.rdd.RDD[Array[String]]):ArrayBuffer[(String,Double)] = {

	val min = tab.map(r => r(col)).reduce((a,b)=> Math.min(a.toDouble,b.toDouble).toString).toDouble
	val max = tab.map(r => r(col)).reduce((a,b)=> Math.max(a.toDouble,b.toDouble).toString).toDouble
	val segment = (max-min)/seg
	println("min = " + min + "\nmax = " + max)
	var array = tab.map(r => r(col))
	var ret = new ArrayBuffer[(String,Double)];
	
	for(i:Int<-0 to seg-1){
		ret :+= (min+segment*i +" - "+ (min+segment*(i+1)),tab.map(r=>r(col).toDouble).filter(r => r>min+segment*i && r<min+segment*(i+1)).mean)}

	return ret
}

//val file = sc.textFile("../res/codePostaux.csv")
//kmean(5,5,file.map( line => line.split(";")))
