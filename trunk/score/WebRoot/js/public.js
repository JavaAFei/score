$(function(){
	var logindiv_height = $(".score_serch").css("height").replace("px", "");
	$(".score_serch").css({
          "margin-top": (window.screen.availHeight - logindiv_height) / 2 -120
    });
            
    var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s; 
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
	(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;		
	
	if (Sys.safari || Sys.chrome){
		$(".score_serch").css({
               "margin-top": (window.screen.availHeight - logindiv_height) / 2 -120
            });
	}else{
		$(".score_serch").css({
               "margin-top": (window.screen.availHeight - logindiv_height) / 2 -140
            });
	}
	
})
