<?php


function printCsvToTable($fileName){

	$fp = fopen($fileName,'r') or die("can't open file");
	print "<table>\n";
	while($csv_line = fgetcsv($fp,1024)) {
	    print '<tr>';
	    for ($i = 0, $j = count($csv_line); $i < $j; $i++) {
		print '<td>'.$csv_line[$i].'</td>';
	    }
	    print "</tr>\n";
	}
	print '</table>';
	fclose($fp) or die("can't close file");

}

?>
