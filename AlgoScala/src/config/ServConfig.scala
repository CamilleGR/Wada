import java.io.File
import java.io._

/**
 * Created by venessiel on 31/01/15.
 */
class ServConfig{

  var cheminCible = "../SiteIntegration/" //Le lien vers lequel sera envoyé le get contenant le nom du fichier/la liste des attributs
  var lienCible = "http://localhost/BD/SiteIntegration/" //Le lien vers lequel sera envoyé le get contenant le nom du fichier/la liste des attributs
  var cheminSource = "scripts/" //Le chemin ou serons récupérés les fichiers Big-Data

  import java.io.File
  import java.io._





  class ServConfig{

    var cheminCible = "../SiteIntegration/" //Le lien vers lequel sera envoyé le get contenant le nom du fichier/la liste des attributs
    var lienCible = "http://localhost/BD/SiteIntegration/" //Le lien vers lequel sera envoyé le get contenant le nom du fichier/la liste des attributs
    var cheminSource = "scripts/" //Le chemin ou serons récupérés les fichiers Big-Data

    def conf():Unit = {

      var f = new File("conf")

      if(!f.exists()){
        f.createNewFile();
        var w = new FileWriter(f)
        w.write("CHEMIN_CIBLE="+cheminCible)
        w.write("LIEN_CIBLE="+lienCible)
        w.write("CHEMIN_SOURCE="+cheminSource)
        w.close()
      }else {


        var br = new BufferedReader(new FileReader(f))
        var temp = br.readLine()

        while (temp != null) {
          if (temp.equals("CHEMIN_CIBLE=")) {
            this.cheminCible = temp.replace("CHEMIN_CIBLE=", "")
          } else if (temp.equals("LIEN_CIBLE=")) {
            this.lienCible = temp.replace("LIEN_CIBLE=", "")
          } else if (temp.equals("CHEMIN_SOURCE=")) {
            this.cheminSource = temp.replace("CHEMIN_SOURCE=", "")
          }
          temp = br.readLine()
        }
        br.close()


        println("\n\n////////////////////////////////////////////////////////////\n////////// Initialisation du service web //////////////\n///////////////////////////////////////////////////");
        println("Chemin cible : "+this.cheminCible +"\n Lien Cible : "+this.lienCible+"\n Chemin Source : "+this.cheminSource + "\n ////////////////////////////////////////////////\n\n")
      }
    }
  }


