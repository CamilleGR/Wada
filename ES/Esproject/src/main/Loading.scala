/**
 *
 */
package main
import scala.io.Source._

/**
 * @author Yosaii
 *
 */


class Loading {
  
  def loadFile(fileName:String): Boolean = {
    
   val lines = fromFile(fileName).getLines
   var fileExtensions="csv|json|tsv".r// not an optimal regex  
   var fileExtension=fileExtensions.findFirstIn(fileName).getOrElse("File type not supported")
   
   
   if (fileExtension=="csv") csv(fileName)
   else if (fileExtension=="tsv") tsv(fileName)
   else if (fileExtension=="json") json(fileName)
     
   true
  }
  
  def csv(fileName:String): Boolean = {true}
  def tsv(fileName:String): Boolean = {true}
  def json(fileName:String): Boolean = {true}

}