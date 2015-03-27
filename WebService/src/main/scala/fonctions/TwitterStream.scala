package fonctions

import java.io.File
import java.io.FileReader
import java.io.BufferedReader
import java.io.FileWriter
import java.io.BufferedWriter
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.twitter._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.api.java.{JavaReceiverInputDStream, JavaDStream, JavaStreamingContext}
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}
import twitter4j._
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat


class TwitterStream(sparkContext: SparkContext) extends java.io.Serializable {

  var sc = sparkContext
  var auth = creerAutorisation
  var ssc:StreamingContext= null
  var jssc:JavaStreamingContext=null
  var estLance = false
  var path_to_source = "";
  def changePath(newpath:String):Unit = path_to_source = newpath
  /*
  *@file : Fichier qui contiendra les 4 clés
  *
  *	Cette fonction créer et retourne une autorisation pour pouvoir créer un flux twitter
  *	par la suite il faudra charger les clés à partir d'un fichier ( crypte de préférence )
  *@return : twitter4j.auth.OAuthAuthorization
  */
  def creerAutorisation(): twitter4j.auth.OAuthAuthorization = {

    val AccessToken = "1089972787-9a1dusqTAU8PeSRxoe9LgeHjL9UTjZljcfqN2cD";
    val AccessSecret = "atkb5uqTXzv2GZhprze7pYxHuzGlsOwhcicqoyMYE3oLf";
    val ConsumerKey = "4lUD3WgWpTVUh8rTL8HOhHs8R";
    val ConsumerSecret = "EuT9tIeqTyFKWWuIL7u2Btt2ndOOOaFb2PY98EpvRlTvbZCFcC";
    System.setProperty("twitter4j.oauth.consumerKey", ConsumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", ConsumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", AccessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", AccessSecret)
    var oauthConf = new twitter4j.conf.ConfigurationBuilder() // Création d'une nouvelle authetification pour l'API de Twitter
    oauthConf.setOAuthAccessToken(AccessToken)
    oauthConf.setOAuthAccessTokenSecret(AccessSecret)
    oauthConf.setOAuthConsumerKey(ConsumerKey)
    oauthConf.setOAuthConsumerSecret(ConsumerSecret)
    return new twitter4j.auth.OAuthAuthorization(oauthConf.build()) // Création de l'objet pour créer le stream plus tard
  }

  /*
  *@tag 	: Tableau de String comportant les differents mot-cles qui peuvent etre contenu dans les tweets
  *@path	: Chemin vers l'endroit ou sera enregistre le flux
  *@time	: Combien de minutes le flux doit durer
  *
  *	Cette fonction permet de creer un flux qui va recuperer des tweets, les cles sont code en dure mais plus tard nous ferons un chargement
  *	de ces cles.
  *	Le flux se deroule pour un certain temps, apres il faudra faire une fonction qui permet d'executer le flux pour un temps indefini, puis
  *	une fonction pour arreter ce flux.
  */
  def creerStream(tag: Array[String], path: String): Unit = {

    this.ssc = new StreamingContext(sc, Seconds(60)) //mettre dans un controleur pour être par défaut
    this.jssc = new JavaStreamingContext(ssc)

    var tweets = TwitterUtils.createStream(jssc, auth, tag, StorageLevel.MEMORY_ONLY) //Creation du flux
  /*    case class TweetMachin(datetime:Date,hastags: Array[String]) {
      def toString:String = datetime.getTime.toString+hashtags.mkString(" ")
    }
    twwets.dstream.map(x => TweetMachin(x.getCreatedAt,x.getText.split(" ")).map(_.toString)*/
    tweets.dstream.map(x => x.getCreatedAt.getTime + " ; " + x.getText.split(" ").filter(_.startsWith("#")).mkString(" ").replace(";", "")).saveAsTextFiles(path)
    this.ajouterStream(path);
  }

  def lancerStream:Boolean = {
    try {
    this.jssc.start()
    this.estLance = true
    return true
    }
    catch {
      case e:Exception => {
      println("Impossible de lancer le stream")
      e.printStackTrace()
        return false
      }
    }

  }

  def stopStream:Boolean =  {
    try {
      if(jssc != null){
      this.jssc.stop(stopSparkContext = false)
      return true;
      } else{
        println("\n\n\nLE JSSC EST NULL \n\n\n")
        return false
      }
    }
    catch {
      case e:Exception => {
        println("Impossible d'arrêter le stream")
        e.printStackTrace()
        return false
      }
    }

  }

  def listerStreams():String = {
    try{
      var list = new File(path_to_source)
      var fr = new FileReader(list)
      var br = new BufferedReader(fr)
      var liste = ""
      var tmp = br.readLine()
      while(tmp != null){
        liste += tmp + "\n"
        tmp = br.readLine()
      }
      br.close()
      fr.close()

      return liste}catch{
      case e :Exception => return ""
    }
  }

  def ajouterStream(path:String):Unit ={
    var listStreams =listerStreams
    var list = new File(path_to_source)
    var fw = new FileWriter(list)
    var bw = new BufferedWriter(fw)
    var listeStr = listStreams + path + "\n"
    var liste = listeStr.split("\n")
    for(p <- liste) {
      bw.write(p)
      bw.newLine();
    }
    bw.close()
    fw.close()
  }



}
