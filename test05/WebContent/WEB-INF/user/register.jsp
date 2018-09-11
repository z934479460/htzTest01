<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
 body{    
        background-image: url(/test05/pic/1.jpg);    
        background-size:cover;  
     }    
#main {
	width: 600px;
	margin: 20px auto;
	margin-top:120px;
}
</style>
</head>
<body>
	<div id="main">
		<form action="user?type=doRegister" method="post" class="form-horizontal"
			role="form">
			<div class="form-group">
				<label class="col-xs-2 control-label">用户名:</label>
				<div class="col-xs-6">
					<input type="text" name="username" class="form-control"
						placeholder="请输入用户名" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">密码:</label>
				<div class="col-xs-6">
					<input type="password" name="password" class="form-control"
						placeholder="请输入密码" />
				</div>
				</div>
				<div class="form-group">
				<label class="col-xs-2 control-label">确认密码:</label>
				<div class="col-xs-6">
					<input type="password" name="password1" class="form-control"
						placeholder="请确认密码" />
				</div>
				<div>
					<input type="submit" value="注册" class="btn btn-primary" />
					</div>
		</form>
		<div>${mes } </div>
	</div>
</body>
</html>