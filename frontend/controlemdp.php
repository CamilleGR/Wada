<?php

    $pseudo = array("ali","eliot","moaz","adrien","livio","fatima",
                    "khouloud","camille","oscar","mustapha","youssef",
                        "victor","alex","hugo","Mickael");
    
    $co = False;
    foreach($pseudo as $value){
        if(strtolower($_POST['id']) == $value){
            $co = True;
        }
    }
    
    if( isset($_POST['mdp']) and  ($_POST['mdp']=='bigdata' || $_POST['mdp']=='BIGDATA') and $co )
    {
        ob_start();
        header('Location: index.html');
        setcookie('pseudo', $_POST['id'], time() + 365*24*3600, null, null, false, true);
        ob_end_flush(); 
    }
    else
    {
        ob_start();
        header('Location: connexion.html');
        ob_end_flush(); 
    }
?>