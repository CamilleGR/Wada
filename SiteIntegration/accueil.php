<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
  </head>


  <body> 
    <?php include "menu.html"; ?>
    <p> Bonjour et bienvenue sur WADA <?php echo ucfirst($_COOKIE['pseudo']); ?> ! </p> 
    <p> Veuillez sélectionner les données que vous voulez visualiser : <p>
    <center>
      <form method="post" action="http://localhost:8080/attributs"> <!--<form method="post" action="localhost:8080">;-->
        <p>
          <select name="nomFichier">
          <!-- C'est le 'onchange' qui permet daller directement sur la page choisi -->

              <option style="visibility: hidden" selected="selected" value="index.html">Choisissez votre base de données</option>      
                <?php
					$fp = fopen('listefichiers.txt', 'r');
                    //$tabliste = explode(";", $_GET['listeFichiers']);
					while (!feof($fp)){
						$line = trim(fgets($fp));

						echo '<option value='.$line.'>'.$line.'</option>';
                    //}
					}
                ?>
          </select>
		</p>
        <p><input type="submit" value="valider" title="Valider" /></p>
      </form>
    </center>

  </body>
</html>