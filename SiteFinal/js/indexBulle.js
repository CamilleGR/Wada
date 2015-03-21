function association(){
  $(document).ready(function () {

/*
	soit camille change le nom de ses variables,
	soit on les renommes pas nous meme en récrant un tableau 
*/
	window.items = [];



      $.getJSON( "data/exempleJSON.json", function( data )
      {

        ali.Items.push({"text":"Maxi","count":"90"});
        console.log("ali   :"+ali.Items);
        $.each( data.stats, function( key, val ){
          //items.push( "{text:" + '"'+val.label+'"' + ", " +"count:"+ '"'+val.value+'"' + "}" );
          ali.Items.push( "{text:" + '"'+val.label+'"' + ", " +"count:"+ '"'+val.value+'"' + "}" );
        });
       	console.log("ali   :"+ali.Items);
        //afficheetretourne();
       	//affichage sur le body ( pas obligaoire )
        //$( "<ul/>", {"class": "my-new-list",html: items.join( "" )}).appendTo( "body" );
      });


	 // load the external data
	 d3.json("data/exempleJSON.json", function(error, dataJson) {
	 	creation(dataJson);
	 });
	  
	  
	 //creation(data.Items);




	function creation(data){
		
		/*console.log(data);
		console.log("item dans creation() :"+items);
		console.log("data dans creation() : data :"+data.stats[0]);*/
      	var bubbleChart = new d3.svg.BubbleChart({
	        //supportResponsive: true,
	        //container: => use @default
	        size: 600,
	        //viewBoxSize: => use @default
	        innerRadius: 600 / 3.5,
	        //outerRadius: => use @default
	        radiusMin: 50,
	        //radiusMax: use @default
	        //intersectDelta: use @default
	        //intersectInc: use @default
	        //circleColor: use @default

	        data: {
	          items: data.Items,
	          eval: function (item) {return item.count;},
	          classed: function (item) {return item.text.split(" ").join("");}
	        },

	        //zaza : console.log("item dans creation() : data :"+data.stats[0].label),

	        plugins: [
	                    { //affiche le popup 
	                      name: "central-click",
	                      options: {
	                        text: "(See more detail)",
	                        style: {
	                          "font-size": "12px",
	                          "font-style": "italic",
	                          "font-family": "Source Sans Pro, sans-serif",
	                          //"font-weight": "700",
	                          "text-anchor": "middle",
	                          "fill": "white"
	                        },
	                        attr: {dy: "65px"},
	                        centralClick: function() {
	                          alert("Here is more details!!");
	                        }
	                      }
	                    },
						

	                    {
	                      name: "lines",
	                      options: {
	                      format: 
	                      [
	                          {// affi les bulles
	                            textField: "count",
	                            classed: {count: true},
	                            style: 
	                                {
	                                  "font-size": "28px",
	                                  "font-family": "Source Sans Pro, sans-serif",
	                                  "text-anchor": "middle",
	                                  fill: "white"
	                                },
	                            attr: 
	                              { // affiche les valeur
	                                dy: "0px",
	                                x: function (d) {return d.cx;},
	                                y: function (d) {return d.cy;}
	                              }
	                          },


	                          { // affiche le nom dans les bulles
	                            textField: "text",
	                            classed: {text: true},
	                            style: {
	                              "font-size": "14px",
	                              "font-family": "Source Sans Pro, sans-serif",
	                              "text-anchor": "middle",
	                              fill: "white"
	                            },
	                            attr: {
	                              dy: "20px",
	                              x: function (d) {return d.cx;},
	                              y: function (d) {return d.cy;}
	                            }
	                         }
	                      ],

	    						centralFormat: [
					                    { // gere l'inimation des bulles
					                      style: {"font-size": "50px"},
					                      attr: {}
					                    },
					                    {// Line #1
					                      style: {"font-size": "30px"},
					                      attr: {dy: "40px"}
					                    }
					                ]
	            }
	          }]

	      	});// fin de la variable bubbleChart

		} //fin de la fonction creation

  });//fin du chargement du document

}//fin de la fonction association




/*function afficheetretourne(){
    console.log("items 2 :"+items);
    return items;
}
		*/