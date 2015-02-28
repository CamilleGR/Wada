package Features

import org.elasticsearch.action.delete.{DeleteResponse, DeleteRequestBuilder}
import org.elasticsearch.action.deletebyquery.{DeleteByQueryResponse, DeleteByQueryRequestBuilder}
import org.elasticsearch.client.Client

/**
 * Created by Yosaii on 05/02/2015.
 * Documentation will be added later
 */
class Delete {


  def deleteJsonFile(client:Client,indexf: String, typef: String, idf: String): DeleteResponse = {

    val delete = client.prepareDelete(indexf, typef, idf).execute().actionGet()
   delete
  }

  def deleteJsonFilebyquery(client:Client,query:String):DeleteByQueryResponse = {
    val delete = client.prepareDeleteByQuery(query).execute().actionGet()
    delete

  }
}
