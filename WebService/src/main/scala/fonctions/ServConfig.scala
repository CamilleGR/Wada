import java.io.File
import java.io._





class ServConfig{

  var cheminCible = "WebService/" //Chemin vers lequel sera envoyé le csv generé
  var lienCibleAttributs = "http://localhost/BD/SiteIntegration/sondage.php" //Le lien vers lequel sera envoyé le get contenant la liste des attributs
  var lienCibleStats = "http://localhost/BD/SiteIntegration/graphe.php" //Le lien vers lequel sera envoyé le get contenant le nom du fichier
  var cheminSource = "scripts/" //Le chemin ou serons récupérés les fichiers Big-Data

  def conf():Unit = {

    var f = new File("config")
    
    if(!f.exists()){
      f.createNewFile();
      var w = new FileWriter(f)
      w.write("CHEMIN_CIBLE="+cheminCible+"\n")
      w.write("LIEN_CIBLE_ATTRIBUTS="+lienCibleAttributs+"\n")
      w.write("LIEN_CIBLE_STATS="+lienCibleAttributs+"\n")
      w.write("CHEMIN_SOURCE="+cheminSource+"\n")
      w.close()
    }else {


      var br = new BufferedReader(new FileReader(f))
      var temp = br.readLine()

      while (temp != null) {
      
        if (temp.startsWith("CHEMIN_CIBLE=")) {
         	 this.cheminCible = temp.replace("CHEMIN_CIBLE=", "")
        } else if (temp.startsWith("LIEN_CIBLE_ATTRIBUTS=")) {
          	this.lienCibleAttributs = temp.replace("LIEN_CIBLE_ATTRIBUTS=", "")
        } else if (temp.startsWith("LIEN_CIBLE_STATS=")) {
          this.lienCibleStats = temp.replace("LIEN_CIBLE_STATS=", "")
        } else if (temp.startsWith("CHEMIN_SOURCE=")) {
         	 this.cheminSource = temp.replace("CHEMIN_SOURCE=", "")
        }
        temp = br.readLine()
        }
        br.close()
  

	println("\n\n////////////////////////////////////////////////////////////\n////////// Initialisation du service web //////////////\n///////////////////////////////////////////////////");
	println("Chemin cible : "+this.cheminCible +"\nLien Cible Attributs: "+this.lienCibleAttributs+"\nLien Cible Statistiques: "+this.lienCibleStats+"\nChemin Source : "+this.cheminSource + "\n////////////////////////////////////////////////\n\n")
	    }


    }
  }

