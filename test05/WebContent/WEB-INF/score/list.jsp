<%@page import="util.Pagination"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
#main {
	width: 600px;
	margin: 20px auto;
}
/*考虑到优先级问题，加上sc提高优先级 */
#sc .select {
	background: #337ab7
}

#biaoti {
	font-size: 20px;
	text-align: center;
	margin: 20px 20px;
	font-weight: bold;
}

#sc .td {
	width: 200px;
}

#sc input {
	width: 100px;
}

#sc select {
	width: 100px;
	height: 30px;
}
</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var selectId = -1;
		$("#save").click(function() {
			location.href = "score?type=save";
		})
 

		
		//用el表达式拿ye等属性，这是一种简化方式，依据的是ye的get方法 ，它可以不用getattribute直接拿p
		//el表达式可以直接拿setattribute设置的属性
	    if(${p.ye}<=1){
			//第一页的时候不能点上一页
			 $("#pre").addClass("disabled");
			 $("#pre").find("a").attr("onclick","return false");
		}
		if(${p.ye}>=${p.maxYe}){
			//第一页的时候不能点上一页
			 $("#next").addClass("disabled");
			 $("#next").find("a").attr("onclick","return false");
	    } 
		
		
	}) 
</script>
</head>
<body>

	<div id="main">
		<div id="biaoti">绩效信息表</div>


		<form action="score" class="form-horizontal" role="form" method="post">

			<div class="form-group">

				<div class="col-sm-3">
					<input type="text" class="form-control" name="name"
						placeholder="姓名" value=${c.employee.name}>
				</div>

				<div class="col-sm-3">
					<select name="depId" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${depList}" var="dep">
							<!--  option中间显示的是给用户看的，value是程序员用的，所以value用id
					if是为了搜索之后下拉框的内容不被重置-->
							<option value="${dep.id }"
								<c:if test="${dep.id==c.employee.dep.id}">selected</c:if>>${dep.name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-sm-3">
					<select name="proId" class="form-control">
						<option value="">项目</option>
						<c:forEach items="${proList}" var="pro">
							<!--  option中间显示的是给用户看的，value是程序员用的，所以value用id
					if是为了搜索之后下拉框的内容不被重置-->
							<option value="${pro.id }"
						<c:if test="${pro.id==c.project.id}">selected</c:if>>${pro.name}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="col-sm-3">
					<input type="text" class="form-control" name="value"
						placeholder="成绩" value=${c.value}>
				</div>
				
				<div class="col-sm-3">
					<select name="grade" class="form-control">
						<option value="">等级</option>
						<c:forEach items="${grades}" var="grade">
							<!--  option中间显示的是给用户看的，value是程序员用的，所以value用id
					if是为了搜索之后下拉框的内容不被重置-->
							<option value="${grade }"
								<c:if test="${grade==c.grade}">selected</c:if>>${grade.value}</option>
						</c:forEach>
					</select>
				</div>

				<div class=" col-sm-3">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>


		</form>


		<table id=sc class="table table-bordered table-striped table-hover">
			<thead>
				<tr id=xinxi>
					<th>姓名</th>
					<th>部门</th>
					<th>项目</th>
					<th>成绩</th>
					<th>等级</th>
				</tr>
			</thead>
			<tbody>
				<!--遍历list，sc是自己定义的每次循环出来的变量  -->
				<c:forEach items="${list}" var="sc">
					<!-- data-id是为了如果id这一列不显示的话依然存在一个id可以被用来拿数据 -->
					<tr data-id="${sc.id}">
						<td>${sc.employee.name}</td>
						<td>${sc.employee.dep.name}</td>
						<td>${sc.project.name}</td>
						<td>${sc.value}</td>
						<td>${sc.grade.value}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination">
			<li id="frist"><a href="score?ye=1&name=${c.employee.name }&depId=${c.employee.dep.id}&proId=${c.project.id}">首页;</a></li>
			<li id="pre"><a href="score?ye=${p.ye-1}&name=${c.employee.name }&depId=${c.employee.dep.id}&proId=${c.project.id}">上一页;</a></li>
			<!--这是传统的for循环，有限次的， beginYe和endYe是显示出来的首尾页 ,status.index就是原来的i -->
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}">class="active"</c:if>><a
					href="score?ye=${status.index}&name=${c.employee.name }&depId=${c.employee.dep.id}&proId=${c.project.id}">${status.index}</a></li>

			</c:forEach>
			<li id="next"><a href="score?ye=${p.ye+1}&name=${c.employee.name }&depId=${c.employee.dep.id}&proId=${c.project.id}">下一页;</a></li>
			<li id="last"><a href="score?ye=${p.maxYe}&name=${c.employee.name }&depId=${c.employee.dep.id}&proId=${c.project.id}">尾页;</a></li>
		</ul>

		<div>
			<input type="button" value="保存" id="save" class="btn btn-primary" />
		</div>
	</div>
</body>
</html>