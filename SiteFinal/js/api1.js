$(document).ready(function(){

			console.log('test');
			$('input#commencer').click(function(){
				$('#formulaire').fadeToggle();
			});

			$('input#arreter').click(function(){
				$.get('proxyWebService.php',{action:"stopstream"},function(data){
					console.log("Arrêt du stream : "+data.reponse);	
					if(data.reponse === "true" ){
						alert("Votre stream a bien été arrêté");
					}else{
						alert("Une erreur est survenue, il se peut qu'il n'y ai pas de stream à arrêter.");
					}	
					
				});
			});

			$('input#traiter').click(function(){
				document.location.href="traitementStream.php";
			});
			
			$('#computeStream').click(function(){
				$.get('proxyWebService.php',{action:"stream",hashtags:$('#hashtags').val()},function(data){
					console.log("Debut du stream");
					alert("Vous pouvez maintenant consulter votre stream avec ce chemin : \n"+data.path);
				});
			});
			
		});
