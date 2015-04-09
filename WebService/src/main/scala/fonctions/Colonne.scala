package fonctions

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.types.{LongType, FloatType, DoubleType, IntegerType}
import org.apache.spark.sql.{SchemaRDD, SQLContext}

object Colonne {
  /*
  Fonction qui retourne le numero de colonne d'un attribut donné
  @args :
  file: RDD[String] -> RDD d'un csv/tsv
  attribut: String  -> l'attribut
  @returns: Int     -> le numero de colonne
  */
  def indiceAttribut(file: RDD[Array[String]], attribut: String): Int = {
    val header = file.first()

    for (i <- 0 to header.length-1) {
      if (header(i).equals(attribut)) {
        return i
      }
    }

    return -1
  }

  /*
  Equivalent de la fonction precedente pour les fichiers JSON
  @args :
  file: SchemaRDD   -> SchemaRDD d'un JSON
  attribut: String  -> l'attribut
  @returns: Int     -> le numero de colonne
  */
  def indiceAttribut(file: SchemaRDD, attribut: String): Int = {
    val tab = file.schema.fieldNames
    for (i <- 0 to tab.length-1) {
      if (tab(i).equals(attribut)) return i
    }
    return -1
  }
  /*
  Fonction POUR FICHIERS CSV/TSV qui permet de savoir si la colonne n'est constitué que de réels ou non
  @args :
  file: RDD[Array[String]]  -> RDD d'un csv/tsv
  col: Int                  -> La colonne en question
  @returns: Boolean         -> true si colonne numerique, false sinon
  */
  def numerique(file: RDD[Array[String]], col: Int) : Boolean = {
    var ar = file.map(r => r(col))

    return ar.filter(line => !line.matches("(^(-?[0-9]*(?:\\.[0-9]+)?)$)")).count() == 0
  }

  /*
  Fonction POUR FICHIERS CSV/TSV qui permet de savoir si la colonne n'est constitué que de réels ou non
  @args :
  sqlContext:SQLContext -> le SQLContext pour l'execution de requete SQL
  file:SchemaRDD        -> SchemaRDD d'un JSON
  col:String            -> Nom de la colonne en question
  @returns: Boolean     -> true si colonne numerique, false sinon
  */
  def numerique(file: SchemaRDD, col: String) : Boolean = {
    if(file.schema.apply(col).dataType.equals(IntegerType)
      || file.schema.apply(col).dataType.equals(DoubleType)
      || file.schema.apply(col).dataType.equals(FloatType)
      || file.schema.apply(col).dataType.equals(LongType))
      return true
    else
      return false
  }
}
