
import org.elasticsearch.client.Client
import org.elasticsearch.node.NodeBuilder.nodeBuilder



object Setup {

  def Nodebuilder(nomDuCluster:String): Client ={

    val node = nodeBuilder().client(true).clusterName(nomDuCluster).node()
    val client = node.client()
    node.close()
    client
  }




}

