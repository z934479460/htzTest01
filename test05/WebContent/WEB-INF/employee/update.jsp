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
	width: 400px;
	margin: 20px auto;
}
</style>
</head>
<body>

	<div id="main">
		<form action="employee" class="form-horizontal" role="form"
			method="post">
			<input type="hidden" name="type" value="update" /> <input
				type="hidden" name="id" value="${emp.id }" />
			<!-- 这里点保存的时候要把id传过去，但不是给用户看的 -->
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						value="${emp.name}">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					<input type="radio" name="sex"
						<c:if test="${emp.sex=='男'}">
						checked </c:if> value="男">男
					<input type="radio" name="sex"
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
					<option value="">部门</option>
					<c:forEach items="${depList}" var="dep">
					<!--  option中间显示的是给用户看的，value是程序员用的，所以value用id-->
					<option value="${dep.id }"<c:if test="${dep.id==emp.dep.id}">selected</c:if>>${dep.name}</option>
				</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>