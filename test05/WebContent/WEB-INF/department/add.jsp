<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List,entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加部门</title>
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
		<form action="department" class="form-horizontal" role="form"
			method="post">
			<input type="hidden" name="type" value="add">
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						placeholder="请输入名称">
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