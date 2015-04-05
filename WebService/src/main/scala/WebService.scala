import java.util.Calendar
import akka.actor.Actor
import fonctions.{TraitementTweet, Traitement, TwitterStream}
import spray.http.MediaTypes._
import spray.http._
import spray.routing._
import spray.util.LoggingContext
import HttpMethods._
import spray.httpx.RequestBuilding._
import spray.http.DateTime


class WebServiceActor extends Actor with WebService {

  def actorRefFactory = context

  def receive = runRoute/*(handleExceptions(RouteExceptionHandler)*/(myRoute)//)

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
  var tw = new TwitterStream(traitement.sc)

  var cheminCible = servConf.cheminCible //Chemin vers lequel sera envoyé le csv generé
  var lienCibleAttributs = servConf.lienCibleAttributs //Le lien vers lequel sera envoyé le get contenant la liste des attributs
  var lienCibleStats = servConf.lienCibleStats //Le lien vers lequel sera envoyé le get contenant le nom du fichier
  var cheminSource = servConf.cheminSource //Le chemin ou serons récupérés les fichiers Big-Data

  tw.changePath(cheminSource + "listeStreams.txt");

  val myRoute = {
    path("attributs") {
      get {
        parameters("nomFichier") { nomFichier =>
          //Dans le cas d'une demande de la liste des attributs, on renvoit en GET les attributs separés par des virgules, ainsi que le nom du fichier
          respondWithMediaType(`application/json`) {
            complete {
              traitement.listeAttributs(cheminSource + "/" + nomFichier)
            }
          }
          //redirect({lienCibleAttributs} + "?attributs=" + {traitement.listeAttributs(cheminSource + "/" + nomFichier)} + "&nomFichier=" + {nomFichier}, StatusCodes.PermanentRedirect)
        }
      }
    } ~
      path("stats") {
        get {
          //On définie ce qui est envoyé SI on utilise la méthode POST
          parameters("nomFichier", "attribut", "segment".as[Int], "filtre") { (nomFichier, attribut, segment, filtre) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields
            //Dans le cas d'une demande des stats, on renvoit en GET le chemin du fichier crée contenant les stats, ainsi que le nombre de tuples lus, et (si la colonne est numerique) le minimum, le maximum, la moyenne
            //val fichier = traitement.traitementStats(cheminSource, cheminCible, nomFichier, attribut, segment, filtre)
            //val stats = if (fichier(2) != "") "&stats=" + fichier(2) else ""
            //val moy = (if (fichier(3) != "") "&moy=" + fichier(3) else "").replace(" ", "+")
            //val mediane = (if (fichier(4) != "") "&med=" + fichier(4) else "")
            //redirect({lienCibleStats} + "?fichier=" + {fichier(0)} + "&count=" + {fichier(1)} + {stats} + {moy} + {mediane}, StatusCodes.PermanentRedirect)
            respondWithMediaType(`application/json`) {
              complete {
                traitement.traitementStats(cheminSource, cheminCible, nomFichier, attribut, segment, filtre)
              }
            }
          }
        }
      } ~
      path("courbe") {
        get {
          parameters("nomFichier", "attribut1", "attribut2", "filtre") { (nomFichier, attribut1, attribut2, filtre) =>
            //val fichier = traitement.traitementGraphe(cheminSource, cheminCible, nomFichier, attribut1, attribut2, filtre)
            //redirect({lienCibleStats} + "?fichier=" + {fichier(0)} + "&count=" + {fichier(1)} + "&stats=" + {fichier(2)} + "&med=" + {fichier(3)}, StatusCodes.PermanentRedirect)
            respondWithMediaType(`application/json`) {
              complete {
                traitement.traitementGraphe(cheminSource, cheminCible, nomFichier, attribut1, attribut2, filtre)
              }
            }
          }
        }
      } ~
      path("kmeans") {
        get {
          parameters("nomFichier", "nbClusters".as[Int], "filtre") { (nomFichier, nbClusters, filtre) =>
            //val fichier = traitement.traitementKmeans(cheminSource, cheminCible, nomFichier, nbClusters, filtre)
            //redirect({lienCibleStats} + "?fichier=" + {fichier} + "&nbCentres=" + {nbClusters}, StatusCodes.PermanentRedirect)
            respondWithMediaType(`application/json`) {
              complete {
                traitement.traitementKmeans(cheminSource, cheminCible, nomFichier, nbClusters, filtre)
              }
            }
          }
        }
      } ~
      path("kmeans_Stats") {
        get {
          parameters("nomFichier", "nbClusters".as[Int], "attribut", "segment".as[Int], "filtre") { (nomFichier, nbClusters, attribut, segment, filtre) =>
            val dossier = traitement.traitementKmeansStats(cheminSource, cheminCible, nomFichier, nbClusters, attribut, segment, filtre)
            //redirect({lienCibleStats} + "?dossier=" + {dossier} + "&nbClusters=" + {nbClusters}, StatusCodes.PermanentRedirect)
            respondWithMediaType(`application/json`) {
              complete {
                traitement.traitementKmeansStats(cheminSource, cheminCible, nomFichier, nbClusters, attribut, segment, filtre)
              }
            }
          }
        }
      } ~
      path("stream") {
        // Pour lancer ou arrêter un flux
        get{

          parameter("hashtags") { (hashtags) =>

            var path = cheminSource+ "Stream/"+hashtags.split(";")(0).replace("#","") +"/minute".replace("#","").trim
            var rep = "{\"path\":\""+path+"\"}"
            tw.creerStream(hashtags.split(";") ,path)
            tw.lancerStream
            tw.estLance=true;
            respondWithMediaType(`application/json`){
                complete{
                  rep
                }
            }
      }}

      } ~
      path("stopstream") {
        get{
        	parameters("action"){(action) =>
          var rep = "{\n\"reponse\":\""+tw.estLance+"\"\n}"
          tw.stopStream
          tw.estLance=false;
          respondWithMediaType(`application/json`){
            complete {
              rep
            }
	   }
          }
        }
      }~
      path("evoTweet"){
        get {
          parameters("path","seg"){ (path,seg)=>
            var tt= new TraitementTweet(traitement.sc)
            respondWithMediaType(`application/json`) {
              complete {
                tt.traitementTweets(path,seg.toInt)
              }
            }
        }
      }
  }~
      path("associatedHashtags"){
        get {
          parameter("path"){ (path)=>
            var tt= new TraitementTweet(traitement.sc)
            respondWithMediaType(`application/json`) {
              complete {
                tt.traitementHashtags(path)
              }
            }
          }
        }
      }
    }
  }

