<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<link rel="stylesheet" href="<%=basePath%>css/reset.css">
<link rel="stylesheet" href="<%=basePath%>css/supersized.css">
<link rel="stylesheet" href="<%=basePath%>css/style.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/supersized.3.2.7.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/supersized-init.js"></script>
<script type="text/javascript">
/*
jQuery(document).ready(function() {
    $('.page-container form').submit(function(){
        var username = $(this).find('.username').val();
        var password = $(this).find('.password').val();
        if(username == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.username').focus();
            });
            return false;
        }
        if(password == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.password').focus();
            });
            return false;
        }
    });

    $('.page-container form .username, .page-container form .password').keyup(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });
});*/
</script>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>  
  <body>
    <div class="page-container">
            <h1>成绩录入系统登录</h1>
            <form action="admin/login" method="post">
                <input type="text" name="loginCode" class="username" placeholder="请输入登录账号"/>
                <input type="password" name="password" class="password" placeholder="请输入登录密码"/>
                <c:if test="${result ne null}">
                	</br></br><span>${result }</span>
                </c:if>
                <button type="submit">登录</button>
                <div class="error"><span>+</span></div>
            </form>
            <div class="connect">
                <p>山东劳动职业技术学院</p>
            </div>
        </div>
  </body>
</html>
