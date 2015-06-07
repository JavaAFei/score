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
	//�˳�
	$('#loginout').click(function() {
		window.location.href = "<%=basePath%>admin/loginout";
	});
	//�޸�����
	$('#editPw').click(function() {
		window.location.href = "<%=basePath%>editPassword.jsp";
	});
	//������ʾ�ֶ�
	$('#option').click(function() {
		window.location.href = "<%=basePath%>option.jsp";
	});
	//��ѯ����
	queryData();
});

//�ϴ�Excel
function uploadExcel(){
         //�õ��ϴ��ļ���ȫ·��  
         var fileName= $('#uploadExcel').filebox('getValue'); 
        //���л���У��  
        if(fileName==""){
           $.messager.alert('��ʾ','��ѡ���ϴ��ļ���','info');   
        }else{
            //���ļ���ʽ����У��  
            var d1=/\.[^\.]+$/.exec(fileName);   
            if(d1==".xls"||d1==".xlsx"){ 
                 //�ύ��  
                 document.getElementById("questionTypesManage").submit();     
                 $.messager.alert('��ʾ','�����ɹ���','info');          
           }else{  
               $.messager.alert('��ʾ','��ѡ��xls��ʽ�ļ���','info');   
               $('#uploadExcel').filebox('setValue','');   
           }
        }
 }
 function initDataGrid(){
 	$('#dg').datagrid({
 		title:'ѧ���ɼ��б�', //����
 		rownumbers:true,//��ʾ�к�
 		singleSelect:true,//��ѡ
 		pagination:true,//��ҳ�ؼ�
 		pageSize: 15,//ÿҳ��ʾ�ļ�¼������Ĭ��Ϊ15 
	    pageList: [15,30,50,100],//��������ÿҳ��¼�������б� 
 		fitColumns:true,
 		url:"stu/queryAll",
 		toolbar:'#tb',//������
 		onLoadSuccess:function(data){
			if(data.rows.length==0){
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="6">û������</td></tr>');
			}
			 $(this).datagrid("fixRownumber");
		}
 	});
	//���÷�ҳ�ؼ� 
	var p = $('#dg').datagrid('getPager'); 
	$(p).pagination({ 
	    pageSize: 15,//ÿҳ��ʾ�ļ�¼������Ĭ��Ϊ15 
	    pageList: [15,30,50,100],//��������ÿҳ��¼�������б� 
	    beforePageText: '��',//ҳ���ı���ǰ��ʾ�ĺ��� 
	    afterPageText: 'ҳ    �� {pages} ҳ', 
	    displayMsg: '��ǰ��ʾ {from} - {to} ����¼   �� {total} ����¼'
	});
 }
 
 //��������
 function queryData(){
 	$.ajax({
          url:"stu/queryAll",
          type:'POST',
          async:false,
          success:function(data){
	          if(data != 1){
	          	  var jsonData = eval("("+data+")"); 
		          var anArray = jsonData.columns;
		          //��̬�����
			      $.each(anArray,function(n,value) {
		    		$(".dgtr").append("<th data-options=\"field:'"+value+"' \">"+value+"</th>");
			     });
			     $(".dgtr").append("<th field=\"23\" formatter=\"delFormat\" width=\"100\">����</th>");
	          } 
		     //��ʼ�����
		     initDataGrid();
		     //�������
			 //$("#dg").datagrid("loadData",jsonData);
          }
	});
 }
 //��ѯ����
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
			//��ȡ���һ�е�number����,������һ��
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			//������ĳЩ���������,�ǲ�֧�ֻ�ȡ����Ԫ�صĿ��,����ȡ��һ��
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			//Ĭ�Ͽ����25,����ֻ�д���25��ʱ��Ž���fix
			if (width > 25) {
				//���5������,����һ��߾�
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
				//�޸��˿��֮��,��Ҫ�������������¼���,���Ե���resize
				$(this).datagrid("resize");
				//һЩ������
				clone.remove();
				clone = null;
			} else {
				//��ԭ��Ĭ��״̬
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
			}
		});
	}
});

//��ʼ������ɾ����ť
function delFormat(value,row,index){
    var res = "<a name='delete' href='javascript:void(0);' class=\'easyui-linkbutton\'  iconCls=\'icon-cancel\'  onclick=\"deleteById('"+row.studentId+"','1');\" title='ɾ��'>ɾ��</a>"; 
    return res;
 };
 function deleteById(studentId){
 	$.messager.confirm('��ʾ', '��ȷ��ɾ�������ɼ���?', function(r){
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
			 				$.messager.alert('��ʾ','ɾ��ʧ�ܣ���ˢ�º����Ի���ϵ����Ա��');
			 			}
			 		}
			 	})
	});	
 }
  function deleteAll(){
 	$.messager.confirm('��ʾ', '��ȷ�����ȫ���ɼ���?', function(r){
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
	 				$.messager.alert('��ʾ','ɾ��ʧ�ܣ���ˢ�º����Ի���ϵ����Ա��');
	 			}
	 		}
	 	})
	});	
 }
</script>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>��ҳ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body>
  ��ӭ��¼��${sessionScope.adminObj.name} 
   <input type="button" id="loginout" value="�˳�"/>
   <input type="button" id="editPw" value="�޸�����"/> 
   <input type="button" id="option" value="������ʾ�ֶ�"/><br>
    <form id="questionTypesManage"  method="post"  action="stu/upload" enctype="multipart/form-data">  
	    <div style="margin-bottom:20px">
			<div>�ϴ��ɼ�:</div>
			<input name="uploadExcel" id="uploadExcel" class="easyui-filebox"  data-options="prompt:'��ѡ���ļ�......'"   buttonText="���ѡ��"   style="width:30%">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:5%" onclick="uploadExcel()">�ϴ��ļ�</a>
		</div>
	</form>
    <table id="dg" style="width:100%;height:500px  ;" >
		<thead>
			<tr class = "dgtr">
				<th data-options="field:'studentId' ,hidden:true">studentId</th>
				<th data-options="field:'examNo'">����</th>
				<th data-options="field:'name'">����</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:3px">
		<span>���ţ�</span>
		<input id="examNoS" style="line-height:26px;border:1px solid #ccc">
		<span>������</span>
		<input id="nameS" style="line-height:26px;border:1px solid #ccc">
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onclick="doSearch()">��ѯ</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="deleteAll()">��ճɼ�</a>
	</div>
  </body>
</html>
