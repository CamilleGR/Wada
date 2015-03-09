function association(){
  $(document).ready(function () {


	window.items = [];
		// 8080/EvoTweet

      $.getJSON( "http://localhost/bigdata/SiteFinal/data/exempleJSON.json", function( data )
      {

		/*var jsonObj = JSON.parse(data);
		console.log(jsonObj.stats + ", " + jsonObj.EvoTweet);*/

		// l'accée est fait !!
        //console.log(data.stats); 

        // structure d'affichage
        $.each( data.stats, function( key, val ){
          items.push( "{text:" + '"'+val.label+'"' + ", " +"count:"+ '"'+val.value+'"' + "}" );
        });

       	console.log("items :"+items);
        afficheetretourne();

       //affichage sur le body ( pas obligaoire )
        $( "<ul/>", {"class": "my-new-list",html: items.join( "" )}).appendTo( "body" );

      });

      function afficheetretourne(){
        console.log("items 2 :"+items);
        return items;
      }
		


      var bubbleChart = new d3.svg.BubbleChart({
        supportResponsive: true,
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
          items: [
            {text: "Java", count: "236"},
            {text: ".Net", count: "382"},
            {text: "Php", count: "170"},
            {text: "Ruby", count: "123"},
            {text: "D", count: "12"},
            {text: "Python", count: "170"},
            {text: "grdeVal", count: "900"},
            {text: "C/C++", count: "382"},
            {text: "Pascal", count: "10"},
            {text: "Something", count: "170"},
            {text: "ali", count: "10"},
            {text: "Pcal", count: "10"}
          ],
          eval: function (item) {return item.count;},
          classed: function (item) {return item.text.split(" ").join("");}
        },

        plugins: [
                    {
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
                          {// Line #0
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
                              {
                                dy: "0px",
                                x: function (d) {return d.cx;},
                                y: function (d) {return d.cy;}
                              }
                          },


                          {// Line #1
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
                    {// Line #0
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
      });
  });
}// fin de la fonction association
