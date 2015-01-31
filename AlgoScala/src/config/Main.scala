/**
 * Created by venessiel on 31/01/15.
 */
object Main {


  def main (args: Array[String]) {

    var sc = new ServConfig()
    sc.config
    println(sc.cheminCible)
    println(sc.cheminSource)
    println(sc.lienCible)


  }



}
