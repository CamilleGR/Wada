import akka.actor.Actor
import fonctions.{Traitement}
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.routing._
import spray.util.LoggingContext

class WebServiceActor extends Actor with WebService {

  def actorRefFactory = context

  def receive = runRoute((handleExceptions(RouteExceptionHandler))(myRoute))

  implicit def RouteExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: Exception =>
        respondWithMediaType(`text/html`) {
          complete {
            <html>
              <body>
                <h1>Error 418 : I'm a Tea Pot</h1>
                <p><i>{e.toString}</i></p>
              </body>
            </html>
          }
        }
    }
}

trait WebService extends HttpService {

  val traitement = new Traitement
  val cheminCible = "AlgoScala/WadaProject/res/" //Le lien vers lequel sera envoyé le get contenant le nom du fichier/la liste des attributs
  val lienCible = "http://localhost/BD/WebService/src/TestForm/form.php" //Le lien vers lequel sera envoyé le get contenant le nom du fichier/la liste des attributs
  val cheminSource = "scripts/" //Le chemin ou serons récupérés les fichiers Big-Data

  val myRoute =
    path("") {
      post { //On définie ce qui est envoyé SI on utilise la méthode POST
        formFields("demande", "nomFichier", "attribut", "segment".as[Int], "filtre") { (demande, nomFichier, attribut, segment, filtre) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields

          if(demande.equals("listeAttributs")){ //Dans le cas d'une demande de la liste des attributs, on renvoit en GET les attributs separés par des virgules, ainsi que le nom du fichier
              redirect({lienCible} + "?attributs=" + {traitement.listeAttributs(nomFichier)} + "&nomFichier=" + {nomFichier}, StatusCodes.PermanentRedirect)
          }

          else if(demande.equals("statistiques")){ //Dans le cas d'une demande des stats, on renvoit en GET le chemin du fichier crée contenant les stats, ainsi que le nombre de tuples lus, et (si la colonne est numerique) le minimum, le maximum, la moyenne
            val fichier = traitement.traitementPost(cheminSource, cheminCible, nomFichier, attribut, segment, filtre)
            val stats = if(fichier(2)!="") "&stats=" + fichier(2) else ""
            redirect({lienCible} + "?fichier=" + {fichier(0)} + "&count=" + {fichier(1)} + {stats}, StatusCodes.PermanentRedirect)
          }

          else throw new Exception()
        }
      }
    }
}

