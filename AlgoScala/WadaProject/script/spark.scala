/*def cutInArray(ch:String,col:Int):Array[String] = {
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
} */

/*
Fonction permettant de retourner la valeur minimale d'une colonne d'un tableau à 2 dimensions
*/
def mini(col:Int, tab:Array[Array[String]]): Int={
var min:Int=0;
for(i <- 1 to tab(0).length ){// la première ligne contient toujours le nom de l'attribut donc un String !
min = if(tab(i)(col).toInt<min) tab(i)(col).toInt else min
}
min
}
/*
Fonction permettant de retourner la valeur maximale d'une colonne d'un tableau à 2 dimensions
*/
def maxi(col:Int, tab:Array[Array[String]]): Int={
var max:Int=0;
for(i <- 1 to tab(0).length ){ // la première ligne contient toujours le nom de l'attribut donc un String !
max = if(tab(i)(col).toInt>max) tab(i)(col).toInt else max
}
max
}



/*
			/!\ Fonction non testé !! /!\
Fonction retournant un tableau à 2 dimensions qui regroupe les valeurs NUMERIQUE
d'une colonne d'un autre tableau en plusieurs segments (ex : 0 à 10, 10 à 20 ...)
@args : 
seg:Int -> Nombre de segments demandé par l'utilisateur.
col:Int -> Numero de la colonne sur laquelle on veut faire le traitement
tab:Array[Array[String]] -> Tableau à 2 dimensions de String ( qui sont en réalité des entiers )
@returns: Array[Array[String]] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment

*/
def segmentNum(seg:Int,col:Int, tab:Array[Array[String]]): Array[Array[String]] = {
	
	if(seg<=0)return null;
	
	var tabR: Array[Array[String]]= new Array[Array[String]](tab.length);
	val min = mini(col,tab);
	val max = maxi(col,tab);
	
	for(i <- 1 to tab(0).length){
		var j:Int=1;
		while(tab(i)(col).toInt > min+ (max/seg)*j-1 && tab(i)(col).toInt < min+ (max/seg)*j){
		j+=1;
		}
		tabR(j) ++= Array(tab(i)(col))
		j=0;
	}	
	return tabR
}



/*
val file = sc.textFile("WadaProject/res/code_postaux_v201410.csv")
var tc = file.collect()
val nbAtt = 4;


var array = new Array[Array[String](4)](tc.length)

for(i <- 1 to tc.length ){
	array(i)=cutInArray(tc(i),nbAtt);
	println(array(i));
}


*/



