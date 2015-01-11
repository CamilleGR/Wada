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
    <?php 	include "menu.html";  

        if ($_GET['myname'] == "salaire")
        {
    ?>
            <button onclick="donut()">Afficher les statistiques en mode : DONUT </button>
                <div id="diagram">
                    <script src="histogram.js">  </script>
                </div>
     <?php
        }
        else if ($_GET['myname'] == "age")
        {
            include "scripts/Test1.html";
        }
        else if ($_GET['myname'] == "profession" )
        {
            include "scripts/Test2.html";
        }
         else if ($_GET['myname'] == "sexe" )
        {
            include "scripts/Test3.html";
        }
        else
            include "scripts/Test4.html";
    ?>

  </body>
</html>