import java.io.{File, PrintWriter}

import org.apache.spark.SparkContext._
import org.apache.spark.rdd._
import org.apache.spark.sql.catalyst.types.{DoubleType, FloatType, IntegerType, LongType}
import org.apache.spark.sql.{SQLContext, SchemaRDD}

  def indiceAttribut(file:RDD[Array[String]],attribut:String):Int = {
    val header = file.first()

    for (i <- 0 to header.length-1) {
      if (header(i).equals(attribut)) {
        return i
      }
    }

    return -1
  }
  
   def numerique(file: RDD[Array[String]], col: Int) : Boolean = {
    var ar = file.map(r => r(col))
    val header = ar.first()
    ar = ar.filter(line => !line.equals(header))

    return ar.filter(line => !line.matches("^(-?[0-9]+(?:\\.[0-9]+)?)$")).count() == 0
  }


  def numerique(sqlContext:SQLContext, file:SchemaRDD,col:String) : Boolean = {
    if(file.schema.apply(col).dataType.equals(IntegerType)
      || file.schema.apply(col).dataType.equals(DoubleType)
      || file.schema.apply(col).dataType.equals(FloatType)
      || file.schema.apply(col).dataType.equals(LongType))
      return true
    else
      return false
  }

  /*
  Fonction de filtre POUR FICHIERS CSV/TSV a partir d'un tableau de filtres
  @args :
  file:RDD[Array[String]] -> RDD d'un csv/tsv
  tab:Array[String] -> Tableau de filtres
  @returns: :RDD[Array[String]] -> RDD filtré
  */
	def filtreCSV (file:RDD[Array[String]] , tab:Array[Array[String]]) :RDD[Array[String]] = {
		
		var rdd : RDD[Array[String]] = file
		var j=0

		for(j <- tab){		
						
			if(numerique(file, indiceAttribut(file,j(0)+""))){	//Si le filtre concerne un attribut numérique:	
									
				j(1)+"" match{
						
					case "=" => rdd = rdd.filter(line => line.contains(j(2)))
					case "!=" => rdd = rdd.filter(line => !line.contains(j(2)))
					case "<" => rdd = rdd.filter(line => line(indiceAttribut(rdd, j(0)+"")).toInt < j(2).toInt)
					case ">" => rdd = rdd.filter(line => line(indiceAttribut(rdd, j(0)+"")).toInt > j(2).toInt)
					case "<=" => rdd = rdd.filter(line => line(indiceAttribut(rdd, j(0)+"")).toInt <= j(2).toInt)
					case ">=" => rdd = rdd.filter(line => line(indiceAttribut(rdd, j(0)+"")).toInt >= j(2).toInt)
							
				}	    
			}else {				//Si le filtre concerne un attribut non numérique :
			
				if(j(1).equals("=")){
						
					rdd = rdd.filter(line => line.contains(j(2)))
							
				}							
			}
		}
		return rdd
	}

  /*
  Fonction de filtre POUR FICHIERS Json a partir d'un tableau de filtres
  @args :
  sqlContext:SQLContext -> le SQLContext pour l'execution de requete SQL
  table:String -> SchemaRDD d'un json
  tab:Array[String] -> Nom de la table SQL
  @returns: :String -> Fin de requête SQL commençant par " WHERE"...

	!! On ne peut pas utiliser colNumerique sans SchemaRDD !!!
  */
	def filtreJson(sqlContext:SQLContext ,file:SchemaRDD,table:String, tab:Array[Array[String]]) :String = {

		var j=0 
		var numLigne = 0
		var req="WHERE"
		for (j <- tab){														// Parcours des filtres
				
			if (numLigne>0) req += " AND"			// Si il y a plusieurs filtres
				
				if(numerique(sqlContext, file , j(0)+"")){		// Si le filtre concerne un attribut numérique:		
									 
						if(j(1).equals("=")
			 			 ||j(1).equals("!=")
			 			 ||j(1).equals("<")
						 ||j(1).equals(">")
					  	 ||j(1).equals("<=")
						 ||j(1).equals(">=")){	
						 
							req += " "+j(0)+" "+j(1)+" "+j(2)
							
						}
									 							
				}else {	
																			// Si le filtre concerne un attribut non numérique :
					if(j(1).equals("=")
			 		 ||j(1).equals("!=")){ 
			 		 
					req += " "+j(0)+" "+j(1)+" "+j(2)
					
					}
				} 							
				numLigne += 1 
		}	
		return req+";"
	}

 //Partie Test 

		//Test Json
	var filtre = Array(Array("age",">=","10"),Array("age","<","20"))
	val sqlContext = new org.apache.spark.sql.SQLContext(sc)
	var file = sqlContext.jsonFile("../BigDataProject/AlgoScala/WadaProject/src/data2.json")
	file.registerTempTable("test")
	println(filtreJson(sqlContext ,file , "test", filtre))



		//Test CSV
	val filtre2 = Array(Array("value","=","25"),Array("label","=","one"))
	
	val file2 = sc.textFile("../BigDataProject/scripts/data3.csv")
	val data = file2.map(line => line.split(","))
	
	filtreCSV(data, filtre2).foreach(r => r.foreach(println))

