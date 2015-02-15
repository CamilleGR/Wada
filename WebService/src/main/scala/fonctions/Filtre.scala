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
  Fonction qui compare 2 valeurs de type Double
  @args :
  val1: Double      -> Première valeur
  val2: Double      -> Deuxième valeur
  sign: String      -> Signe de comparaison
  @return: Boolean  -> Si l'expression retourne vrai ou faux
   */
  def compare(val1: Double, val2: Double, sign: String): Boolean = {
    if (sign=="=") val1 == val2
    else if (sign=="!=") val1 != val2
    else if (sign=="<") val1 < val2
    else if (sign==">") val1 > val2
    else if (sign=="<=") val1 <= val2
    else if (sign==">=") val1 >= val2
    else false
  }

  /*
  Fonction de filtre POUR FICHIERS CSV/TSV a partir d'un tableau de filtres
  @args :
  file:RDD[Array[String]]       -> RDD d'un csv/tsv
  filtre: String                -> chaine de caractère contenant les filtres séparés par des points-virgule
  @returns: :RDD[Array[String]] -> RDD filtré
  */
  def filtreCSV (file:RDD[Array[String]] , filtre: String) :RDD[Array[String]] = {

    val tab = stringToArray(filtre)
    val header = file.first()
    var rdd : RDD[Array[String]] = file.filter(line => !line.apply(0).equals(header.apply(0)))
    var j=0

    for(j <- tab){

      if(Colonne.numerique(rdd, Colonne.indiceAttribut(file,j(0)+""))){	//Si le filtre concerne un attribut numérique:
        val col = Colonne.indiceAttribut(file, j(0))
        rdd = rdd.filter(line => compare(line(col).toDouble, j(2).toDouble, j(1)))
      }
      else {				//Si le filtre concerne un attribut non numérique :
        if(j(1).equals("=")) rdd = rdd.filter(line => line.contains(j(2)))
        else if (j(1).equals("!=")) rdd = rdd.filter(line => !line.contains(j(2)))
      }
    }
    return rdd
  }

  /*
  Fonction de filtre POUR FICHIERS Json a partir d'un tableau de filtres
  @args :
  sqlContext:SQLContext -> le SQLContext pour l'execution de requete SQL
  file: SchemaRDD       -> SchemaRDD d'un json
  filtre: String        -> chaine de caractère contenant les filtres séparés par des points-virgule
  @returns: :String     -> Fin de requête SQL commençant à placer dans une clause where

	!! On ne peut pas utiliser colNumerique sans SchemaRDD !!!
  */
  def filtreJson(sqlContext:SQLContext ,file:SchemaRDD, filtre:String) :String = {

    val tab = stringToArray(filtre)
    var j=0
    var numLigne = 0
    var req=""
    for (j <- tab){														// Parcours des filtres

      if (numLigne>0) req += " AND "			// Si il y a plusieurs filtres

      if(Colonne.numerique(file , j(0)+"")){		// Si le filtre concerne un attribut numérique:

        if(j(1).equals("=")
          ||j(1).equals("!=")
          ||j(1).equals("<")
          ||j(1).equals(">")
          ||j(1).equals("<=")
          ||j(1).equals(">=")){

          req += j(0) + j(1) + j(2)

        }

      }else {
        // Si le filtre concerne un attribut non numérique :
        if(j(1).equals("=")
          ||j(1).equals("!=")){

          req += j(0) + j(1)+ "\'" + j(2) + "\'"

        }
      }
      numLigne += 1
    }
    return req
  }
}