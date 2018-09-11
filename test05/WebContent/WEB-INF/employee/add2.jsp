<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加员工</title>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
#main {
	width: 400px;
	margin: 20px auto;
}
#photos {
text-align: center;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">

$().ready(function(){
	//点击上传按钮把头像上传 
	//新建一个form表单，把要上传的文件加到这个表单里，表单里面name叫photo的第一个文本域里的第一个文件 
	$("#upload").click(function(){
		var formData=new FormData();
		
		for(var i=0;i<$("[name=photo]")[0].files.length;i++){ 
			formData.append("photo",$("[name=photo]")[0].files[i]);
		}
		$.ajax({
			url:"employee?type=upload",
			type:"post",
			data:formData,
			//以这种方式上传还需要加上这三个东西
			cache:false,
			processData:false,
			contentType:false,
			dataType:"text",
			success:function(data){
			
				var str="<img src='pic/"+data+"'/>";
				str+="<input type='hidden' name='picture' value='"+data+"'/>";
				$("#photos").append(str);
				
			}
			
		})
		
	})

$(document).on("click","#photos img",function(){
	//img下边是隐藏域，先把隐藏域移除在移除img
	$(this).next().remove();
	$(this).remove();
	
})
	
	



	
	
})

</script>
</head>
<body>
	<div id="main">
		<form action="employee?type=add2" class="form-horizontal" role="form"
			method="post" >
			
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						placeholder="请输入姓名">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					<input type="radio" name="sex" value="男">男 <input
						type="radio" name="sex" value="女">女
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">年龄</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="age"
						placeholder="请输入年龄">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">部门</label>
				<div class="col-sm-10">
					<select name="depId" class="form-control" >
						<option value="">部门</option>
						<c:forEach items="${depList}" var="dep">
							<!--  option中间显示的是给用户看的，value是程序员用的，所以value用id-->
							<option value="${dep.id }">${dep.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">头像</label>
				<div class="col-sm-7">
					<input type="file" class="form-control" name="photo" multiple>
				</div>
				<div class="col-sm-3">
					<input type="button" class="form-control" value="上传" id="upload">
				</div>
			</div>
			<div class="form-group" id="photos"></div>


			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</div>
	</div>
	</form>
	</div>
</body>
</html>