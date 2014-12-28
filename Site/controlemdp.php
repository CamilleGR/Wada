<?php
    if( isset($_POST['mdp']) and  ($_POST['mdp']=='bigdata' || $_POST['mdp']=='BIGDATA') and (trim($_POST['id'])!='') )
    {
        ob_start();
        header('Location: accueil.php');
        setcookie('pseudo', $_POST['id'], time() + 365*24*3600, null, null, false, true);
        ob_end_flush(); 
    }
    else
    {
        ob_start();
        header('Location: index.html');
        header("Cache-Control: no-store, no-cache, must-revalidate");
        ob_end_flush(); 
    }
?>