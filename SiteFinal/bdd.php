<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Wada - Base de données</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="bootstrap-table.css">
  </head>


  <body> 
    <?php include "menu.html"; ?>

	<script type="text/javascript">
		$('#table').bootstrapTable({
		    url: 'data/data1.json'
		});
	</script>

	<table data-url="data/data1.json" data-toggle="table" >
	    <thead>
		    <tr>
		        <th data-field="id" data-halign="center" data-align="center" >Item ID</th>
		        <th data-field="name" data-halign="center" data-align="center">Item Name</th>
		        <th data-field="price" data-halign="center" data-align="center">Item Price</th>
		    </tr>
	    </thead>
	</table>




    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-table.js"></script>
  </body>
</html>