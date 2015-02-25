    <?php
      /* on vérifie que l'information "selection" existe ET qu'elle n'est pas vide : */
      if ( isset($_POST['selection']) && !empty($_POST['selection']) ) 
      {
        // si c'est bien le cas (existe ET non-vide à la fois), on redirige le visiteur vers sa valeur choisie de l'information "selection"
        header("Location: ".$_POST['selection']."");
      }
      else
      {
        // sinon, on le redirige vers une autre page utile : 
        header("Location: http://www.monsite.net/plan.html");
      }
    ?>