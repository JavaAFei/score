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
	//退出
	$('#loginout').click(function() {
		window.location.href = "<%=basePath%>admin/loginout";
	});
	//修改密码
	$('#editPw').click(function() {
		window.location.href = "<%=basePath%>editPassword.jsp";
	});
	//配置显示字段
	$('#option').click(function() {
		window.location.href = "<%=basePath%>option.jsp";
	});
	//查询数据
	queryData();
});

//上传Excel
function uploadExcel(){
         //得到上传文件的全路径  
         var fileName= $('#uploadExcel').filebox('getValue'); 
        //进行基本校验  
        if(fileName==""){
           $.messager.alert('提示','请选择上传文件！','info');   
        }else{
            //对文件格式进行校验  
            var d1=/\.[^\.]+$/.exec(fileName);   
            if(d1==".xls"||d1==".xlsx"){ 
                 //提交表单  
                 document.getElementById("questionTypesManage").submit();     
                 $.messager.alert('提示','操作成功！','info');          
           }else{  
               $.messager.alert('提示','请选择xls格式文件！','info');   
               $('#uploadExcel').filebox('setValue','');   
           }
        }
 }
 function initDataGrid(){
 	$('#dg').datagrid({
 		title:'学生成绩列表', //标题
 		rownumbers:true,//显示行号
 		singleSelect:true,//单选
 		pagination:true,//分页控件
 		pageSize: 15,//每页显示的记录条数，默认为15 
	    pageList: [15,30,50,100],//可以设置每页记录条数的列表 
 		fitColumns:true,
 		url:"stu/queryAll",
 		toolbar:'#tb',//工具栏
 		onLoadSuccess:function(data){
			if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="6">没有数据</td></tr>');
			}
			 $(this).datagrid("fixRownumber");
		}
 	});
	//设置分页控件 
	var p = $('#dg').datagrid('getPager'); 
	$(p).pagination({ 
	    pageSize: 15,//每页显示的记录条数，默认为15 
	    pageList: [15,30,50,100],//可以设置每页记录条数的列表 
	    beforePageText: '第',//页数文本框前显示的汉字 
	    afterPageText: '页    共 {pages} 页', 
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
 }
 
 //加载数据
 function queryData(){
 	$.ajax({
          url:"stu/queryAll",
          type:'POST',
          async:false,
          success:function(data){
	          if(data != 1){
	          	  var jsonData = eval("("+data+")"); 
		          var anArray = jsonData.columns;
		          //动态添加列
			      $.each(anArray,function(n,value) {
		    		$(".dgtr").append("<th data-options=\"field:'"+value+"' \">"+value+"</th>");
			     });
			     $(".dgtr").append("<th field=\"23\" formatter=\"delFormat\" width=\"100\">操作</th>");
	          } 
		     //初始化表格
		     initDataGrid();
		     //填充数据
			 //$("#dg").datagrid("loadData",jsonData);
          }
	});
 }
 //查询方法
 function doSearch(){
	$('#dg').datagrid('load',{
		examNoS: $('#examNoS').val(),
		nameS: $('#nameS').val()
	});
}

$.extend($.fn.datagrid.methods, {
	fixRownumber : function (jq) {
		return jq.each(function () {
			var panel = $(this).datagrid("getPanel");
			//获取最后一行的number容器,并拷贝一份
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			//由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			//默认宽度是25,所以只有大于25的时候才进行fix
			if (width > 25) {
				//多加5个像素,保持一点边距
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
				//修改了宽度之后,需要对容器进行重新计算,所以调用resize
				$(this).datagrid("resize");
				//一些清理工作
				clone.remove();
				clone = null;
			} else {
				//还原成默认状态
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
			}
		});
	}
});

//初始化单个删除按钮
function delFormat(value,row,index){
    var res = "<a name='delete' href='javascript:void(0);' class=\'easyui-linkbutton\'  iconCls=\'icon-cancel\'  onclick=\"deleteById('"+row.studentId+"','1');\" title='删除'>删除</a>"; 
    return res;
 };
 function deleteById(studentId){
 	$.messager.confirm('提示', '你确定删除这条成绩吗?', function(r){
				if (!r){
					return;
				}
			 	$.ajax({
			 		url:"stu/del",
			 		type:'POST',
			 		data:{studentId:studentId},
			 		async:false,
			 		success:function(data){
			 			if(data=='0'){
			 				$('#dg').datagrid('reload');
			 			}else{
			 				$.messager.alert('提示','删除失败，请刷新后重试或联系管理员！');
			 			}
			 		}
			 	})
	});	
 }
  function deleteAll(){
 	$.messager.confirm('提示', '你确定清空全部成绩吗?', function(r){
		if (!r){
			return;
		}
	 	$.ajax({
	 		url:"stu/delAll",
	 		type:'POST',
	 		async:false,
	 		success:function(data){
	 			if(data=='0'){
	 				$('#dg').datagrid('reload');
	 			}else{
	 				$.messager.alert('提示','删除失败，请刷新后重试或联系管理员！');
	 			}
	 		}
	 	})
	});	
 }
</script>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>主页</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body>
  欢迎登录，${sessionScope.adminObj.name} 
   <input type="button" id="loginout" value="退出"/>
   <input type="button" id="editPw" value="修改密码"/> 
   <input type="button" id="option" value="配置显示字段"/><br>
    <form id="questionTypesManage"  method="post"  action="stu/upload" enctype="multipart/form-data">  
	    <div style="margin-bottom:20px">
			<div>上传成绩:</div>
			<input name="uploadExcel" id="uploadExcel" class="easyui-filebox"  data-options="prompt:'请选择文件......'"   buttonText="点击选择"   style="width:30%">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:5%" onclick="uploadExcel()">上传文件</a>
		</div>
	</form>
    <table id="dg" style="width:100%;height:500px  ;" >
		<thead>
			<tr class = "dgtr">
				<th data-options="field:'studentId' ,hidden:true">studentId</th>
				<th data-options="field:'examNo'">考号</th>
				<th data-options="field:'name'">姓名</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:3px">
		<span>考号：</span>
		<input id="examNoS" style="line-height:26px;border:1px solid #ccc">
		<span>姓名：</span>
		<input id="nameS" style="line-height:26px;border:1px solid #ccc">
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onclick="doSearch()">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="deleteAll()">清空成绩</a>
	</div>
  </body>
</html>
