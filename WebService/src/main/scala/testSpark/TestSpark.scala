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

  val conf = new SparkConf().setAppName("test").setMaster("local")
  val sc = new SparkContext(conf)

  def listeAttributs(file:String):String = {
    var ret = "";

    if (true) {
      // Ici il faudra traiter les différents types de fichier, pour l'instant on estime que c'est un .csv
      val textfile = sc.textFile("scripts/" + file);
      ret = textfile.first(); // trouver une solution pour ne pas charger TOUS le fichier mais juste la premiere ligne
    }

    return ret;
  }


  def traitementPost(demande:String, nomFichier:String, attribut:String, segment:Int) = {

    val textFile = sc.textFile("scripts/" + nomFichier)
    val data = textFile.map(_.split(",")).collect()

    val function = new SparkFonction()
    val tab = function.segmentNum(segment, 1, data) // pour l'instant le calcule ne se fait que pour l'attribut de la deuxième colonne, donc fonction de recherche de la colonne de l'attribut à faire
    val tabPrc = function.prcTab(tab)
    function.creerCsv(nomFichier + attribut, "WebService/src/test/", tabPrc)
  }

  val myRoute =
    path("") {
      post { //On définie ce qui est envoyé SI on utilise la méthode POST
        formFields("demande", "nomFichier", "attribut", "segment".as[Int]) { (demande, nomFichier, attribut, segment) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields
          respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
            if(demande.equals("listeAttributs")){
                 complete {
                    // on ajoute un bloc {} pour mettre notre variable
                    <html>
                      <body>
                        <h1>Chargement de la liste des attributs, vous serez redirigé sur la page ...</h1>
                        <script>window.location='http://cheminVerslapage?attributs={listeAttributs(nomFichier)}'</script>
                      </body>
                    </html>
                  }

            }else if(demande.equals("statistiques")){
                  traitementPost(demande, nomFichier, attribut, segment)
                  complete {
                      <html>
                        <body>
                          <h1>Chargement des statistiques, vous serez redirigé sur la page ...</h1>
                        </body>
                      </html>
              }

            }else {
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
