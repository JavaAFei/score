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
 		title:'配置显示的列', //标题
 		rownumbers:true,//显示行号
 		singleSelect:true,//单选
 		fitColumns:true,
 		url:"admin/option",
 		onLoadSuccess:function(data){
			if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="6">没有数据</td></tr>');
			}
		} 
 	});
});
function optionFormat(value,row,index){
    var res = "";  
    if(row.isShow=='显示'){  
        res="<a name='up' href='javascript:void(0);' onclick=\"updateOption('"+row.id+"','1');\" title='隐藏'>隐藏</a>";  
    }else if(row.isShow=='隐藏'){  
         res="<a name='down' href='javascript:void(0);' onclick=\"updateOption('"+row.id+"','0');\" title='显示'>显示</a>";  
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
          		alert('修改失败')
          	}
          	$('#dg').datagrid('reload');
          }
	});
 }  
</script>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>配置显示</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body>
  	配置上传的列是否显示</br>
  	<table id="dg" style="width:300px;height:400px  ;" >
		<thead>
			<tr class = "dgtr"> 
				<th data-options="field:'columnName'">列名称</th>
				<th data-options="field:'isShow'">是否显示</th> 
				<th field="23" formatter="optionFormat" width="100">配置</th>  
			</tr>
		</thead>
	</table>
  </body>
</html>
