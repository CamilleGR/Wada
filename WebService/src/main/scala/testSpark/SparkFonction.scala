package testSpark

import java.io.{File,PrintWriter}
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd._
import java.lang.Math

import org.apache.spark.sql.{SQLContext, SchemaRDD}

class SparkFonction {


  /*
  Fonction POUR LES FICHIERS CSV retournant un Array[(String,Int)] qui regroupe les valeurs NUMERIQUE
  d'une colonne d'un autre tableau en plusieurs segments (ex : 0 à 10, 10 à 20 ...)
  @args :
  seg:Int -> Nombre de segments demandé par l'utilisateur.
  col:Int -> Numero de la colonne sur laquelle on veut faire le traitement
  tab:RDD[Array[String]] -> RDD à 2 dimensions de String ( qui sont en réalité des entiers )
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def segmentNum(seg:Int,col:Int, tab:RDD[Array[String]]): Array[(String,Int)] = {

    if(seg<=0)return null;

    val tempTab = tab.map(r => r(col))  //On ne garde que la colonne en question

    val header = tempTab.first()
    val data = tempTab.filter(line => !line.equals(header)) //On retire l'entête du tableau

    val min = data.reduce((a, b) => Math.min(a.toDouble, b.toDouble).toString).toDouble //On calcule la valeur minimale
    val max = data.reduce((a, b) => Math.max(a.toDouble, b.toDouble).toString).toDouble //et maximale

    var tabR = new Array[(String,Int)](0)

    for(i <- 1 to seg) {
      //On regroupe les valeurs en seg parties distincts, elles sont donc regroupés par parties comprises entre min+(max/seg)*(j-1) et min+(max/seg)*(i)
      tabR :+= ((min+ (max/seg)*(i-1)) + " à " + (min+ (max/seg)*i),
        data.filter(r => r.toDouble >= min+(max/seg)*(i-1) &&  r.toDouble <= min+(max/seg)*i).count().toInt)
    }

    return tabR
  }

  /*
  Equivalent de la fonction précedente pour les fichiers de type JSON
  @args :
  sqlContext:SQLContext -> le SQLContext pour l'execution de requete SQL
  seg:Int -> Nombre de segments demandé par l'utilisateur.
  col:String -> Nom de la colonne sur laquelle on veut faire le traitement
  tab:String -> Nom de la table SQL
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def segmentNum(sqlContext:SQLContext,seg:Int,col:String, tab:String): Array[(String,Int)] = {

    if(seg<=0)return null;

    val min = sqlContext.sql("SELECT min(" + col + ") FROM " + tab).map(t => t(0).toString).first().toDouble //On calcule la valeur minimale
    val max = sqlContext.sql("SELECT max(" + col + ") FROM " + tab).map(t => t(0).toString).first().toDouble //et maximale

    //En JSON, l'entête ne se trouve pas dans le SchemaRDD, donc pas besoin de le retirer

    var nb:Int = 0
    var temp = min+(max/seg)

    var tabR = new Array[(String,Int)](0)

    for(i <- 1 to seg) {
      //On regroupe les valeurs en seg parties distincts, elles sont donc regroupés par parties comprises entre min+(max/seg)*(j-1) et min+(max/seg)*(i)
      nb = sqlContext.sql("SELECT count(*) FROM " + tab + " WHERE " + col + ">=" + temp*(i-1) + " AND " + col + "<=" + (temp*i)).map(t => t(0).toString).first().toInt //On execute la requete SQL qui correspond
      tabR :+= ((min+ (max/seg)*(i-1)) + " à " + (min+ (max/seg)*i), nb)
    }

    return tabR
  }

  /*
  Fonction POUR LES FICHIERS CSV retournant un Array[(String,Int)] qui regroupe les chaines de caractères
  @args :
  seg:Int -> Nombre de segments demandé par l'utilisateur.
  col:Int -> Numero de la colonne sur laquelle on veut faire le traitement
  tab:RDD[Array[String]] -> RDD à 2 dimensions de String
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def segmentStringArray(seg:Int,col:Int,array:RDD[Array[String]]): Array[(String,Int)] = {

    val tempTab = array.map(r => r(col)) //On ne garde que la colonne sur laquel on veut effectuer nos stats
    val header = tempTab.first()
    val data = tempTab
      .filter(line => !line.equals(header)) //On retire l'entête...
      .map(r => (r,1))  //On convertit le RDD[String] en RDD[(String,Int)] pour l'execution des methodes tel que reduceByKey ou sortByKey
      .reduceByKey((x, y) => x + y) //On regroupe les valeurs egales dans la première colonne et on entre dans la deuxième le nombre de fois qu'elles aparaissent
      .map(t => (t._2, t._1)) //On inverse les colonnes pour trier en fonction de la deuxième colonne (sortByKey ne trie qu'en fonction de la première colonne)
      .sortByKey(false) //On trie en fonction des valeurs correspondant au nombre d'occurences afin de ne garder que les plus grandes

    val dataReduced = data.map(t => (t._2, t._1)) //On réinverse les colonnes pour avoir l'affichage normal du tableau

    if (seg >= data.count()) { //Si le nombre de segments est plus grand ou egal au nombre de ligne du tableau généré, on peut directement renvoyer toutes ses lignes
      return dataReduced.take(data.count().toInt)
    }
    else {                    //Sinon on prend les seg premières lignes et on en ajoute une pour toutes les autres
      var sum = 0
      dataReduced
        .map(t => t._2)
        .take(seg)
        .foreach(r => sum += r) //On calcule la somme des valeurs des seg permières lignes

      var tab = dataReduced.take(seg)
      tab :+= ("Reste",(array.count()-1-sum).toInt) //On ajoute une ligne avec pour valeur total-(sommes des seg premières lignes)

      return tab;
    }
  }

  /*
  Equivalent de la fonction précedente pour les fichiers de type JSON
  @args :
  sqlContext:SQLContext -> le SQLContext pour l'execution de requete SQL
  seg:Int -> Nombre de segments demandé par l'utilisateur.
  col:String -> Nom de la colonne sur laquelle on veut faire le traitement
  tab:String -> Nom de la table SQL
  @returns: Array[(String,Int)] -> Tableau regroupant les valeurs de la colonne $col du taleau $tab en $seg segment
  */
  def segmentStringArray(sqlContext:SQLContext, seg:Int,col:String,array:String): Array[(String,Int)] = {
    val total = sqlContext.sql("SELECT COUNT(*) FROM " + array).map(t => t(0).toString).first().toInt //On calcul le nombre total de ligne pour calculer la valeur de la dernière ligne
    val data = sqlContext.sql("SELECT " + col + ", COUNT(" + col + ") AS C FROM " + array + " GROUP BY " + col + " ORDER BY C DESC").map(t => (t(0).toString, t(1).toString.toInt)) //On regroupe les valeurs egales dans la première colonne et on entre dans la deuxième le nombre de fois qu'elles aparaissent

    if (seg >= data.count()) { //Si le nombre de segments est plus grand ou egal au nombre de ligne du tableau généré, on peut directement renvoyer toutes ses lignes
      return data.take(total.toInt)
    }
    else { //Sinon on prend les seg premières lignes et on en ajoute une pour toutes les autres
      var sum = 0
      data
        .map(t => t._2)
        .take(seg)
        .foreach(r => sum += r) //On calcule la somme des valeurs des seg permières lignes

      var tab = data.take(seg)
      tab :+= ("Reste",(total-sum)) //On ajoute une ligne avec pour valeur total-(sommes des seg premières lignes)

      return tab;
    }
  }
  
