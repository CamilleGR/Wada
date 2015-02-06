import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.twitter._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.api.java.{JavaReceiverInputDStream, JavaDStream, JavaStreamingContext}
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}
import twitter4j._
import java.util.Date
import java.text.SimpleDateFormat


:load tweetFunction.scala

/*
*	Flux configurer en minute => flux qui vise à faire des statistiques sur plus d'une heure
*/
val ssc = new StreamingContext(sc,Seconds(60))	//Création d'un nouveau StreamingContext

/*
*	Ici on rentre les clés qu'on obtient avec son compte de développeur twitter
*
*/
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

var auth = new twitter4j.auth.OAuthAuthorization(oauthConf.build()) // Création de l'objet pour créer le stream plus tard
var filter = Array("#TheVoice");// On recherche les Tweets qui contiennent les mots cles de filter
var jssc = new JavaStreamingContext(ssc)			// Création d'un JavaStreamingContext ... Me demandez pas pourquoi
var tweets = TwitterUtils.createStream(jssc,auth,filter,StorageLevel.MEMORY_ONLY) //Création du flux

//tweets.dstream.map(x=> x.getCreatedAt+";"+x.getText.replace(";",",").replace('\n',' ')).saveAsTextFiles("Tweets/tweet/tweet")
// Pour récupérer les hashtag => +","+x.getText.split(" ").filter(_.startsWith("#")).mkString(" ")

// On aurait pu récupérer les secondes comem ceci => +":"+x.getCreatedAt.getSeconds
tweets.dstream.map(x=> x.getCreatedAt.getHours+":"+x.getCreatedAt.getMinutes).map(x=> (x,1)).reduceByKey((x,y)=>x+y).saveAsTextFiles("Tweets/reduce/TheVoice")

jssc.start

	
	
	
