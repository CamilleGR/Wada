<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Wada - Sondage</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">

  </head>

  <body> 
    <center>
      
      <?php include "menu.html"; ?>

      <p> Précisez le type de données que vous voulez visualiser :<p> <br\>

      <form action="graphe.php" method="GET" name="name3">

        <fieldset class="radioset">
          <ul>
            <?php
              $tabliste = explode(";", $_GET['listeAttributs']);
              foreach ($tabliste as $key => $value)
                echo '<li><input type=radio name=myname value='.$value.' ><label>'.$value.'</label></li>';
            ?>
          </ul>
        </fieldset>
        <button>Visualiser</button>


      </form>


    </center>

    <script src="codebase/bootstrap/js/jquery-1.10.2.min.js"></script>
    <script src="codebase/bootstrap/js/bootstrap.min.js"></script>
    <script src="codebase/bootstrap-formhelpers-min.js"></script>
  <script src="codebase/bootstrap-formhelpers-datepicker.js"></script>

  </body>
</html>


