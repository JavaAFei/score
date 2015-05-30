<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
$(function() {
    $('.page-container form .username, .page-container form .password').keyup(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });
    
    $("#login").click(function(){
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
        
		$.ajax({
	          url:"stu/login",
	          type:'POST',
	          data:$('#loginForm').serialize(),
	          async:false,
	          success:function(data){
		          if(data == 'success'){
					window.location.href = "stu/query";
		          }else{
		          	$("#result").text(data);
		          }
	          }
		});
        
    });
});
</script>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>  
  <body>
    <div class="page-container">
            <h1>成绩查询系统登录</h1>
            <form action="" method="post"  id="loginForm">
                <input type="text" name="name" class="username" placeholder="请输入姓名"/>
                <input type="text" name="examNo" class="password" placeholder="请输入考号"/>
                	</br></br><span id = "result"></span>
                <button type ="button"  id ="login">登录</button>
                <div class="error"><span>+</span></div>
            </form>
            <div class="connect">
	            <c:if test="${lastUpdateTime ne null && lastUpdateTime ne ''}">
	            	<p style="width: 330px;">数据更新于<fmt:formatDate value="${lastUpdateTime}" pattern="yyyy年MM月dd日HH点mm分" /></p>
	            	<p></p> 
	            </c:if>
                <p>山东劳动职业技术学院</p>
            </div>
        </div>
  </body>
</html>
