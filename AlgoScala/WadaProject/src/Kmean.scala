
def kmean(seg: Int, col: Int, tab: RDD[Array[String]]): Array[(String,Int)] = {
if(seg<=0)return null;
val data = tab.map(r => r(col)) //On ne garde que la colonne en question
val min = data.reduce((a, b) => Math.min(a.toDouble, b.toDouble).toString).toDouble //On calcule la valeur minimale
val max = data.reduce((a, b) => Math.max(a.toDouble, b.toDouble).toString).toDouble //et maximale
val temp = (max-min)/seg
var tabR = new Array[(String,Int)](0)
for(i <- 1 to seg) {
//On regroupe les valeurs en seg parties distincts, elles sont donc regroupés par parties comprises entre min+(max/seg)*(j-1) et min+(max/seg)*(i)
tabR :+= ((min+temp*(i-1)) + " à " + (min+temp*i),
if (i < seg) data.filter(r => r.toDouble >= (min+temp*(i-1)) && r.toDouble < (min+temp*i)).count().toInt
else data.filter(r => r.toDouble >= (min+temp*(i-1)) && r.toDouble <= (min+temp*i)).mean()
)
}
return tabR
}
