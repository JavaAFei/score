<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/score/user/addUser" method="post">
		<h3>添加用户信息</h3>
		
		姓名：<input type="text" name="userName" id="userName"
			value="" /> 年龄：<input type="text" name="age" id="age"
			value="" /> <input type="submit" value="提交" />
	</form>
</body>
</html>