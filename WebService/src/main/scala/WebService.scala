import java.util.Calendar

import akka.actor.Actor
import fonctions.{Traitement}
import spray.http.MediaTypes._
import spray.http._
import spray.routing._
import spray.util.LoggingContext
import HttpMethods._
import spray.httpx.RequestBuilding._
import fonctions.TwitterStream
import spray.http.DateTime


class WebServiceActor extends Actor with WebService {

  def actorRefFactory = context

  def receive = runRoute(handleExceptions(RouteExceptionHandler)(myRoute))

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

  val servConf = new ServConfig
  servConf.conf

  val traitement = new Traitement

  var cheminCible = servConf.cheminCible //Chemin vers lequel sera envoyé le csv generé
  var lienCibleAttributs = servConf.lienCibleAttributs //Le lien vers lequel sera envoyé le get contenant la liste des attributs
  var lienCibleStats = servConf.lienCibleStats //Le lien vers lequel sera envoyé le get contenant le nom du fichier
  var cheminSource = servConf.cheminSource //Le chemin ou serons récupérés les fichiers Big-Data

  val myRoute = {
    path("attributs") {
      post {
        formFields("nomFichier") { nomFichier =>
          //Dans le cas d'une demande de la liste des attributs, on renvoit en GET les attributs separés par des virgules, ainsi que le nom du fichier
          redirect({lienCibleAttributs} + "?attributs=" + {traitement.listeAttributs(nomFichier)} + "&nomFichier=" + {nomFichier}, StatusCodes.PermanentRedirect)
        }
      }
    } ~
      path("stats") {
        post {
          //On définie ce qui est envoyé SI on utilise la méthode POST
          formFields("nomFichier", "attribut", "segment".as[Int], "filtre") { (nomFichier, attribut, segment, filtre) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields
            //Dans le cas d'une demande des stats, on renvoit en GET le chemin du fichier crée contenant les stats, ainsi que le nombre de tuples lus, et (si la colonne est numerique) le minimum, le maximum, la moyenne
            val fichier = traitement.traitementStats(cheminSource, cheminCible, nomFichier, attribut, segment, filtre)
            val stats = if (fichier(2) != "") "&stats=" + fichier(2) else ""
            val moy = (if (fichier(3) != "") "&moy=" + fichier(3) else "").replace(" ", "+")
            val mediane = (if (fichier(4) != "") "&med=" + fichier(4) else "")
            redirect({lienCibleStats} + "?fichier=" + {fichier(0)} + "&count=" + {fichier(1)} + {stats} + {moy} + {mediane}, StatusCodes.PermanentRedirect)
          }
        }
      } ~
      path("graph") {
        post {
          formFields("nomFichier", "attribut1", "attribut2", "filtre") { (nomFichier, attribut1, attribut2, filtre) =>
            val fichier = traitement.traitementGraphe(cheminSource, cheminCible, nomFichier, attribut1, attribut2, filtre)
            redirect({lienCibleStats} + "?fichier=" + {fichier(0)} + "&count=" + {fichier(1)} + "&stats=" + {fichier(2)} + "&med=" + {fichier(3)}, StatusCodes.PermanentRedirect)
          }
        }
      } ~
      path("kmeans") {
        post {
          formFields("nomFichier", "nbClusters".as[Int], "filtre") { (nomFichier, nbClusters, filtre) =>
            val fichier = traitement.traitementKmeans(cheminSource, cheminCible, nomFichier, nbClusters, filtre)
            redirect({lienCibleStats} + "?fichier=" + {fichier} + "&nbCentres=" + {nbClusters}, StatusCodes.PermanentRedirect)
          }
        }
      } ~
      path("kmeans_Stats") {
        post {
          formField("nomFichier", "nbClusters".as[Int], "attribut", "segment".as[Int], "filtre") { (nomFichier, nbClusters, attribut, segment, filtre) =>
            val dossier = traitement.traitementKmeansStats(cheminSource, cheminCible, nomFichier, nbClusters, attribut, segment, filtre)
            redirect({lienCibleStats} + "?dossier=" + {dossier} + "&nbClusters=" + {nbClusters}, StatusCodes.PermanentRedirect)
          }
        }
      } ~
      path("stream") {
        // Pour lancer ou arrêter un flux
        post {
          formFields("hashtags","temps"){ (hashtags,temps) =>
            var tw = new TwitterStream(traitement.sc)
            var cal = Calendar.getInstance()

            var path = cheminSource+ "Stream/"+hashtags.split(";")(0).replace("#","").trim+"_"+cal.getTimeInMillis
            tw.creerStream(hashtags.split(";") ,path, temps.toInt)

            respondWithMediaType(`text/html`) {
              complete{
                <html>
                  <body>
                    <h1>Votre stream a bien été créé !</h1>
                      <p>Enregistré dans : {path}</p>
                      <p>Durée : {temps}</p>
                      <p>Vous serez redirigé dans quelque secondes ...</p>
                    <script>
                    sleep(10);
                    document.location.href="http://www.google.com";
                    </script>
                  </body>
                </html>
              }
            }
          }
        }
      }
  }
}