  /* 
  Fonction qui crée un fichier csv à partir d'un tableau à deux dimensions
  @args :
  nom:String -> Le nom du fichier à créer
  chemin:String -> Le chemin du fichier
  tab:Array[(String,Float)] -> tableau de stats
  @returns: String -> Chemin du fichier crée
  */
  def creerCsv(nom:String, chemin:String, tab:Array[(String, Float)]): String = {
    val writer = new PrintWriter(new File(chemin + nom + ".csv")) //On crée un fichier csv en Scala
    writer.write("categorie,valeur\n")  //On écrit son entête

    for (i <- 0 to tab.length-1) { //On écrit chacune des lignes
      writer.write(tab(i)._1 + "," + tab(i)._2 + "\n")
    }

    writer.close()

    chemin + nom + ".csv"
  }

  /*
  Fonction qui convertit les stats d'un tableau en pourcentage
  @args :
  tab:Array[(String,Int)] -> tableau de stats
  @returns: Array[(String, Float)] -> tableau convertit
  */
  def prcTab(tab:Array[(String, Int)]):Array[(String, Float)]={
    var total=0;
    for(i<- 0 to tab.length-1){
      total+=tab(i)._2;
    }

    var tabR = new Array[(String,Float)](tab.length);
    for(i<- 0 to tab.length-1){
      tabR(i)=(tab(i)._1,tab(i)._2.toFloat/total.toFloat *100);
    }
    return tabR;
  }

  /*
  Fonction qui retourne le separateur d'un fichier CSV/TSV
  @args :
  tab:RDD[String] -> RDD d'un csv/tsv
  @returns: AString -> le separateur (virgule, point-virgule ou tabulation)
  */
  def separateur(file:RDD[String]):String = {
    val header = file.first()

    if (header.contains(",")) return ","

    else if (header.contains(";")) return ";"

    else if (header.contains("\t")) return "\t"

    else return null
  }

  /*
  Fonction qui retourne le numero de colonne d'un attribut donné
  @args :
  tab:RDD[String] -> RDD d'un csv/tsv
  @returns: AString -> le separateur (virgule, point-virgule ou tabulation)
  */
  def colAttribut(file:RDD[Array[String]],attribut:String):Int = {
    val header = file.first()

    for (i <- 0 to header.length-1) {
      if (header(i).equals(attribut)) {
        return i
      }
    }

    return -1
  }
}