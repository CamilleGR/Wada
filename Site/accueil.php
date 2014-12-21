<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
<!--     <link href='http://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'> -->
  </head>



  <header>
    <div class="area"></div><nav class="main-menu">
            <ul>
                <li>
                    <a href="accueil.php">
                        <i class="fa fa-home fa-2x"></i>
                        <span class="nav-text">
                          Accueil
                        </span>
                    </a>
                  
                </li>
                <li class="has-subnav">
                    <a href="#">
                        <i class="fa fa-laptop fa-2x"></i>
                        <span class="nav-text">
                          Rapport du projet
                        </span>
                    </a>
                    
                </li>
                 <li class="has-subnav">
                    <a href="#">
                       <i class="fa fa-folder-open fa-2x"></i>
                        <span class="nav-text">
                            Pages
                        </span>
                    </a>
                   
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-bar-chart-o fa-2x"></i>
                        <span class="nav-text">
                            Graphique de visualisation
                        </span>
                    </a>
                    
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-font fa-2x"></i>
                        <span class="nav-text">
                            Typography and Icons
                        </span>
                    </a>
                </li>
                <li>
                   <a href="#">
                       <i class="fa fa-table fa-2x"></i>
                        <span class="nav-text">
                            Tables
                        </span>
                    </a>
                </li>
                <li>
                   <a href="#">
                        <i class="fa fa-map-marker fa-2x"></i>
                        <span class="nav-text">
                            Maps
                        </span>
                    </a>
                </li>
                <li>
                    <a href="sujet.pdf">
                       <i class="fa fa-info fa-2x"></i>
                        <span class="nav-text">
                            Sujet du projet
                        </span>
                    </a>
                </li>
            </ul>
            <li class="has-subnav">
                    <a href="contact.html">
                       <i class="fa fa-list fa-2x"></i>
                        <span class="nav-text">
                          Contact
                        </span>
                    </a>                   
                </li>

            <ul class="logout">
                <li>
                   <a href="index.html">
                        <i class="fa fa-power-off fa-2x"></i>
                        <span class="nav-text">
                            Deconnexion
                        </span>
                    </a>
                </li>  
            </ul>
        </nav>
  </header>









  <body> 
  <br/><br/><br/>

    <center>
      <form method="GET" action="redirection_navigation.php">
        <label>
          <select name="selection" onchange="document.location = this.options[this.selectedIndex].value;">
              <option style="visibility: hidden" selected="selected" value="index.html">Choisissez votre base de données</option>      
                <?php
                  // ouverture en lecture seum 
                  $fichier = fopen("liste.txt","r");
                  /*Si on a réussi à ouvrir le fichier*/
                  if ($fichier)
                  {
                    /*Tant que l'on est pas à la fin du fichier*/
                    while (!feof($fichier))
                    {
                      /*On lit la ligne courante*/
                      $buffer = fgets($fichier);
                      /*On l'affiche*/
                      $buffer = trim($buffer);
                      $adr = $buffer.'.html';
                      echo "<option value=$adr>$buffer</option>";
                    }
                    /*On ferme le fichier*/
                    fclose($fichier);
                  }
                ?>
          </select>
          <!-- Valider tout seul avec le script en php : redirection_navigation
          <p><input type="submit" value="valider" title="Valider" /></p>
           -->
        </label>
      </form>
    </center>








  </body>
</html>