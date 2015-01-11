import java.io._
import scala.util.matching.Regex.Match




//Update 10/01/2015 à 01:50 : La fonction est  fonctionnel !

// In : a string "Score>200;Pays=russie;" Chaque filtre doit finir par ";" même si il y a rien après
//      Liste des opérateurs prit en charge : >= , <= , = , != , > , <   [ Pour L'instant ]
//Out : " TwoDimensional Array "


def stringToArray(filtre :String):Array[Array[String]] = {

  var nbFiltre:Int =filtre.count(_ == ';')+1 // calcule le nombre de ; et donc le nb de filtre
  var tabFiltre = Array.ofDim[Array[String]](nbFiltre) // crée un tableau 2 à dimensions ou le nombre de ligne = ligneAttr+NbLigneFiltre

  var fullLineArray = filtre.split(";")// split la chaine de charactères = 1 ligne = 1 filtre

  var operatorList="([<!>]?=)|([<>])".r // Liste des opérateurs pris en charge , le .r le rend comme une expréssion REGEX / ancien regex : [<!>=]=

  for(i <- 1 to nbFiltre)
  {
    var filtreLine=fullLineArray(i-1)
    var operatorUsed=operatorList.findFirstIn(filtreLine).getOrElse("Aucun Op trouvé") // On detecte l'opérateur utilisé et avec cet opérateur on va faire un split
    var attrAndVar =fullLineArray(i-1).split(operatorUsed)
    tabFiltre(i-1)={Array(attrAndVar(0),operatorUsed, attrAndVar(1))}

  }

   tabFiltre
}

def comparaison(a:Int ,b:Int,op:String): Boolean =
{
  if(op.equals(">")) 				 a>b
  else if (op.equals(">="))  a>=b
  else if (op.equals("<")) 	 a<b
  else if (op.equals("<="))  a<=b
  else if (op.equals("!="))  a!=b
  else if (op.equals("="))	 a==b
  else
    false
}
