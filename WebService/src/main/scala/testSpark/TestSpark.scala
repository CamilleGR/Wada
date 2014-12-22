package testSpark

import java.io._

import akka.actor.Actor
import org.apache.spark.{SparkConf, SparkContext}
import spray.http.MediaTypes._
import spray.routing._

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

  def traitementPost(demande:String, nomFichier:String, attribut:String, segment:Int) = {

    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
    val textFile = sc.textFile("scripts/" + nomFichier + ".csv").collect()
    val data = textFile.map(_.split(",")) // Convertir un fichier csv en tableau

    var writer:PrintWriter = null
    var nameFile:String = null
    if (demande.equals("satistiques")) { // Création d'un fichier csv contenant les statistiques pour l'attribut donnée
      nameFile = nomFichier + attribut + ".csv"
      writer = new PrintWriter(new File("WebService/src/test/" + nameFile))
      // Algorithme de calcul des statistiques à faire
    }
    else if (demande.equals("listeAttributs")) { // Création d'un fichier txt contenant tous les attributs ligne par ligne
      nameFile = "attributs" + nomFichier + ".txt"
      writer = new PrintWriter(new File("WebService/src/test/" + nameFile))
      data.apply(0).foreach{r => writer.write(r + "\n")}
      writer.close()
    }
    
    sc.stop()
  }

  val myRoute =
    path("") {
      post { //On définie ce qui est envoyé SI on utilise la méthode POST
        formFields("demande", "nomFichier", "attribut", "segment".as[Int]) { (demande, nomFichier, attribut, segment) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields
          traitementPost(demande, nomFichier, attribut, segment)
          respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
            complete {
              // on ajoute un bloc {} pour mettre notre variable
              <html>
                <body>
                  <h1>Le fichier crée se trouve dans WebService/src/test</h1>
                </body>
              </html>
            }
          }
        }
      }
    }
}