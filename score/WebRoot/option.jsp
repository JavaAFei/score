<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/jsAndCss.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script>
$(function(){
	$('#dg').datagrid({
 		title:'������ʾ����', //����
 		rownumbers:true,//��ʾ�к�
 		singleSelect:true,//��ѡ
 		fitColumns:true,
 		url:"admin/option",
 		onLoadSuccess:function(data){
			if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="6">û������</td></tr>');
			}
		} 
 	});
});
function optionFormat(value,row,index){
    var res = "";  
    if(row.isShow=='��ʾ'){  
        res="<a name='up' href='javascript:void(0);' onclick=\"updateOption('"+row.id+"','1');\" title='����'>����</a>";  
    }else if(row.isShow=='����'){  
         res="<a name='down' href='javascript:void(0);' onclick=\"updateOption('"+row.id+"','0');\" title='��ʾ'>��ʾ</a>";  
    }
    return res;
 };
 
 function updateOption(id,option){
	$.ajax({
          url:"admin/updateOption",
          type:'POST',
          async:false,
          data:{id:id,option:option},
          success:function(data){
          	if(data=='1'){
          		alert('�޸�ʧ��')
          	}
          	$('#dg').datagrid('reload');
          }
	});
 }  
</script>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>������ʾ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body>
  	�����ϴ������Ƿ���ʾ</br>
  	<table id="dg" style="width:300px;height:400px  ;" >
		<thead>
			<tr class = "dgtr"> 
				<th data-options="field:'columnName'">������</th>
				<th data-options="field:'isShow'">�Ƿ���ʾ</th> 
				<th field="23" formatter="optionFormat" width="100">����</th>  
			</tr>
		</thead>
	</table>
  </body>
</html>
