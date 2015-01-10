import java.io.{File, PrintWriter}

import org.apache.spark.SparkContext._
import org.apache.spark.rdd._
import org.apache.spark.sql.catalyst.types.{DoubleType, FloatType, IntegerType, LongType}
import org.apache.spark.sql.{SQLContext, SchemaRDD}


class SparkFonction {

  def colAttribut(file:RDD[Array[String]],attribut:String):Int = {
    val header = file.first()

    for (i <- 0 to header.length-1) {
      if (header(i).equals(attribut)) {
        return i
      }
    }

    return -1
  }


  def colNumerique(file:RDD[Array[String]],col:Int) : Boolean = {
    var ar = file.map(r => r(col))
    val header = ar.first()
    ar = ar.filter( line => !line.equals(header))

    if (true) { //Methode 2
      return ar.filter(line => !line.matches("(-?[0-9]+(?:\\.[0-9]+)?)")).count() == 0
    }

    return false
  }


  def colNumerique(sqlContext:SQLContext, file:SchemaRDD,col:String) : Boolean = {
    if(file.schema.apply(col).dataType.equals(IntegerType)
      || file.schema.apply(col).dataType.equals(DoubleType)
      || file.schema.apply(col).dataType.equals(FloatType)
      || file.schema.apply(col).dataType.equals(LongType))
      return true
    else
      return false
  }


  def getExtension(name:String):String = {
    var ret = ""
    for(i<- name.lastIndexOf('.')+1 to name.length-1 ){
      ret += name.charAt(i);
    }
    return ret;
  }

  /*
  Fonction de filtre POUR FICHIERS CSV/TSV a partir d'un tableau de filtres
  @args :
  file:RDD[Array[String]] -> RDD d'un csv/tsv
  tab:Array[String] -> Tableau de filtres
  @returns: :RDD[Array[String]] -> RDD filtré
  */
	def filtreCSV (file:RDD[Array[String]] , tab:Array[String]) :RDD[Array[String]] = {}
		
		if(getExtension(file.name).equals("csv")){		//Verification de l'extension
			var i=0 
			var j=0
			for (i <-tab){				//Parcours des filtres
				j += 1 ;
				if(colNumerique(file, colAttribut(file, tab(0)(j)+""))){	//Si le filtre concerne un attribut numérique :
					if(tab(1)(j).equals("=") ){
						file.filter(line => line.contains(tab(2)(j)))	//Filtre =
					}else if(tab(1)(j).equals("<") ){			// MARCHE PAS :'(	
						/*var colonneRDD = file.map(valeurRDD => valeurRDD(colAttribut(file, tab(0)(j)+"")))
    						val keys = colonneRDD.first()
    						colonneRDD = colonneRDD.filter( line => !line.equals(keys))
						return file.filter(line => colonneRDD < tab(2)(j)))*/
					}else if(tab(1)(j).equals(">") ){			// MARCHE PAS :'(
						/*var colonneRDD = file.map(valeurRDD => valeurRDD(colAttribut(file, tab(0)(j
+"")))
    						val keys = colonneRDD.first()
    						colonneRDD = colonneRDD.filter( line => !line.equals(keys))
						return file.filter(line => colonneRDD >tab(2)(j)))*/
					}else{
						//Erreur
					} 
				}else {					//Si le filtre concerne un attribut non numérique :
					if(tab(1)(j).equals("=")){
						file.filter(line => line.contains(tab(2)(j)))	//Filtre
					}else {
						// Erreur
					}
				}
			}
		}else {
			//Erreur
		}
		return file
	}


  /*
  Fonction de filtre POUR FICHIERS Json a partir d'un tableau de filtres
  @args :
  sqlContext:SQLContext -> le SQLContext pour l'execution de requete SQL
  file:SchemaRDD -> SchemaRDD d'un json
  tab:Array[String] -> Tableau de filtres
  @returns: :SchemaRDD -> SchemaRDD filtré
  */
	def filtreJson (sqlContext:SQLContext, file:SchemaRDD,tab:Array[String]) :SchemaRDD = {

		if(getExtension(file.name).equals("json")){		//Verification de l'extension
			var i=0 
			var j=0
			for (i <-tab){	//Parcours des filtres
				j += j ;
				if(colNumerique(sqlContext , file, tab(0)(j)+"")){		//Si le filtre concerne un attribut numérique :
					if(tab(1)(j).equals("=") ){
						//Filtrer Json Numerique = filtre
					}else if(tab(1)(j).equals("<") ){
						//Filtrer Json Numerique < filtre
					}else if(tab(1)(j).equals(">") ){
						//Filtrer Json Numerique > filtre
					}else{
						//Erreur
					} 
				}else {						//Si le filtre concerne un attribut non numérique :
					if(tab(1)(j).equals("=")){
						//Filtrer Json Non Numerique
					}else {
						// Erreur
					}
				}
			}
		}else {
			//Erreur
		}

		return file
	}
}
