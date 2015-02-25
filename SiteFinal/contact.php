<?php
  $erreur = NULL; 
  $info = NULL;
  	if(!empty($_POST['nom']) && !empty($_POST['sujet']) && !empty($_POST['email'])&& !empty($_POST['message']))
  	{
  		extract($_POST);
  		if(preg_match("#^[a-z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$#",$email))
      {
    		$nom = htmlspecialchars(addslashes($nom));
    		$sujet = htmlspecialchars(addslashes($sujet));
    		$email = htmlspecialchars(addslashes($email));
    		$message = htmlspecialchars(addslashes($message));
    		$destinataire ="projetwada@gmail.com";
    		$sujet = "Formulaire de contact";
    		$entete = 'From:'.$email.";
    		$message = 'Nom:'.$nom. '' .'Message:' .$message.";
    		
    		mail($destinataire, $sujet, $message, $entete);
    		
    		$info = "<center><h2>email envoyer</h2></center>";
    		unset($_POST,$message,$nom,$sujet,$email);
  		}
  		else
      {
        $erreur="Adresse email invalide";
  		}
  	}
  	else
    {
   			$erreur= "<center><h5>Veuillez remplir tous les champs obligatoires*</h5></center>";
  	}
?>
<!doctype html>  
  <html>
  <head>
    <meta charset="UTF-8">
    <title>Wada - Contact</title>
    <link rel="stylesheet" type="text/css" href="css/contact.css"/>
  </head>

  <body>
    <?php include "menu.html"; ?>

    <!-- ****************************** CARDE DE PRESENTATION ****************************** -->

    <div id="container">
      <div id="demo">
      <p class="padabove">N'hésitez pas à nous contacter pour plus de renseignements concernant ce projet. </p>

         <a><img src="img/wada.png" class="dim"></a>
      </div>   
      
      <div id="contact-wrap">   
        <div id="contact-area">
          
          <form method="post" action="contact.php">

            
            <input type="text" name="nom" id="nom" placeholder="Nom *" value="<?php if(isset($_POST['nom'])) echo htmlspecialchars($_POST['nom']);?>"/><br/>
			

            <input type="text" name="sujet" id="sujet" placeholder="Sujet *" value="<?php if(isset($_POST['sujet'])) echo htmlspecialchars($_POST['sujet']);?>"/><br/> 
			
      
            <input type="text" name="email" id="email" placeholder="Email *" value="<?php if(isset($_POST['email'])) echo htmlspecialchars($_POST['email']);?>"/><br/>
			
            
            <textarea name="message" rows="20" cols="20" id="message" placeholder="Message *"><?php if(isset($_POST['message'])) echo htmlspecialchars($_POST['message']);?></textarea><br/>
	

            <input type="submit" name="submit" value="Envoyer" class="submit-button"/> <span style="color:red;"><?php echo $erreur;?></span><span style="color:green;"><?php echo $info;?></span>
			
          </form>
            </div>
         </div>
    </div>





  
</html>
</body>