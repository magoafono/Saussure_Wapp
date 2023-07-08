/**
 * à 
 */

function greekselection(){

	var t = '';
	if (window.getSelection) {
		t = window.getSelection();
	} else if (document.getSelection) {
		t = document.getSelection();
	} else if (document.selection) {
		t = document.selection.createRange().text;
	}

//	var element = document.getElementById("greektextarea");
//	var value = element.value;
//	var valueDIV = element.innerHTML;
//	var start = element.selectionStart;
//	var end = element.selectionEnd;
//	var select = valueDIV.substring(start,end);
	var start = 0;
	var end = 0;
	var targetElement = document.getElementById("form:greektext");
	targetElement.value= t + " [" + start + "$" + end + ").";
	targetElement.innerHTML = t + " [" + start + "$" + end + ").";
	//alert(select);
	//confirm('selezione1' + valueDIV + "[" + start + "$" + end + ")" );
}

/**
 * 
 */

function arabicselection(){

	var t = '';
	if (window.getSelection) {
		t = window.getSelection();
	} else if (document.getSelection) {
		t = document.getSelection();
	} else if (document.selection) {
		t = document.selection.createRange().text;
	}

//	var element = document.getElementById("form:arabictextarea");
//	var value = element.value;
//	var start = element.selectionStart;
//	var end = element.selectionEnd;
//	var select = value.substring(start,end);
	var start = 0;
	var end = 0;
	var targetElement = document.getElementById("form:arabictext");
	targetElement.value = t + " [" + start + "$" + end + ").";
	targetElement.innerHTML = t + " [" + start + "$" + end + ").";
	//targetElement.value= select + " [" + start + "$" + end + ")٠";
	//targetElement.innerHTML = select + " [" + start + "$" + end + ")&#x0660;";
	//alert(select);
	//confirm(t);
}

function zoommo() {
	$(".fancybox").fancybox();
}

function dynamic_zoom() {
	//alert("ciao");
	$('.loupe1').ClassyLoupe({
		'default_zoom': 100,
		'shape' : 'square',
		'default_size' : 450,
		'glossy' : false,
		'drop_shadow' : false,
		'loupe_toggle_time' : 0,
        'loupe_toggle_easing': 'linear',
        'overlay_effect_time': 0,
        'overlay_effect_easing': 'swing'

	});
}


function notaoff(){

}