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

  def listeAttributs(file:String):String = "Liste a definir" // Fonction à définir, mais là j'ai la flemme - signé Camille

  def traitementPost(demande:String, nomFichier:String, attribut:String, segment:Int) = {

    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
    val textFile = sc.textFile("scripts/" + nomFichier + ".csv")

    if (demande.equals("statistiques")) { // Création d'un fichier csv contenant les statistiques pour l'attribut donnée

      val data = textFile.map(_.split(",")).collect()

      val function = new SparkFonction()
      val tab = function.segmentNum(segment, 1, data) // pour l'instant le calcule ne se fait que pour l'attribut de la deuxième colonne, donc fonction de recherche de la colonne de l'attribut à faire
      val tabPrc = function.prcTab(tab)
      function.creerCsv(nomFichier + attribut, "WebService/src/test/", tabPrc)
    }
    else if (demande.equals("listeAttributs")) { // Création d'un fichier txt contenant tous les attributs ligne par ligne
      val nameFile = "attributs" + nomFichier + ".txt"
      val writer = new PrintWriter(new File("WebService/src/test/" + nameFile))

      val data = textFile.first().split(',')
      data.foreach{r => writer.write(r + "\n")}

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
            if(demande.equals("listeAttributs")){
                 complete {
                    // on ajoute un bloc {} pour mettre notre variable
                    <html>
                      <body>
                       <h1>Chargement de la liste des attributs, vous serez redirigé sur la page ...</h1>
                      </body>
                    </html>
                  }

            }else if(demande.equals("statistiques")){
                    complete {
                      <html>
                        <body>
                          <h1>Chargement des statistiques, vous serez redirigé sur la page ...</h1>
                          <script> Document.setLocation.href='cheminVerslapage?attributs="{listeAttributs(nomFichier)}"'</script>
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