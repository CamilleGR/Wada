<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="styles.css"/>
  </head>

  <body> 
  <br/><br/><br/>

    <div class="area"></div><nav class="main-menu">
            <ul>
                <li>
                    <a href="accueil.html">
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
                            UI Components
                        </span>
                    </a>
                    
                </li>
                <li class="has-subnav">
                    <a href="#">
                       <i class="fa fa-list fa-2x"></i>
                        <span class="nav-text">
                            Forms
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
                            Graphs and Statistics
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
                    <a href="#">
                       <i class="fa fa-info fa-2x"></i>
                        <span class="nav-text">
                            Documentation
                        </span>
                    </a>
                </li>
            </ul>

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