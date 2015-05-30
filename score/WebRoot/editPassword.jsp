<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/jsAndCss.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>修改密码</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <script type="text/javascript">
		$(function(){
			$('#oldPassword').validatebox({    
			    required: true, 
			    missingMessage:'请输入新密码！' ,
			    novalidate:true
			}); 
			$('#newPassWord').validatebox({    
			    required: true, 
			    missingMessage:'请输入新密码！' ,
			    novalidate:true,
			    validType:['password','length[6,20]']
			}); 
			$('#renewPassWord').validatebox({    
			    required: true, 
			    missingMessage:'请重新输入新密码！' ,
			    novalidate:true,
			   validType:'same[\'newPassWord\']' 
			}); 
			/*
			$('#passwordForm').form({
			     url:'admin/editPassword',
			     onSubmit:function(){
			         return $(this).form('validate');
			     },
			     success:function(data){
			         if(data == '0'){
			         	alert('密码修改成功！');
					// ;
			         }else{
			         	alert('密码修改失败！');
			         }
			     }
			 }); */
			
			$('.validatebox-text').bind('blur', function(){
				$(this).validatebox('enableValidation').validatebox('validate');
			});
		})
		
		function toEdit(){
			$('.validatebox-text').validatebox('enableValidation').validatebox('validate');
			if($('#passwordForm').form('validate')){
				 	$.ajax({
			          url:"admin/editPassword",
			          type:'POST',
			          data: $('#passwordForm').serialize(),
			          async:false,
			          success:function(data){
				          if(data == '0'){
				         	alert('密码修改成功！');
							window.location.href = "<%=basePath%>/main.jsp";
				         }else{
				         	alert('密码修改失败！');
				         }
			          }
				});
			}
		}
		function tuBack(){
			window.location.href = "<%=basePath%>/main.jsp";
		}
	</script>
	<body>
	<form id="passwordForm"  method="post"  action="admin/editPassword" enctype="multipart/form-data">  
  		<div style="margin-bottom:20px">
			 请输入原密码： 
			<input id="oldPassword"  name = "oldPassword"  class="easyui-validatebox textbox"  style="width:200px;height:32px" "><br/>
			请输入新密码：
			<input id="newPassWord"  name = "newPassWord" class="easyui-validatebox textbox"  style="width:200px;height:32px"" ><br/>
   			请重新输入新密码：
   			<input  id="renewPassWord"  name = "renewPassWord"  class="easyui-validatebox textbox"  style="width:200px;height:32px" >
   			<br/>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="toEdit()">确定</a>
			<a href="javascript:void(0)"" class="easyui-linkbutton" onclick="tuBack()">取消</a>
			<br/>
		</div>
	</form> 
	</body>
</html>
