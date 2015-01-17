<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
	<META http-equiv=Content-Type content="text/html; charset=windows-1252">
	<link rel="stylesheet" type="text/css" href="css/diagram.css">
	<script src="js/d3.min.js"></script>
    </head>
    <body>
        <h1> Formulaire de test </h1>

        <?php
        	$fp = fopen('listefichiers.txt', 'r');
	?>
	<p> <form method="POST" action="http://localhost:8080/attributs">
		<select name="nomFichier">
			<?php 
				while (!feof($fp)){
					$line = trim(fgets($fp));
					echo '<option value="'.$line.'">'.$line.'</option>';
				}
			?>
		</select>
		<input type="submit" />
	</form></p>
	
	<?php
		fclose($fp);
		if (isset($_GET["attributs"])) {
		$fichier = $_GET['nomFichier'];
		echo '<h2>'.$fichier.'</h2>';
        ?>
		<p><form method="POST" action="http://localhost:8080/stats">
            		<input type="hidden" name="nomFichier" value="<?php echo $fichier?>"/>
			<label>Attributs<select name="attribut">
			<?php
				$attributs = explode(",", $_GET["attributs"]);
				for ($i=0; $i<count($attributs); $i++) {
					echo '<option value="'.$attributs[$i].'">'.$attributs[$i].'</option>';
				}
			?>
			</select></label><br />
            		<label>segments : <input type="text" name="segment" value="2"/></label><br />
            		<label>filtre : <input type="text" name="filtre"/</label><br />
            		<input type="submit"/>
 		</form></p>

        <?php
		}
            	if(isset($_GET["fichier"])) {
                	$fichier = $_GET["fichier"];
                	echo "<h2> fichier $fichier crée</h2>";
					echo "<button id=\"data1\" onclick=\"createHistogram('".$fichier."')\">Graphique</button>";
            	}
            	if(isset($_GET["count"])) {
               		$count = $_GET["count"];
               		echo "<p> nombre de lignes : $count </p>";
            	}
            	if(isset($_GET["stats"])) {
                	$stats = explode(",", $_GET["stats"]);
                	echo "<p> min : $stats[0]</p>";
                	echo "<p> max : $stats[1]</p>";
                	echo "<p> moy : $stats[2]</p>";
            	}
            	if(isset($_GET["stats"])) {
            		$kmean = explode(";", $_GET["kmean"]);
            		echo "<h3> Kmean </h3>";
            		echo '<p>';
            		foreach ($kmean as $val) {
            			$ligne = explode(",", $val);
    					echo $ligne[0].' -> '.$ligne[1].'<br />';
            		}
            		echo '</p>';
            	}
        ?>
	<div id="diagram" style="width:"1000px"; height:"400px":">

			</div>
	
	<script src="js/histo.js"></script>
    </body>
</html>
