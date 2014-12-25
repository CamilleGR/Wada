<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
<!--     <link href='http://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'> -->
  </head>








  <body> 
    <?php include "menu.html"; ?>
    <br/><br/><br/>



    <center>
      <form method="post" action="redirection_navigation.php"> <!--   <form method="post" action="localhost:8080">    -->
        <label>
          <select name="selection" onchange="document.location = this.options[this.selectedIndex].value;">
          <!-- C'est le 'onchange' qui permet daller directement sur la page choisi -->

              <option style="visibility: hidden" selected="selected" value="index.html">Choisissez votre base de données</option>      
                <?php
                  // ouverture en lecture seum 
                  $fichier = fopen("listeFichiers.txt","r");
                  /*Si on a réussi à ouvrir le fichier*/
                  if ($fichier)
                  {
                    /*Tant que l'on est pas à la fin du fichier*/
                    while (!feof($fichier))
                    {
                      /*On lit la ligne courante*/
                      $buffer = fgets($fichier);
                      /*On l'affiche*/
                      $buffer = trim($buffer);
                      $adr = $buffer.'.php';
                      echo "<option value=$adr>$buffer</option>";
                    }
                    /*On ferme le fichier*/
                    fclose($fichier);
                  }
                ?>
          </select>
          <!-- Valider tout seul avec le script en php : redirection_navigation
          <p><input type="submit" value="valider" title="Valider" /></p>
           -->
        </label>
      </form>
    </center>








  </body>
</html>