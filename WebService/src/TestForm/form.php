<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
	<META http-equiv=Content-Type content="text/html; charset=windows-1252">
    </head>
    <body>
        <h1> Formulaire de test </h1>
        <p> Cette page servira à envoyer un formulaire de test qui utilisera la méthode POST à notre web service</p>

    	<p> POST :  <form method="POST" action="http://localhost:8080">
            		<select name="demande">
                		<option value="statistiques">Statistiques</option>
                		<option value="listeAttributs">Liste Attributs</option>
            		</select>
            			<input type="text" name="nomFichier" value="fichier"/>
            			<input type="text" name="attribut" value="attribut"/>
            			<input type="text" name="segment" value="0"/>
            			<input type="submit"/>
        	    </form></p>

        <?php
            if(isset($_GET["fichier"])) {
            $fichier = $_GET["fichier"];
                echo "<h2> fichier $fichier crée<h2>";
            }
            else if(isset($_GET["attributs"])) {
            $attributs = $_GET["attributs"];
                echo "<h2> liste des attributs : $attributs</h2>";
            }
        ?>

    </body>
</html>
