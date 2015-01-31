package fonctions

import java.io.{File, PrintWriter}
import org.apache.spark.rdd.RDD

object Csv {
  /*
  Fonction qui retourne le separateur d'un fichier CSV/TSV
  @args :
  tab: RDD[String] -> RDD d'un csv/tsv
  @returns: String -> le separateur (virgule, point-virgule ou tabulation)
  */
  def separateur(file: RDD[String]): String = {
    val header = file.first()

    if (header.contains(",")) return ","

    else if (header.contains(";")) return ";"

    else if (header.contains("\t")) return "\t"

    else return null
  }

   /*
   Fonction qui crée un fichier csv à partir d'un tableau à deux dimensions
   @args :
   nom:String                 -> Le nom du fichier à créer
   chemin:String              -> Le chemin du fichier
   tab:Array[(String,Float)]  -> tableau de stats
   @returns: String           -> Chemin du fichier crée
   */
   def creer(nom: String, chemin: String, tab: Array[(String, Int)]): String = {
     val writer = new PrintWriter(new File(chemin + nom + ".csv")) //On crée un fichier csv en Scala
     writer.write("label,value\n")  //On écrit son entête

     for (i <- 0 to tab.length-1) { //On écrit chacune des lignes
       writer.write(tab(i)._1 + "," + tab(i)._2 + "\n")
     }

     writer.close()

     nom + ".csv"
   }

  def creer(nom: String, chemin: String, tab: Array[(String, (Double, Double, Double))]): String = {
    val writer = new PrintWriter(new File(chemin + nom + ".csv"))
    writer.write("label,min,max,moy\n")

    for (i <- 0 to tab.length-1) {
      writer.write(tab(i)._1 + "," + tab(i)._2._1 + "," + tab(i)._2._2 + "," + tab(i)._2._3 + "\n")
    }

    writer.close()

    nom + ".csv"
  }
}
