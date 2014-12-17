package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import java.io._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val myname = "Camille"

  val writer = new PrintWriter(new File("test.txt" ))
  writer.write("Hello Scala") // Exemple concret pour l'écriture d'un fichier avec scala ... Difficulté 5 étoiles
  writer.close()


  val myRoute =
    path("") {
      get { //On définie ce qui est envoyé SI on utilise la méthode GET
        parameter("user", "password") { (user, password) => //Les paramètres de la méthode GET sont mentionnés par la fonction formFields
          respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
           complete {
             // on ajoute un bloc {} pour mettre notre variable : Voir ligne 34
             <html>
               <body>
                 <h1>Dis bonjour à
                   {myname}
                   !!</h1>

                 <p>Utilisation de GET :</p>
                 <p>user : {user}</p>
                 <p>password : {password}</p>
               </body>
             </html>
           }
          }
        }
      }~ post { //On définie ce qui est envoyé SI on utilise la méthode POST
        formFields("user", "password") { (user, password) => //Les paramètres de la méthode POST sont mentionnés par la fonction formFields
          respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
            complete {
              // on ajoute un bloc {} pour mettre notre variable : Voir ligne 34
              <html>
                <body>
                  <h1>Dis bonjour à
                    {myname}
                    !!</h1>
                  <p>Utilisation de POST :</p>
                  <p>user : {user}</p>
                  <p>password : {password}</p>
                </body>
              </html>
            }
          }
        }
      }
    }
}