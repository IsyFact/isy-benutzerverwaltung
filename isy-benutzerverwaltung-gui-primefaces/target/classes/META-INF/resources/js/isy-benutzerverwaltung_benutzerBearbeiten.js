(function() {

	zaehleSelectCheckboxMenuEintraegeRollen();

})();

/**
 * Zaehlt die Anzahl selektierter SelectCheckboxMenu-Eintraege und schreibt
 * diese in das Label
 */
function zaehleSelectCheckboxMenuEintraegeRollen() {
	var selectCheckboxMenuId = "rolle";
	var inputFelder = $('[id$=' + selectCheckboxMenuId + '] input');
	var i = 0;

	inputFelder.each(function() {
		if ($(this).is(':checked')) {
			i = i + 1;
		}
	});

	$selectCheckboxMenuLabel = $('[id$=' + selectCheckboxMenuId + ']').find(
			'.ui-selectcheckboxmenu-label');

	var anzahlAusgewaehlt = '';
	var ausgewaehltText = '';
	if (i > 0) {
		anzahlAusgewaehlt = i.toString();
		ausgewaehltText = anzahlAusgewaehlt + ' ausgewählt';
	} else {
		ausgewaehltText = 'Bitte wählen...';
	}

	$selectCheckboxMenuLabel.html(ausgewaehltText);
}