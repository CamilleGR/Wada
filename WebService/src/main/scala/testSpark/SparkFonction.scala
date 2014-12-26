package testSpark

import java.io.{File, PrintWriter}

class SparkFonction {
  /*
  Fonction permettant de retourner la valeur minimale d'une colonne d'un tableau à 2 dimensions
  */
  def mini(col:Int, tab:Array[Array[String]]): Int={
    var min:Int=0;
    for(i <- 1 to tab.length-1 ){// la première ligne contient toujours le nom de l'attribut donc un String !
      min = if(tab(i)(col).toInt<min) tab(i)(col).toInt else min
    }
    min
  }
  
  /*
  Fonction permettant de retourner la valeur maximale d'une colonne d'un tableau à 2 dimensions
  */
  def maxi(col:Int, tab:Array[Array[String]]): Int={
    var max:Int=0;
    for(i <- 1 to tab.length-1 ){ // la première ligne contient toujours le nom de l'attribut donc un String !
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

    var tabR = Array.ofDim[String](seg, 2);
    val min = mini(col,tab);
    val max = maxi(col,tab);
    println(min)
    println(max)
    
    for(j <- 1 to seg) {
      var nb = 0;
      for (i <- 1 to tab.length-1) {
        if (tab(i)(col).toInt > min+ (max/seg)*(j-1) && tab(i)(col).toInt < min+ (max/seg)*j)
          nb += 1;
      }
      tabR(j-1) = Array(min+ (max/seg)*(j-1) + " à " + min+ (max/seg)*j, nb + "")
    }
    return tabR
  }
  
  /* 
  Fonction qui crée un fichier csv à partir d'un tableau à deux dimensions
  @args :
  nom:String -> Le nom du fichier à créer
  chemin:String -> Le chemin du fichier
  tab:Array[Array[String]] -> Tableau à deux dimensions à convertir
  */
  def creerCsv(nom:String, chemin:String, tab:Array[Array[String]]): Unit = {
    val writer = new PrintWriter(new File(chemin + nom + ".csv"))
    
    for (i <- 0 to tab.length-1) {
      for (j <- 0 to tab(i).length-1) {
        writer.write(tab(i)(j))
        if (j < tab(i).length-1) writer.write(",")
      }
      writer.write("\n")
    }
    
    writer.close()
  }

  def prcTab(tab:Array[Array[String]]):Array[Array[String]]={
    var total=0;
    for(i<- 0 to tab.length-1){
      total+=tab(i)(1).toInt;
    }

    var tabR = Array.ofDim[String](tab.length, 2);
    for(i<- 0 to tab.length-1){
      tabR(i)(0)=tab(i)(0);
      tabR(i)(1)=(tab(i)(1).toFloat/total.toFloat *100) +"";

    }
    return tabR;
  }
}
