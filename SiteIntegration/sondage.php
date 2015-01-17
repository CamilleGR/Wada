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

      <form action="http://localhost:8080/stats" method="POST" name="name3">
		<?php $fichier = $_GET['nomFichier']; ?>
		<input type="hidden" name="nomFichier" value="<?php echo $fichier?>"/>
        <fieldset class="radioset">
          <ul>
            <?php
              $tabliste = explode(",", $_GET['attributs']);
              foreach ($tabliste as $key => $value)
                echo '<li><input type=radio name=attribut value='.$value.' ><label>'.$value.'</label></li>';
            ?>
          </ul>
        </fieldset> 
	<p>
		<label>Nombre de segments : <input type="text" name="segment" value="2"/></label>
        	<label>Filtres : <input type="text" name="filtre" value=""/></label>
	</p>
        <button>Visualiser</button>


      </form>


    </center>
  </body>
</html>


