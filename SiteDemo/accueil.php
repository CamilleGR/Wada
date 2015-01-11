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
      <form method="post" action="redirection_navigation.php"> <!--<form method="post" action="localhost:8080">;-->
        <label>
          <select name="selection" onchange="document.location = this.options[this.selectedIndex].value;">
          <!-- C'est le 'onchange' qui permet daller directement sur la page choisi -->

              <option style="visibility: hidden" selected="selected" value="index.html">Choisissez votre base de données</option>      
                <?php
                    $tabliste = explode(";", $_GET['listeFichiers']);
                    foreach ($tabliste as $key => $value)
                    {
                      //$adr = $value.'.php';
                      echo "<option value=getListeAttributs.php name=selectionner >$value</option>";
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