//package fonctions

/**
 * Created by venessiel on 23/02/15.
 */
class TwitterStreamUtil extends java.io.Serializable {


  def timeFormat(str: String): String = {
    var tab = str.split(" ");
    tab(0) = tab(5)
    tab(4) = ""
    tab(5) = ""
    tab(3) = tab(3).substring(0, 5)
    if (tab(1).equals("Jan")) {
      tab(1) = "01"
    } else if (tab(1).equals("Feb")) {
      tab(1) = "02"
    } else if (tab(1).equals("Mar")) {
      tab(1) = "03"
    } else if (tab(1).equals("Apr")) {
      tab(1) = "04"
    } else if (tab(1).equals("May")) {
      tab(1) = "05"
    } else if (tab(1).equals("Jun")) {
      tab(1) = "06"
    } else if (tab(1).equals("Jul")) {
      tab(1) = "07"
    } else if (tab(1).equals("Aug")) {
      tab(1) = "08"
    } else if (tab(1).equals("Sep")) {
      tab(1) = "09"
    } else if (tab(1).equals("Oct")) {
      tab(1) = "10"
    } else if (tab(1).equals("Nov")) {
      tab(1) = "11"
    } else if (tab(1).equals("Dec")) {
      tab(1) = "12"
    }
    return tab.mkString("")
  }

}
