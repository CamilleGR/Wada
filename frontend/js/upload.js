// make dataset globally available
var dz;

// load dataset and create table
function load_dataset(csv) {
  var data = d3.csv.parse(csv);
  
  create_table(data);
  progressUp(".progress-bar", 50);
  loadHisto(data);
  
  progressUp(".progress-bar", 100);
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
function dropdown1(container,itemArray){
	
	var stats = d3.select(container);
    
	var group = stats.append("div").attr("class","dropdown btn-group").attr("role","group");
    
    var btn = group.append("button").attr("id","dLabel").attr("class","btn btn-default btn-xs").attr("aria-haspopup","button").attr("data-toggle","dropdown").attr("aria-haspopup","true").attr("aria-expanded","false");
    
    btn.text("Attributs");
    btn.append("span").attr("class","caret");
    var listAttr = group.append("ul").attr("class","dropdown-menu").attr("aria-labelledby","dLabel").attr("role","menu");
	itemArray.forEach(function(d){listAttr.append("li").append("a").attr("role","menuitem")
    .text(d);});
    
    $('.dropdown-toggle').dropdown();
}

// create a table with column headers, types, and data
function create_table(data) {
  // table stats
  var keys = d3.keys(data[0]);
	
  var stats = d3.select("#stats").html("");
    	
    stats.append("div")
    .text("Columns : " + keys.length)

  stats.append("div")
    .text("Rows : " + data.length)
    stats.append("hr");
    
    dropdown1("#stats",keys);


  d3.select("#table")
    .html("")
    .append("tr")	
    .selectAll("th")
    .data(keys)
    .enter().append("th").attr("class","thead")
      .text(function(d) { return d; });

  d3.select("#table")
    .selectAll("tr")
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