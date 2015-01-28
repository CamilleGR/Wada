<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
    <script src="scripts/d3.js" charset="utf-8"></script>
  </head>




    <style type="text/css">
        svg {
            font: 10px sans-serif;
        }

        .bar {
            stroke: white;
            fill: steelBlue;

        }

        .axis path, .axis line {
            fill: none;
            stroke: #000;
            shape-rendering: crispEdges;
        }

        rect.mover {
            stroke: lightSteelBlue;
            stroke-opacity: 0.3;
            fill: lightSteelBlue;
            fill-opacity: 0.1;
        }
       .bara {
       stroke: white;
       fill: Red;
       }
       
       body {
        font: 10px sans-serif;
        }

        .arc path {
        stroke: #fff;
        }
        .baro {
        opacity: 0.4;
        background-color: red;
        }
        
        .bari {
        opacity: 1;
        }
    </style>





  <body>
    	<?php 	include "menu.html";  ?>
	<?php
		echo '<p>';
            	if(isset($_GET["count"])) {
               		$count = $_GET["count"];
               		echo "nombre de lignes : $count <br />";
            	}
            	if(isset($_GET["stats"])) {
                	$stats = explode(",", $_GET["stats"]);
                	echo "min : $stats[0]<br />";
                	echo "max : $stats[1]<br />";
                	echo "moy : $stats[2]<br />";
            	}
            	if(isset($_GET["med"])) {
			$mediane = $_GET["med"];
                	echo "mediane : $mediane <br />";
            	}
		echo '</p>';
            	if(isset($_GET["moy"])) {
            		$kmean = explode(";", $_GET["moy"]);
            		echo "<h2> Moyennes par tranche </h2>";
            		echo '<h3>';
            		foreach ($kmean as $val) {
            			$ligne = explode(",", $val);
    					echo $ligne[0].' -> '.$ligne[1].'<br />';
            		}
            		echo '</h3>';
            	}
	?>
    <div id="diagram">
        <?php include "histogram.php"; ?>
    </div>
    <center><button onclick="donut()">Afficher les statistiques en mode : DONUT </button></center>
  </body>
</html>
