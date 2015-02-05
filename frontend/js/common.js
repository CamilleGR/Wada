/**
 * @author user
 */

function openFiles() {
	$('#open2').popover('hide');
}

function progressUp(barId, val) {
	$(barId).css("width", "" + val + "%");
}


/*Snippet For random background color*/

function get_random_color() {
	var letters = 'ABCDE'.split('');
	var color = '#';
	for (var i = 0; i < 3; i++) {
		color += letters[Math.floor(Math.random() * letters.length)];
	}
	return color;
}

function get_random_color2() {
	var letters = '3456789'.split('');
	var color = '#';
	for (var i = 0; i < 3; i++) {
		color += letters[Math.floor(Math.random() * letters.length)];
	}
	return color;
}

function get_random_color_set(nb) {
	var colors = [];
	var i = 0;
	for (i; i < nb; i++) {
		colors.push(get_random_color());
	}
	return colors;
}