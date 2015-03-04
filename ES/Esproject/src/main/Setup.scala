package main

import org.elasticsearch.node.NodeBuilder.nodeBuilder
import org.elasticsearch.client.Client
import org.elasticsearch.common.xcontent.XContentFactory._

class Setup {

  def Tester (nomDuCluster:String): Boolean = {

    val node = nodeBuilder().client(true).clusterName(nomDuCluster).node()
    val client = node.client()
    
    
    
    // Indexer man
    var response = client.prepareIndex("twitter", "tweet", "1")
        .setSource(jsonBuilder()
                    .startObject()
                        .field("user", "kimchy")
                        .field("postDate", "14/10/01")
                        .field("message", "trying out Elasticsearch")
                    .endObject()
                  )
        .execute()
        .actionGet();
    
    
    
    
    true
 
  }
  
}