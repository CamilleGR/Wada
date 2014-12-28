<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
  </head>

  <body> 
    <center>
      
      <?php include "menu.html"; ?>

      <p> Précisez le type de données que vous voulez visualiser :<p> <br\>

      <form action="graphe.php" method="post">
        <fieldset class="radioset">
          <ul>
            <?php
              $tabliste = explode(";", $_GET['listeAttributs']);
              foreach ($tabliste as $key => $value)
                echo "<li><input type=radio name=radio[]><label>$value</label></li>";
            ?>
          </ul>
        </fieldset>
        <button>Visualiser</button>
      </form>


    </center>
  </body>
</html>


