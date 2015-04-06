// make dataset globally available
var dz;
var chart;

function setChart(index){
	chart = index;
}
function getChart(){
	return chart;
}

// load dataset and create table
function load_dataset(csv) {
  var data = d3.csv.parse(csv);
  dz=data;
  create_table(data);
  progressUp(".progress-bar", 50);
  loadHisto(data);
  //console.log("1");
  setChart(1);		
  
  d3.select("#visuelChng").remove();
  notif();
  
  var uli = $('<li></li>').attr("role","presentation");
  	var test = $('<a></a>').attr("id","visuelChng").click(function(){
  	//alert(chart);
  	setChart(changeGraph(getChart(),dz));
  	notif();
  	uli.addClass("active");
  		
  });
  test.html('<p><span class="glyphicon glyphicon-refresh"></span>'+' Changer de visuel</p>');
  uli.append(test);
  $("#tools").append(uli);
  
  console.log("%"+chart);
  //console.log(test.parent().html());
   
  progressUp(".progress-bar", 100);
}

function notif(){
	$("#tll1").html("");
  	var min = $("<span></span>").attr("class","glyphicon glyphicon-minus");
  	var selecTtl = $("#tll1");
  	if(getChart()==1){
	  	selecTtl.text(" Histogramme ");
	  }else{
	  	selecTtl.text(" Diagramme ");
	  }
	  selecTtl.prepend(min);
}

function changeGraph(chart,data){

	
  	if(chart==1){
  		loadPie(dz);
  		return 2;
  	}else if(chart==2){
  		loadHisto(dz);
  		return 1;
  	}else{
  		console.log("Chart = 1 loadPie\nChart = 2 loadHisto");
  	}
  	
  }

/*
  $("#collapseOne").removeClass("in");
  $("#collapseOne").addClass("collapsing");
  $("#collapseOne").removeClass("collapsing");
  $("#collapseOne").addClass("collapse");
  */

function range(start, end) {
    var foo = [];
    for (var i = start; i <= end; i++) {
        foo.push(i);
    }
    return foo;
}

function addItemDD(list,name){
	var item = list.append("li").append("a").attr("role","menuitem");
	item.text(name);
	return item;
}
//itemArray.forEach(function(d){});


function createDropdown(container,name){
	
	var stats = d3.select(container);
    
	var group = stats.append("div").attr("class","dropdown btn-group").attr("role","group");
    
    var btn = group.append("button").attr("id","dLabel").attr("class","btn btn-default btn-xs").attr("aria-haspopup","button").attr("data-toggle","dropdown").attr("aria-haspopup","true").attr("aria-expanded","false");
    
    btn.text(name);
    btn.append("span").attr("class","caret");
    var listAttr = group.append("ul").attr("class","dropdown-menu").attr("aria-labelledby","dLabel").attr("role","menu");
	
    
    $('.dropdown-toggle').dropdown();
    return listAttr;
}

// create a table with column headers, types, and data
function create_table(data, statsData) {
  // table stats
  console.log(data);
  var keys = d3.keys(data[0]);
  
  
  var name = "A propos de "+keys[0];
  
  var info = d3.select("#statsTitle").text(name);
  var stats = d3.select("#stats").html("");
    	
    /*stats.append("div")
    .text("Columns : " + keys.length)

  stats.append("div")
    .text("Rows : " + data.length)
    stats.append("hr");*/
	
  for (key in statsData) {
	var label = statsData[key].label;
	if (statsData[key].label == "count") label = "Nombre de tuples";
	if (statsData[key].label == "med") label = "Mediane";
	if (statsData[key].label == "min") label = "Valeur minimale";
	if (statsData[key].label == "max") label = "Valeur maximale";
	if (statsData[key].label == "moy") label = "Moyenne";
	stats.append("div").text(label + " : " + statsData[key].value);
  }


  d3.select("#table")
    .html("")
    .append("tr")	
    .selectAll("th")
    .data(keys)
    .enter().append("th").attr("class","thead")
      .text(function(d) { return d; });

  d3.select("#table")
    .selectAll("tr.linetable")
      .data(data)
    .enter().append("tr")
    
      .attr("class", "linetable")
      .selectAll("td")
      .data(function(d) { return keys.map(function(key) { return d[key];	 }) ; })
      .enter().append("td").attr("class","tcell")       .text(function(d) { return d; });
      
}

function create_table_kmeans(data, statsData, centers) {
  // table stats
  console.log(data);
  var keys = d3.keys(data[0]);
  
  
  var name = "A propos de "+keys[0];
  
  var info = d3.select("#statsTitle").text(name);
  var stats = d3.select("#stats").html("");
    	
    /*stats.append("div")
    .text("Columns : " + keys.length)

  stats.append("div")
    .text("Rows : " + data.length)
    stats.append("hr");*/
	
  for (key in statsData) {
	var label = statsData[key].label;
	if (statsData[key].label == "count") label = "Nombre de tuples";
	if (statsData[key].label == "med") label = "Mediane";
	if (statsData[key].label == "min") label = "Valeur minimale";
	if (statsData[key].label == "max") label = "Valeur maximale";
	if (statsData[key].label == "moy") label = "Moyenne";
	stats.append("div").text(label + " : " + statsData[key].value);
  }

  d3.select("#statsPanel").html(d3.select("#statsPanel").html() + "<h5>Centres des clusters :</h5>");
  //var tab = d3.select("table");
  //var header = d3.select("tr");
  var s = "<table class='table' ><tr><th>centre</th><th>abscisse</th><th>ordonnée</th></tr>";
  //tab.append(header);
  for (key in centers) {
	//var line = d3.select("tr");
	//line.html("<td>" + centers[key].centre + "</td><td>" + centers[key].abscisse + "</td><td>" + centers[key].ordonnee + "</td>");
	//tab.append(tr);
	s += "<tr><td>" + centers[key].centre + "</td><td>" + centers[key].abscisse + "</td><td>" + centers[key].ordonnee + "</td></tr>";
  }
  s += "</table>";
  console.log(stats.html);
  d3.select("#statsPanel").html(d3.select("#statsPanel").html() + s);

  d3.select("#table")
    .html("")
    .append("tr")	
    .selectAll("th")
    .data(keys)
    .enter().append("th").attr("class","thead")
      .text(function(d) { return d; });

  d3.select("#table")
    .selectAll("tr.linetable")
      .data(data)
    .enter().append("tr")
    
      .attr("class", "linetable")
      .selectAll("td")
      .data(function(d) { return keys.map(function(key) { return d[key];	 }) ; })
      .enter().append("td").attr("class","tcell")       .text(function(d) { return d; });
      
}

// handle upload button
function upload_button(el, callback) {
  var uploader = document.getElementById(el);  
  var reader = new FileReader();

  reader.onload = function(e) {
    var contents = e.target.result;
    callback(contents);
  };

  uploader.addEventListener("change", handleFiles, false);  

  function handleFiles() {
    d3.select("#table").text("loading...");
    var file = this.files[0];
    reader.readAsText(file);
  };
};
