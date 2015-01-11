package fonctions

object Fichier {
  /*
  Fonction qui retourne l'extension d'un nom de fichier
  @args :
  name: String      -> nom du fichier
  @returns: String  -> extension du fichier
  */
  def extension(name: String): String = {
    var ret = ""
    for(i<- name.lastIndexOf('.')+1 to name.length-1 ){
      ret += name.charAt(i);
    }
    return ret;
  }
}
