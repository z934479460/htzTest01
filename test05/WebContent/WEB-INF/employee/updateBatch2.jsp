<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改员工</title>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
#main {
	width: 900px;
	margin: 20px auto;
}

.emp {
	width: 400px;
	float: left;
	margin: 10px 50px 10px 0;
	border: 1px dashed #ccc;
}

#saveBtn {
	clear: both;
	/*text-align是对行级元素(图片，超链接，按钮 )和文字的居中，块级元素设置大小然后margin */
	text-align: center;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$("#save").click(function() {
			//遍历表单里的元素，取他们的数据 
			var emps = "";
			$(".emp").each(function(index, element) {
				//拿出当前元素的id，name是它们的一个属性
				var id = $(this).find("[name=id]").val();
				var name = $(this).find("[name=name]").val();
				//冒号是筛选选择器 
				var sex = $(this).find("[name=sex]:checked").val();
				var age = $(this).find("[name=age]").val();
				//要拿到val，name必须跟value在一个标签里，也就是说这里的name不能用depId，它是select的name
				if($(this).find("[name=a]:selected").val()==""){
					var depId=-1;
				}else{
					depId=$(this).find("[name=a]:selected").val();
				}
				
				//把拿到的员工信息字符串拼接起来
				emps += id + "," + name + "," + sex + "," + age + "," + depId + ";";
			})
			emps = emps.substring(0, emps.length - 1);
			//发送路径
			window.location.href = "employee?type=updateBatch2&emps=" + emps;
		})
	})
</script>
</head>
<body>

	<div id="main">
		
		<c:forEach items="${list}" var="emp">
		<form action="employee" class="form-horizontal emp" method="post">
			<!--点保存就传updateBatch1  -->
			<input type="hidden" name="type" value="updateBatch1" /> <input
				type="hidden" name="id" value="${emp.id }" />
			<!-- 这里点保存的时候要把ids传过去，但不是给用户看的 -->
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						value="${emp.name }">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					<input type="radio" name="sex" <c:if test="${emp.sex=='男'}">
						checked </c:if> value="男">男 <input type="radio" name="sex"
						<c:if test="${emp.sex=='女'}"> checked </c:if> value="女">女
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">年龄</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="age"
						value="${emp.age }">
				</div>
			</div>
			
				<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">部门</label>
				<div class="col-sm-10">
					<select name="depId" class="form-control">
					<option name ="a" value="">部门</option>
					<c:forEach items="${depList}" var="dep">
					<!--  option中间显示的是给用户看的，value是程序员用的，所以value用id-->
					<option name ="a" value="${dep.id }"<c:if test="${dep.id==emp.dep.id}">selected</c:if>>${dep.name}</option>
				</c:forEach>
					</select>
				</div>
			</div>
			
		</form>
		</c:forEach>
		<div id="saveBtn">
			<button id="save" type="button" class="btn btn-primary">保存</button>
		</div>
	</div>


</body>
</html>