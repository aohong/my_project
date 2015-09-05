var URL_PRE="http://localhost:8080/";
//var URL_PRE="";

$(document).ready(function(){

	$("body").prepend($("<div />").load(URL_PRE+"html/common/header.html"));
	$("body").append($("<div />").load(URL_PRE+"html/common/footer.html"));

});

//方法二：
(function ($) {
	$.getUrlParam = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
})(jQuery);