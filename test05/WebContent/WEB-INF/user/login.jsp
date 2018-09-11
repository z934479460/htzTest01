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
	margin-top:150px;	
}
#btn{
text-align: center;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
//判断本页面是不是最高页面，这两个是页面的内置对象 
if(self!=top){
	top.location="user";
}
$().ready(function(){
	$("#register").click(function(){
		location.href = "user?type=showRegister";
	})
})
</script>
</head>
<body>
	<div id="main">
		<form action="user?type=doLogin" method="post" class="form-horizontal"
			role="form">
			<div class="form-group">
				<label class="col-xs-2 control-label">账号:</label>
				<div class="col-xs-6">
					<input type="text" name="username" class="form-control"
						placeholder="请输入账号" value="${name }"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">密码:</label>
				<div class="col-xs-6">
					<input type="password" name="password" class="form-control"
						placeholder="请输入密码" />
				</div>	</div>
				<div id="btn">
					<input type="submit" value="登录" class="btn btn-primary" />
					<input id="register" type="button" value="注册" class="btn btn-primary">
				</div>
		</form>
		
	</div>
	<div>${mes } </div>
</body>
</html>