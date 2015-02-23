<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<!--     <link href='http://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'> -->
  </head>








  <body> 
    <?php include "menu.html"; ?>
    <br/><br/><br/>

    <div class="wrapper">
    <center> <p> Quel liste d'attribue ? ( pas forcemet le mm nomvre d'attribut ) => donc PHP ! </p></center>
    <div class="toggle_radio">
      <input type="radio" class="toggle_option" id="first_toggle" name="toggle_option">
      <input type="radio" checked class="toggle_option" id="second_toggle" name="toggle_option">
      <input type="radio" class="toggle_option" id="third_toggle" name="toggle_option">

      <label for="first_toggle"><p>Sondage age</p></label>
      <label for="second_toggle"><p>Sondage Profession</p></label>
      <label for="third_toggle"><p>Sondage Avis</p></label>


      <?php

                  $fichier = fopen("listeAttribus.txt","r");
                  if ($fichier)
                  {
                    $espace = 533;
                    $i=0;
                    $buffersauve = array();
                    while (!feof($fichier))
                    {
                      $buffer = fgets($fichier);
                      $buffer = trim($buffer);
                      $buffersauve[] = $buffer;
                      echo "<input type=radio class=toggle_option id=toggle$i name=toggle_option>\n";
                      $i++;
                    }

                    $j=0;
                    foreach ($buffersauve as $key => $value) {
                      echo "<label for=toggle$j><p>$value</p></label>\n";
                      $j++;
                    }

                    //Contour blan qui dit ce qu'on a selectionné: DOIT ABSOLUMENT ETRE A LA FIN et RIEN ECRIT DEDANS  !! 
                    //echo "\n<div class=toggle_option_slider></div>\n\n";
                    
                    $k=0;
                    foreach ($buffersauve as $key => $value) {
                      echo "<style>#toggle$k:checked ~ .toggle_option_slider{ background: #F0FFFF; left: $espace px; }</style>\n";
                      $k++;
                      $espace=$espace+106;
                    }
                   fclose($fichier);
                  }
                ?>

      <div class="toggle_option_slider"></div> <!-- Contour blan qui dit ce qu'on a selectionné: DOIT ABSOLUMENT ETRE A LA FIN et RIEN ECRIT DEDANS  !! -->
    </div>



    <style>
        /* concerne le paneau transparent des radio bouton */
        #first_toggle:checked ~ .toggle_option_slider{
          background: #F0FFFF;
          left: 3px;
        }
        #second_toggle:checked ~ .toggle_option_slider{
          background: #F0FFFF;
          left: 109px;
        }
        #third_toggle:checked ~ .toggle_option_slider{
          background: #F0FFFF;
          left: 215px;
        }

    </style>







  </body>
</html>