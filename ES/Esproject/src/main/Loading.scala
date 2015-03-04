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
  /**
   * Recharger un fichier depuis un répertoire local
   * 
   *  @param : Nom du fichier
   *  @return: True or false
   */
  
  def loadFile(fileName:String): Boolean = {
    
   val lines = fromFile(fileName).getLines
   var fileExtensions="csv|json|tsv".r// not an optimal regex  
   var fileExtension=fileExtensions.findFirstIn(fileName).getOrElse("File type not supported")
   
   
   if (fileExtension=="csv") csv(fileName)
   else if (fileExtension=="tsv") tsv(fileName)
   else if (fileExtension=="json") json(fileName)
     
   true
  }
    /**
   * Traitement d'un fichier CSV
   * 
   *  @param : Nom du fichier
   *  @return: True or false
   */
  def csv(fileName:String): Boolean = {true}
    /**
   * Traitement d'un fichier tsv
   * 
   *  @param : Nom du fichier
   *  @return: True or false
   */
  def tsv(fileName:String): Boolean = {true}
    /**
   * Traitement d'un fichier Json
   * 
   *  @param : Nom du fichier
   *  @return: True or false
   */
  def json(fileName:String): Boolean = {true}

}
