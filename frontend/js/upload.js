// make dataset globally available
var dz;

// load dataset and create table
function load_dataset(csv) {
  var data = d3.csv.parse(csv)
  create_table(data);
}

// create a table with column headers, types, and data
function create_table(data) {
  // table stats
  var keys = d3.keys(data[0]);

  var stats = d3.select("#stats")
    .html("")

  stats.append("div").attr("class","active")
    .text("Columns : " + keys.length)

  stats.append("div").attr("class","actvie")
    .text("Rows : " + data.length)

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