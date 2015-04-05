<?php

	//curl_setopt($ch, CURLOPT_URL, "localhost:8080/evoTweet?path=".urlencode($_GET['path'])."&seg=".urlencode($_GET['seg']));


	if(isset($_GET['action'])){
		
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
				$url = "localhost:8080/stream?hashgtags=".urlencode($_GET['hashtags']);
				break;
			case "stopStream" :
				$url = "localhost:8080/stopStream";
				break;
			case "attributs" :
				$url = "localhost:8080/attributs?nomFichier=".urlencode($_GET['nomFichier']);
				break;
			case "stats" :
				$file = "nomFichier=".urlencode($_GET["nomFichier"]);
				$attr = "attribut=".urlencode($_GET["attribut"]);
				$seg = "segment=".urlencode($_GET["segment"]);
				$filtre = "filtre=".urlencode($_GET["filtre"]);
				$url = "localhost:8080/stats?$file&$attr&$seg&$filtre";
				break;
			case "courbe" :
				$file = "nomFichier=".urlencode($_GET["nomFichier"]);
				$attr1 = "attribut1=".urlencode($_GET["attribut1"]);
				$attr2 = "attribut2=".urlencode($_GET["attribut2"]);
				$filtre = "filtre=".urlencode($_GET["filtre"]);
				$url = "localhost:8080/courbe?$file&$attr1&$attr2&$filtre";
				break;
			case "kmeans" :
				$file = "nomFichier=".urlencode($_GET["nomFichier"]);
				$clust = "nbClusters=".urlencode($_GET["nbClusters"]);
				$filtre = "filtre=".urlencode($_GET["filtre"]);
				$url = "localhost:8080/kmeans?$file&$clust&$filtre";
				break;
			case "kmeans_Stats" :
				$file = "nomFichier=".urlencode($_GET["nomFichier"]);
				$attr = "attribut=".urlencode($_GET["attribut"]);
				$seg = "segment=".urlencode($_GET["segment"]);
				$clust = "nbClusters=".urlencode($_GET["nbClusters"]);
				$filtre = "filtre=".urlencode($_GET["filtre"]);
				$url = "localhost:8080/kmeans_Stats?$file&$attr&$seg&$clust&$filtre";
				break;
		}
	curl_setopt($ch, CURLOPT_URL, $url);
	header('Content-Type: application/json');
	echo curl_exec($ch);
	curl_close($ch); 		
		
	}
?>
