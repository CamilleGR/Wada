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

    <div class="wrapper">
    <center> <p> Quel liste d'attribue ? ( pas forcemet le mm nomvre d'attribut ) => donc PHP ! </p></center>
    <div class="toggle_radio">

      <input type="radio" checked class="toggle_option" id="second_toggle" name="toggle_option">
      <input type="radio" class="toggle_option" id="third_toggle" name="toggle_option">


      <label for="second_toggle"><p>Sondage Profession</p></label>
      <label for="third_toggle"><p>Sondage Avis</p></label>

      <?php

                  $fichier = fopen("listeAttribus.txt","r");
                  if ($fichier)
                  {
                    $espace = 3;
                    $i=0;
                    $buffersauve = array();
                    while (!feof($fichier))
                    {
                      $buffer = fgets($fichier);
                      $buffer = trim($buffer);
                      $buffersauve[] = $buffer;
                      $string = "toggle$i";
                      echo '<input type="radio" class="toggle_option" id=';echo '"'."$string".'"'.' name="toggle_option">'; //le CSS me force a avoir les " " 
                      echo "\n";
                      $i++;
                    }
                    echo "\n\n";


                    $j=0;
                    foreach ($buffersauve as $key => $value) {
                      $string = "toggle$j";
                      echo '<label for='; echo '"'."$string".'"'.'><p>'.$value.'</p></label>';
                      echo "\n";
                      $j++;
                    }


                    //Contour blan qui dit ce qu'on a selectionné: DOIT ABSOLUMENT ETRE A LA FIN et RIEN ECRIT DEDANS  !! 
                    echo "\n";
                    echo '<div class="toggle_option_slider"></div>';
                    //echo "\n</div>"; // ferme la div de "toggle_radio"
                    echo "\n\n";


                    echo "<style>";
                    $k=0;
                    foreach ($buffersauve as $key => $value) {
                      echo "\n";
                      echo "#toggle$k:checked ~ .toggle_option_slider{ background: #F0FFFF; left: $espace px; }";
                      $k++;
                      $espace=$espace+106;
                    }
                    echo "\n";
                    echo "</style>";
                    fclose($fichier);
                  }
      ?>



 <div class="toggle_option_slider"></div> <!-- Contour blan qui dit ce qu'on a selectionné: DOIT ABSOLUMENT ETRE A LA FIN et RIEN ECRIT DEDANS  !! -->
    </div>

    <style>
        /* concerne le paneau transparent des radio bouton */

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