<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	pageContext.setAttribute("basePath", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript"
	src="${basePath}/static/js/jquery-1.11.2.min.js"></script>
<link
	href="${basePath}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${basePath}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script type="text/javascript">			
			//保存
			function save(){
				if($("#excel").val()=="" || document.getElementById("excel").files[0] =='请选择xls格式的文件'){
					
					$("#excel").tips({
						side:3,
			            msg:'请选择文件',
			            bg:'#AE81FF',
			            time:3
			        });
					return false;
				}
				
				$("#Form").submit();
			}
			
			function fileType(obj){
				var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
			    if(fileType != '.xls'){
			    	$("#excel").tips({
						side:3,
			            msg:'请上传xls格式的文件',
			            bg:'#AE81FF',
			            time:3
			        });
			    	$("#excel").val('');
			    	document.getElementById("excel").files[0] = '请选择xls格式的文件';
			    }
			}
		</script>
	</head>
<body>
	<form action="${basePath}/readExcel.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<table style="width:95%;" >		
			<tr>
				<td style="padding-top: 20px;"><input type="file" id="excel" name="excel" style="width:300px;" onchange="fileType(this)" /></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">导入</a>
					<a class="btn btn-mini btn-danger">取消</a>
					<a class="btn btn-mini btn-success" onclick="window.location.href='${basePath}/downExcel.do'">下载模版</a>
				</td>
			</tr>
		</table>
		</div>
	</form>
	
	


		<script src="${basePath}/static/js/ace/elements.fileinput.js"></script>
		<script src="${basePath}/static/js/ace/ace.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="${basePath}/static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(function() {
			//上传
			$('#excel').ace_file_input({
				no_file:'请选择EXCEL ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'xls|xls',
				blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			
		});
		
		</script>
	
</body>
</html>