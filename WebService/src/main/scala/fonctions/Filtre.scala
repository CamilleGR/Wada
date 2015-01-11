package fonctions

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SchemaRDD, SQLContext}

object Filtre extends Serializable {
  /*
  Fonction convertit un chaine contenant des filtres en un tableau à deux dimensions de 3 colonnes
  @args :
  filtre:String                   -> chaine de caractère contenant les filtres séparés par des points-virgule
  @returns: Array[Array[String]]  -> tableau de filtres à deux dimensions, le nom de l'attribut dans la première colonne, le symbole dans la deuxième, et la valeur dans la troisième
  */
   private def stringToArray(filtre: String): Array[Array[String]] = {

     var nbFiltre = filtre.count(_ == ';')+1 // calcule le nombre de ; et donc le nb de filtre
     var tabFiltre = Array.ofDim[Array[String]](nbFiltre) // crée un tableau 2 à dimensions ou le nombre de ligne = ligneAttr+NbLigneFiltre

     var fullLineArray = filtre.split(";")// split la chaine de charactères = 1 ligne = 1 filtre

     var operatorList="([<!>]?=)|([<>])".r // Liste des opérateurs pris en charge , le .r le rend comme une expréssion REGEX / ancien regex : [<!>=]=

     for(i <- 1 to nbFiltre) {
       var filtreLine=fullLineArray(i-1)
       var operatorUsed=operatorList.findFirstIn(filtreLine).getOrElse("Aucun Op trouvé") // On detecte l'opérateur utilisé et avec cet opérateur on va faire un split
       var attrAndVar =fullLineArray(i-1).split(operatorUsed)
       tabFiltre(i-1)={Array(attrAndVar(0),operatorUsed, attrAndVar(1))}
     }
     return tabFiltre
   }

  /*
  Fonction de filtre POUR FICHIERS CSV/TSV a partir d'un tableau de filtres
  @args :
  file:RDD[Array[String]] -> RDD d'un csv/tsv
  tab:Array[String] -> Tableau de filtres
  @returns: :RDD[Array[String]] -> RDD filtré
  */
  def filtreCSV (file:RDD[Array[String]] , tab:Array[String]) :RDD[Array[String]] = {

    if(Fichier.extension(file.name).equals("csv")){		//Verification de l'extension
    var i=0
      var j=0
      for (i <-tab){				//Parcours des filtres
        j += 1 ;
        if(Colonne.numerique(file, Colonne.indiceAttribut(file, tab(0)(j)+""))){	//Si le filtre concerne un attribut numérique :
          if(tab(1)(j).equals("=")
            ||tab(1)(j).equals("!=")
            ||tab(1)(j).equals("<")
            ||tab(1)(j).equals(">")
            ||tab(1)(j).equals("<=")
            ||tab(1)(j).equals(">=")){
            tab(1)(j)+"" match{
              case "=" => file.filter(line => line.contains(tab(2)(j)))
              case "!=" => file.filter(line => !line.contains(tab(2)(j)))
              case "<" => file.filter(line => line(Colonne.indiceAttribut(file, tab(0)(j)+"")).toInt < tab(2)(j).toInt)
              case ">" => file.filter(line => line(Colonne.indiceAttribut(file, tab(0)(j)+"")).toInt > tab(2)(j).toInt)
              case "<=" => file.filter(line => line(Colonne.indiceAttribut(file, tab(0)(j)+"")).toInt <= tab(2)(j).toInt)
              case ">=" => file.filter(line => line(Colonne.indiceAttribut(file, tab(0)(j)+"")).toInt >= tab(2)(j).toInt)
            }
          }
        }else {				//Si le filtre concerne un attribut non numérique :
          if(tab(1)(j).equals("=")){
            file.filter(line => line.contains(tab(2)(j)))	//Filtre
          }else {
            return file
          }
        }
      }
    }else {
      return file
    }
    return file
  }

  /*
  Fonction de filtre POUR FICHIERS Json a partir d'un tableau de filtres
  @args :
  sqlContext:SQLContext -> le SQLContext pour l'execution de requete SQL
  table:String -> SchemaRDD d'un json
  tab:Array[String] -> Nom de la table SQL
  @returns: :SchemaRDD -> SchemaRDD filtré
  */
  def filtreJson (sqlContext:SQLContext, file:SchemaRDD,table:String,tab:Array[String]) :SchemaRDD = {

    if(Fichier.extension(file.name).equals("json")){		//Verification de l'extension
    var i=0
      var j=0
      var req=""
      for (i <- tab){				//Parcours des filtres
        j += 1 ;
        if(Colonne.numerique(sqlContext, file , tab(0)(j)+"")){	//Si le filtre concerne un attribut numérique :
          if(tab(1)(j).equals("=")
            ||tab(1)(j).equals("!=")
            ||tab(1)(j).equals("<")
            ||tab(1)(j).equals(">")
            ||tab(1)(j).equals("<=")
            ||tab(1)(j).equals(">=")){
            /*
          tab(1)(j)+"" match{		//Filtre
            case "=" => req = sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j)=tab(2)(j))
            case "!=" => req = sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j)!=tab(2)(j))
            case "<" => req = sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j)<tab(2)(j))
            case ">" => req = sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j)>tab(2)(j))
            case "<=" => req = sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j)<=tab(2)(j))
            case ">=" => req = sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j)>=tab(2)(j))
          }
            */
            sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j) + tab(1)(j) + tab(2)(j))
          }
        }else {				//Si le filtre concerne un attribut non numérique :
          if(tab(1)(j).equals("=")){
            sqlContext.sql("SELECT * FROM " + table + " WHERE " + tab(0)(j) + tab(1)(j) + tab(2)(j)) //Filtre
          }else {
            return file
          }
        }
      }
    }else {
      return file
    }
    return file
  }
}