def moyenne(col:Int, tab:Array[Array[String]]):Double={
  var som:Int=0
  var moy:Double=0
  for(i<-1 to tab.length-1){
    som=tab(i)(col).toInt+som
  }
  moy=som/(tab.length-1)
}

def mediane(col:Int, tab:Array[Array[String]]):Double={
  var med:Double=0
  if((tab.length-1)%2 != 0)
    med=tab((tab.length-1)%2+1)(col)
  else
    med=tab((tab.length-1)/2+0.1)(col)
}

def variance(col:Int, tab:Array[Array[String]]):Double={
  var vari:Double=0
  for(i<-1 to tab.length-1){
    vari=(tab(i)(col).toDouble-moy(col,tab))*(tab(i)(col).toDouble-moy(col,tab))+vari
  }
  vari/tab.length-1
}
