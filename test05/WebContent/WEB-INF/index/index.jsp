<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
* {
	margin: 0;
	padding: 0;
}

#main {
	overflow: hidden;
}

a {
	color: #fff;
	text-decoration: none;
}

#left {
	width: 200px;
	height: 600px;
	float: left;
	padding-left: 20px;
}

#right {
	width: 800px;
	height: 600px;
	float: left;
}

#top, #bottom {
	clear: both;
	height: 80px;
	background: #337ab7
}

.yi {
	width: 160px;
	height: 40px;
	background: #337ab7;
	color: #fff;
	margin-top: 10px;
	border-radius: 3px;
	text-align: center;
	line-height: 40px;
}

.er {
	list-style: none;
	width: 160px;
}

.er li {
	margin-top: 5px;
	text-align: center;
	background: #337a97;
	color: #fff;
	height: 30px;
	line-height: 30px;
	font-size: 14px;
}

.er a {
	color: white;
}

#num {
	position: absolute;
	right: 50px;
	top: 50px;
	color: white;
}

</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$(".yi").click(function() {
			$(this).next().slideToggle();
		})
	})
	var websocket = null;
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://192.168.0.181:8080/test05/websocket");
	} else {
		alert('没有建立websocket连接')
	}

	//接收到消息的回调方法
	websocket.onmessage = function(event) {
		$("#num").html(event.data);
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
	window.onbeforeunload = function() {
		websocket.close();
	}

	//关闭连接
	function closeWebSocket() {
		websocket.close();
	}

</script>
</head>
<body>
	<div id="container">
		<div id="top">
			<div id="num">${applicationScope.num }</div>
		</div>
		<div id="main">
			<div id="left">
				<div class="yi">员工管理</div>
				<ul class="er">
					<li><a href="employee" target="right">员工管理</a></li>
					<li><a href="employee?type=showAdd" target="right">添加员工</a></li>
				</ul>
				<div class="yi">部门管理</div>
				<ul class="er">
					<li><a href="department" target="right">部门管理</a></li>
					<li><a href="department?type=showAdd" target="right">添加部门</a></li>
				</ul>
				<div class="yi">项目管理</div>
				<ul class="er">
					<li><a href="project" target="right">项目管理</a></li>
					<li><a href="project?type=showAdd" target="right">添加项目</a></li>
				</ul>
				<div class="yi">绩效管理</div>
				<ul class="er">
					<li><a href="score" target="right">绩效查看</a></li>
					<li><a href="score?type=manage" target="right">绩效管理</a></li>
				</ul>
			</div>
			<iframe id="right" name="right" scrolling="no" frameborder="0"
				src="employee"></iframe>
		</div>
		<div id="bottom"></div>
	</div>
</body>
</html>