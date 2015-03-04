
/**
 *
 */
package main
import scala.io.Source._
import  org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.client.Client


/**
 * @author Yosaii
 *
 */
object Functionalities {
  
  
  var response = client.prepareIndex("twitter", "tweet", "1")
        .setSource(jsonBuilder()
                    .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                    .endObject()
                  )
        .execute()
        .actionGet();
  


}