<?php



	//curl_setopt($ch, CURLOPT_URL, "localhost:8080/evoTweet?path=".urlencode($_GET['path'])."&seg=".urlencode($_GET['seg']));


	
	if(isset($_GET['path'])){
		
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_HEADER, false);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);	
		
		switch($_GET['action']){
		
			case "evoTweet" :
				$url ="localhost:8080/evoTweet?path=".urlencode($_GET['path'])."&seg=".urlencode($_GET['seg']);
				break;
			case "associatedHashtags":
				$url = "localhost:8080/associatedHashtags?path=".urlencode($_GET['path']);
				break;
			case "stream" :
				$url = "localhsot:8080/stream?hashgtags=".urlencode($_GET['hashtags']);
				break;
			case "attributs" :
					$url = "localhost:8080/attributs?nomFichier=".urlencode($_GET['nomFichier']);
					break;
			case "stats" :
				$file = "nomFichier=".urlencode($_GET["nomFichier"]);
				$attr = "attribut=".urlencode($_GET["attribut"]);
				$seg = "segment=".urlencode($_GET["segment"]);
				$filtre = "filtre=".urlencode($_GET["filtre"]);
				$url = "localhost:8080/stats?$file&$attr&$seg$filtre";
				break;
		
		}
	curl_setopt($ch, CURLOPT_URL, $url);
	echo curl_exec($ch);
	curl_close($ch); 		
		
	}
