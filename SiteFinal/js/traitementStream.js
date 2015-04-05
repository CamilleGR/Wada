$(document).ready( function(){

	$('#formulaire').fadeIn();
	
	
	$('#boutonSubmit').click(function(e){
		$('#formulaire').fadeOut();

		//quand on clique on peut afficher les graphes
		$('#courbes').fadeIn();
		$('#bulles').fadeIn();

		//$('#global').fadeIn();
	});



});
