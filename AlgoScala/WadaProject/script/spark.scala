def cutInArray(ch:String,col:Int):Array[String] = {
    var i = 0
    var c = 0
    var tab = new Array[String](col)

    var temp = ""
    while(i < ch.length){

      if(ch(i) != ';' && ch(i) != '\0' ){
        temp = temp + ch(i)
      }else{
        tab(c)=temp
        temp=""
        c=c+1
      }
     i=i+1
    tab(c)=temp
    }
    tab
  }

def nbAttr(ar:String) : Int={
	temp:Int=1
	for(i <- 0 to ar.length ){
		temp= if(ar(i)==';')temp+1 else temp
	}
	temp
}



val file = sc.textFile("WadaProject/res/code_postaux_v201410.csv")
var tc = file.collect()
val nbAtt = 4;


var array = new Array[Array[String](4)](tc.length)

for(i <- 1 to tc.length ){
	array(i)=cutInArray(tc(i),nbAtt);
	println(array(i));
}






