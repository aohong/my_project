var URL_PRE="http://localhost:8080/test/";
//var URL_PRE="";

$(document).ready(function(){

	$("body").prepend($("<div />").load(URL_PRE+"html/common/header.html"));
	$("body").append($("<div />").load(URL_PRE+"html/common/footer.html"));

});
