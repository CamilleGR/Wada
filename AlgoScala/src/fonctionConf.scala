//sam. 14 févr. 2015 00:02:36 CET 

/*
*@file : Fichier qui contiendra les 4 clés
*
*	Cette fonction créer et retourne une autorisation pour pouvoir créer un flux twitter
*	par la suite il faudra charger les clés à partir d'un fichier ( crypte de préférence )
*@return : twitter4j.auth.OAuthAuthorization
*/
	def creerAutorisation():twitter4j.auth.OAuthAuthorization ={
	
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
*@auth	: Autorisation de twitter
*@sc	: SparkContext
*
*	Cette fonction permet de creer un flux qui va recuperer des tweets, les cles sont code en dure mais plus tard nous ferons un chargement 
*	de ces cles.
*	Le flux se deroule pour un certain temps, apres il faudra faire une fonction qui permet d'executer le flux pour un temps indefini, puis
*	une fonction pour arreter ce flux.
*/
	def creerStream(tag:Array[String],path:String,time:Int,auth:twitter4j.auth.OAuthAuthorization,sc:SparkContext):Unit ={

	var ssc = new StreamingContext(sc,Seconds(60))
	var jssc= new JavaStreamingContext(ssc)
	var tweets = TwitterUtils.createStream(jssc,auth,tag,StorageLevel.MEMORY_ONLY) //Creation du flux

	tweets.dstream.map(x=> x.getCreatedAt.getDay+"/"+x.getCreatedAt.getMonth+"/"+x.getCreatedAt.getYear+" "+x.getCreatedAt.getHours+":"+x.getCreatedAt.getMinutes + " ; " + x.getText.split(" ").filter(_.startsWith("#")).mkString(" ").replace(";","")).saveAsTextFiles(path)

	jssc.start
	Thread.sleep(time*60000)	// on attend time minutes pour couper le flux
	jssc.stop(stopSparkContext=false)
	}
