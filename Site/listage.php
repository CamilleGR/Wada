<!doctype html>  
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Ali - Accueil</title>
	    <link rel="stylesheet" type="text/css" href="styles.css"/>
	</head>

	<body> 
	    <?php include "menu.html"; ?>

		<?php
			//Chargement du contenu de la page dans une variable
			$page = file_get_contents("http://localhost/Site%20du%20projet/");

			//Recherche des liens
			preg_match_all('#<a href="(.*?)"(.*?)>#is',$page,$resultat,PREG_PATTERN_ORDER);

			//Listage des liens trouvés
			foreach ($resultat[1] as $liens) {
				echo "$liens<br />";
			}
		?>
	</body>
</html>