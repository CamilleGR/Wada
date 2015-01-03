package testSpark

import java.io._

import akka.actor.Actor
import org.apache.spark.sql.catalyst.types.IntegerType
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import spray.http.MediaTypes._
import spray.routing._
import org.apache.spark.sql.{SchemaRDD, SQLContext}

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class TestSparkActor extends Actor with TestSpark {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait TestSpark extends HttpService {

  val conf = new SparkConf().setAppName("test").setMaster("local")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
  val function = new SparkFonction() //Pour executer les fonctions dans la classe SparkFonction

  /*
Fonction qui retourne l'extension d'un nom de fichier
@args :
name:String -> nom du fichier
@returns: String -> extension du fichier
*/
  def getExtension(name:String):String = {
    var ret = ""
    for(i<- name.lastIndexOf('.')+1 to name.length-1 ){
      ret += name.charAt(i);
    }
    return ret;
  }

  /*
  Fonction qui retourne la liste des attributs d'un fichier
  @args :
  file:String -> nom du fichier
  @returns: String -> liste des atributs séparés par des virgules
  */
  def listeAttributs(file:String):String = {
    var ret = "";
    val extension = getExtension(file)

    if ( extension=="csv" || extension=="tsv" ) { //Si c'est un csv ou tsv
      val textfile = sc.textFile("scripts/" + file);
      ret = textfile.first(); //On charge la ligne de l'entête (la première)
    }
    else if (extension == "json") {
      val textFile = sqlContext.jsonFile("scripts/" + file)
      var tabR = new Array[String](0)
      textFile.schema.fieldNames.foreach(r => tabR :+= r) //On récupère les attributs
      ret = tabR.mkString(",") //On les associe avec un separateur
    }

    return ret;
  }

  def traitementPost(demande:String, nomFichier:String, attribut:String, segment:Int) = {
    var array:Array[String] = null
    var tab:Array[(String,Int)] = new Array[(String, Int)](0)

    if (getExtension(nomFichier)=="csv" || getExtension(nomFichier)=="tsv") { //Si c'est un csv ou tsv...
      val textFile = sc.textFile("scripts/" + nomFichier) //On charge le fichier avec spark, le type de textFile est RDD[Array[String]]
      val sep = function.separateur(textFile) //On récupère le séparateur du csv/tsv
      val data = textFile.map(_.split(sep)) //On crée un RDD[Array[String]] qui correspond, en fonction du separateur
      val col = function.colAttribut(data, attribut) //On récupère le numero de colonne

      if (function.colNumerique(data,col))
        tab = function.segmentNum(segment, col, data) // <- Si c'est un Réel on execute segmentNum
      else
        tab = function.segmentStringArray(segment,col,data) // <- Si c'est un String on execute segmentStringArray
    }
    else if (getExtension(nomFichier)=="json") {
      import sqlContext.createSchemaRDD
      val textFile = sqlContext.jsonFile("scripts/" + nomFichier)//On charge le fichier avec spark, le type de textFile est SchemaRDD
      textFile.registerTempTable("textFile") //On enregistre le SchemaRDD comme une table SQL

      if (function.colNumerique(sqlContext, textFile, attribut))
        tab = function.segmentNum(sqlContext,segment,attribut,"textFile") // <- Si c'est un Réel on execute segmentNum
      else
        tab = function.segmentStringArray(sqlContext,segment,attribut,"textFile") // <- Si c'est un String on execute segmentStringArray
    }
    val tabPrc = function.prcTab(tab) //On convertit les valeurs en pourcentage
    val cheminFichierStats = function.creerCsv(nomFichier + attribut, "WebService/src/test/", tabPrc) //On crée le fichier CSV à renvoyer à la webApp

    cheminFichierStats //On renvoit le chemin du fichier crée
  }

  val myRoute =
    path("") {
      post { //On définie ce qui est envoyé SI on utilise la méthode POST
        formFields("demande", "nomFichier", "attribut", "segment".as[Int]) { (demande, nomFichier, attribut, segment) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields
          respondWithMediaType(`text/html`) {
            if(demande.equals("listeAttributs")){ //Dans le cas d'une demande de la liste des attributs, on renvoit en GET les attributs separés par des virgules
                 complete {
                    <html>
                      <body>
                        <h1>Chargement de la liste des attributs, vous serez redirigé sur la page ...</h1>
                        <script>window.location='http://cheminVerslapage?attributs={listeAttributs(nomFichier)}'</script>
                      </body>
                    </html>
                  }

            }else if(demande.equals("statistiques")){ //Dans le cas d'une demande des stats, on renvoit en GET le chemin du fichier crée contenant les stats
                  val fichier = traitementPost(demande, nomFichier, attribut, segment)
                  complete {
                      <html>
                        <body>
                          <h1>Chargement des statistiques, vous serez redirigé sur la page ...</h1>
                          <script>window.location='http://cheminVerslapage?fichier={fichier}'</script>
                        </body>
                      </html>
              }

            }else { //Bon ça c'est Camille, voila...
                    complete {
                      <html>
                        <body>
                          <h1>Error 418 : I'm a Tea Pot</h1>
                        </body>
                     </html>
                    }
            }
          }
        }
      }
    }
}
