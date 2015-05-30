<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page import="java.util.*" %>
<%@ page import="com.jialin.entity.User" %>


<%
		/*   User user=(User)request.getAttribute("user");
		  System.out.println(user.getId());
		  System.out.println(user.getUserName());
		  System.out.println(user.getAge()); */
		  //注意request中已经有一个user，再放入一个user,不会将前一个覆盖
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/score/user/updateUser" method="post">
		<h3>更改用户信息</h3>
		<input type="hidden"  name="id"  value="${user1.id }"  />
 		姓名：<input type="text" name="userName"  value="${user1.userName} " /> 
 		年龄：<input type="text" name="age"   value="${user1.age} " /> 
 		<input type="submit" value="提交" />
	</form>
</body>
</html>