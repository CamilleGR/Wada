package fonctions

object Tableau {
  /*
  Fonction qui convertit les stats d'un tableau en pourcentage
  @args :
  tab: Array[(String,Int)]           -> tableau de stats
  @returns: Array[(String, Float)]  -> tableau convertit
  */
  def pourcentage(tab: Array[(String, Int)]): Array[(String, Float)]={
    var total=0;
    for(i<- 0 to tab.length-1){
      total+=tab(i)._2;
    }

    var tabR = new Array[(String,Float)](tab.length);
    for(i<- 0 to tab.length-1){
      tabR(i)=(tab(i)._1,tab(i)._2.toFloat/total.toFloat *100);
    }
    return tabR;
  }
}
