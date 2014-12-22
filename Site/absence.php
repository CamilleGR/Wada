<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
  </head>

  <body> <br/><br/><br/>


    <?php include "menu.html"; ?>


    <div class="wrapper">
    <center> <p> Quel liste d'attribue ? ( pas forcemet le mm nomvre d'attribut ) => donc PHP ! </p></center>
    <div class="toggle_radio">
      <input type="radio" class="toggle_option" id="first_toggle" name="toggle_option">
      <input type="radio" checked class="toggle_option" id="second_toggle" name="toggle_option">
      <input type="radio" class="toggle_option" id="third_toggle" name="toggle_option">
      <label for="first_toggle"><p>Sondage age</p></label>
      <label for="second_toggle"><p>Sondage Profession</p></label>
      <label for="third_toggle"><p>Sondage Avis</p></label>
        <div class="toggle_option_slider"></div>
    </div>


  </body>
</html>