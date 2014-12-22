<!doctype html>  
  <html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="contact.css"/>
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,900' rel='stylesheet' type='text/css'> 

  </head>

  <body>


    <?php include "menu.html"; ?>
    

    <!-- ****************************** CARDE DE PRESENTATION ****************************** -->

    <div id="container">
      <div id="demo">
      <p class="padabove">N'hésitez pas à nous contacter si vous êtes intéresser par notre projet ...</p>

         <a><img src="images/wada.png" class="dim"></a>
      </div>   
      
      <div id="contact-wrap">   
        <div id="contact-area">
          
          <form method="post" action="contactengine.php">

            
            <input type="text" name="Name" id="Name" placeholder="Nom *" />
            
            <input type="text" name="City" id="City" placeholder="Sujet *"/>
      
            <input type="text" name="Email" id="Email" placeholder="Email *"/>

            <input type="text" name="Phone" id="Phone" placeholder="Téléphone"/>
            
            <textarea name="Message" rows="20" cols="20" id="Message" placeholder="Message *"></textarea>

            <input type="submit" name="submit" value="Envoyer" class="submit-button"/>
          </form>
            </div>
         </div>
    </div>





  </body>
</html>