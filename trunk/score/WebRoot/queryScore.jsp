<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/jsAndCss.jsp" %>
<link rel="stylesheet" type="text/css" href="${cssPath}/public.css">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script>
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

</script>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>主页</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body style="background: #2e8bfc;">
		<div class="score_serch">
			<h3>山东劳动职业技术学院-考试成绩查询结果</h3>
			<div class="score_box">
				<table width="70%" cellpadding="0" cellspacing="0" border="1">
					<tr>
						<td class="align_r">姓名：</td>
						<td class="align_l">${sessionScope.studentObj.name }</td>
					</tr>
					<tr>
						<td class="align_r"> 考号：</td>
						<td class="align_l">${sessionScope.studentObj.examNo }</td>
					</tr>
					<tr class="subjet">
						<td class="align_r">科目：</td>
						<td class="align_l">结果</td>
					</tr>
					<c:forEach items="${scoreList}" var="score">
						<tr>
							<td class="align_r">${score.name }：</td>
							<td class="align_l">${score.value }</td>
						</tr>
				    </c:forEach>
				</table>
			</div>
		</div>
  </body>
</html>
