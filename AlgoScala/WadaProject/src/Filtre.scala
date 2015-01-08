import java.io._
import scala.util.matching.Regex.Match




//Update 08/01/2015 à 22h : La fonction n'est pas fonctionnel mais en cours du dev !

// In : a string "Score>200;Pays=russie;" Chaque filtre doit finir par ";" même si il y a rien après
//      Liste des opérateurs prit en charge : >= , <= , == , !=   [ Pour L'instant ]
//Out : " TwoDimensional Array "

def stringToArray(filtre :String):Array[Array[String]] = {

  var nbFiltre:Int =filtre.count(_ == ';')              // calcule le nombre de ; et donc le nb de filtre
  var tabFiltre = Array.ofDim[String](nbFiltre+1,2)     // crée un tableau 2 à dimmensions ou le nombre de ligne = ligneAttr+NbLigneFiltre
  tabFiltre(0)={Array("attribut","op","val")}

  var fullLineArray = filtre.split(";")                // split la chaine de charactères = 1 ligne = 1 filtre

  var operatorList="[<!>=]=".r                          // Liste des opérateurs pris en charge



  // Problème dans la boucle
  for(i <- 0 to nbFiltre)
  {
    var filtreLine:String=fullLineArray(i)                                                // On met notre ligne du array à une var de type String [Optionel]

    var operatorUsed=operatorList.findFirstIn(filtreLine).getOrElse("Aucun Op trouvé") // On detecte l'opérateur utilisé et avec cet opérateur on va faire un split

    var attrAndVar=fullLineArray(i).split(operatorUsed)                                  // return Array["Attribut","Valeur")
    print(attrAndVar(0))
    tabFiltre(i+1)={Array(attrAndVar(0),operatorUsed, attrAndVar(1))}                    // attrAndVar c'est l'attribut avant

  }


  return tabFiltre
}
