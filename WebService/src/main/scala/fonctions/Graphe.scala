package fonctions

import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

object Graphe {

  /*
  Retourne un tableau distinct de tous les individus du rdd
  @args :
  rdd: RDD[Array[String]]         -> le rdd de tableau de String
  colUser1:Array[Int]             -> tableau regroupant l'ensemble des indices des colonnes définissant un premier individu
  colUser2: Int                   -> tableau regroupant l'ensemble des indices des colonnes définissant l'individu lié au premier individu (définit pas les indices colUser1)
  @returns: Array[Array[String]]  -> tableau distinct de tous les individus
  */
  def tableauDistincts(rdd: RDD[Array[String]], colUser1:Array[Int], colUser2:Array[Int]): Array[Array[String]] = {
    return rdd.map(tabColonnes(_, colUser1)).union(rdd.map(tabColonnes(_, colUser2))).distinct.collect
  }

  /*
  Retourne un tableau avec les valeurs des indices du tableau de colonnes
  @args :
  tab: Array[String]      -> un tableau de string
  colonnes: Array[Int]    -> le tableau des indices de colonnes
  @returns: Array[String] -> le tableau avec uniquement les valeurs de indices du tableau de colonnes
  */
  def tabColonnes(tab: Array[String], colonnes: Array[Int]): Array[String] = {
    var tab = Array[String]()
    for (i <- colonnes) {
      tab :+= tab(i)
    }
    tab
  }

  /*
  Retourne un VertexRDD pour la création du graphe
  @args :
  rdd: RDD[(Array[String], Array[String], String)]  -> le rdd des individus, chaque tableau contient un individu, le string est le type de relation entre les deux individus
  tabDistinct: Array[Array[String]]                 -> tableau de l'ensemble des individus
  @returns: RDD[(VertexId, Array[String])]          -> le VertexRDD
  */
  def vertexRDD(rdd: RDD[(Array[String], Array[String], String)], tabDistinct: Array[Array[String]]): RDD[(VertexId, Array[String])] = {
    return rdd.map(r => r._1).union(rdd.map(r => r._2)).distinct.map(r => ((tabDistinct.indexOf(r) + 1).toLong, r))
  }

  /*
  Retourne un EdgeRDD pour la création du graphe
  @args :
  rdd: RDD[(Array[String], Array[String], String)]  -> le rdd des individus, chaque tableau contient un individu, le string est le type de relation entre les deux individus
  tabDistinct: Array[Array[String]]                 -> tableau de l'ensemble des individus
  @returns: RDD[Edge[String]]                       -> le EdgeRDD
  */
  def edgeRDD(rdd: RDD[(Array[String], Array[String], String)], tabDistinct: Array[Array[String]]): RDD[Edge[String]] = {
    return rdd.map(r => {
      val vertex1 = (tabDistinct.indexOf(r._1)+1).toLong
      val vertex2 = (tabDistinct.indexOf(r._2)+1).toLong
      Edge(vertex1, vertex2, r._3)
    })
  }

  /*
  Retourne un graphe de relation entre les individus
  @args :
  rdd: RDD[Array[String]]                 -> un rdd de tableau de string
  colUser1: Int                           -> tableau regroupant l'ensemble des indices des colonnes définissant un premier individu
  colUser2: Int                           -> tableau regroupant l'ensemble des indices des colonnes définissant l'individu lié au premier individu (définit pas les indices colUser1)
  colRelation: Int                        -> indice de la colonne définissant le type de relation entre les deux individus
  @returns: Graph[Array[String], String]  -> Le graphe de relation
  */
  def graphe(rdd: RDD[Array[String]], colUser1:Array[Int], colUser2:Array[Int], colRelation:Int): Graph[Array[String], String] = {
    val tabDistinct = tableauDistincts(rdd, colUser1, colUser2)

    val data = rdd.map(r => (tabColonnes(r, colUser1), tabColonnes(r, colUser2), r(colRelation)))

    val vrdd = vertexRDD(data, tabDistinct)
    val erdd = edgeRDD(data, tabDistinct)

    return Graph(vrdd, erdd)
  }
}