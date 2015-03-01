console.log("Chargement du JS ...");

define(['jquery'], function ($) {
   	function _ajax_request(url, data, callback, type, method) {
		if (jQuery.isFunction(data)) {
			callback = data;
			data = {};
		}
		return jQuery.ajax({
			type: method,
			url: url,
			data: data,
			success: callback,
			dataType: type
			});
	}

	jQuery.extend({
		
		delete_: function(url, data, callback, type) {
			return _ajax_request(url, data, callback, type, 'DELETE');
		}
	});
});
	$(document).ready(function(){

		console.log("Mise en place des variables ...");

		var host = "http://localhost"
		
		var port = 50070;
		
		var path = "/webhdfs/v1/"
		
		var dir = "default/";
		
		var user = "user";
		
		var url = host+":"+port+path;
		
		var op;
		
		var res;
		
		
		
		$('.nav').click(function(){
		
			console.log("Parcourir dossier actuel");
			op="?op=LISTSTATUS";
			
		
			
		});
		
		
		$('.open').click(function(){
		
			console.log("Ouverture fichier et obtenir le path webhdfs ...");

		});
		
		
		$('.upload').click(function(){
		
		
		});
		
		$('.delete').click(function(){
		
		});
		
		console.log("fin mise en place ...");
		
		
		console.log("En attente ...");
		


	});




