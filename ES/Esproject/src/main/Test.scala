package main

import org.elasticsearch.node.NodeBuilder.nodeBuilder
import scala.collection.mutable.HashMap

class Test {
  
  val node = nodeBuilder().client(true).clusterName("elasticsearch").node()
    val client = node.client()
    node.close()
    
    
       

  val json = "{" +
        "\"user\":\"kimchy\"," +
        "\"postDate\":\"2013-01-30\"," +
        "\"message\":\"trying out Elasticsearch\"" +
    "}";
  
  
val x = new HashMap[Int,String]()  { override def default(key:Int) = "-" }
x += (1 -> "b", 2 -> "a", 3 -> "c")

  
}