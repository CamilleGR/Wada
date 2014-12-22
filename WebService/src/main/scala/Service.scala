import java.io._

import akka.actor.Actor
import spray.http.MediaTypes._
import spray.routing._

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ServiceActor extends Actor with Service {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait Service extends HttpService {

  def traitementPost(dem : String) : Unit ={
    // File f = new File("test")
    if(dem.equals("statistiques")){
      //File f = Fonction de Spark Permettant de faire les statistiques
    }else if (dem.equals("listeAttributs")){
      //File f = Fonction de Spark renvoyant la liste des attributs d'un fichier
    }
    // f
  }

  def spark() = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
    val textFile = sc.textFile("scripts/data1.csv")
    textFile.collect()
  }

  //val writer = new PrintWriter(new File("test.txt" ))
  //writer.write("Hello Scala") // Exemple concret pour l'écriture d'un fichier avec scala ... Difficulté 5 étoiles
  //writer.close()


  val myRoute =
    path("") {
      post { //On définie ce qui est envoyé SI on utilise la méthode POST
        formFields("demande", "nomFichier", "attribut", "segment".as[Int]) { (demande, nomFichier, attribut, segment) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields
          var test = spark()
          respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
            complete {
              // on ajoute un bloc {} pour mettre notre variable
              <html>
                <body>
                  <p>Utilisation de POST :</p>
                  <p>{test}</p>
                  <p>{demande} {nomFichier} {attribut} {segment}</p>
                </body>
              </html>
            }
          }
        }
      }
    }
}