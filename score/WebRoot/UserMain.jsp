<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ page import="java.util.*" %>
<%@ page import="com.jialin.entity.User" %>


<%
		  List list=(List)request.getAttribute("userlist");
		 for(Iterator iter=list.iterator();iter.hasNext();)
		 {
			 User user =(User)iter.next();
			 System.out.println(user.getId());
		}


%> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/score/js/jquery-1.7.1.min.js"></script>

<script type="text/javascript">
	function delUser(id) {
		alert("323")
		$.get("/score/user/delUser?id=" + id, function(data) {
			if (data.result == "success") {
				alert("删除成功");
				window.location.reload(); 
			} else {
				alert("操作失败");
			}
		});
	}

	function addUser() {
		window.location.href = "/score/user/toAddUser";
	}
</script>
</head>
<body>
	<h3>用户信息</h3>

	<table>
		<tr>
			<td>id</td>
			<td>用户名</td>
			<td>年龄</td>
			<td>操作</td>
		</tr>
		 <c:choose>
			<c:when test="${empty userlist }">
				<tr>
					<td colspan="3">无数据</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${userlist}" var="user">
					<tr>
						<td>${user.id }</td>
						<td>${user.userName }</td>
						<td>${user.age }</td>
						<td><a href="/score/user/toUpdateUser?id=${user.id} ">编辑</a> <a
							href="javascript:delUser('${user.id }')">删除</a></td>
					</tr>
				</c:forEach>
			</c:otherwise>

		</c:choose>
	</table>
	<input type="button" id="btnAdd" value="添加用户" onclick="addUser()">
</body>
</html>