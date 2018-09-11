<%@page import="util.Pagination"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
/*#pro{
 border-collapse:collapse;
}
tr:nth-child(odd) {
background-color:red;
}
 tr:nth-child(even){
 background-color:yellow;
 }
.ccc{
width:80px;
height:50px;
text-align:center;
border:1px solid black;
}*/
#main {
	width: 600px;
	margin: 20px auto;
}
/*考虑到优先级问题，加上pro提高优先级 */
#pro .select {
	background: #337ab7
}

#biaoti {
	font-size: 20px;
	text-align: center;
	margin: 20px 20px;
	font-weight: bold;
}

#pro .td {
	width: 200px;
}

#pro input {
	width: 100px;
}

#pro select {
	width: 100px;
	height: 30px;
}
</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var selectId = -1;
		$("#add").click(function() {
			//用val方法拿到proId  
			var proId=$("#selectPro").val();
		//在刚加载出这个页面search的时候就把dep发过来了，点击add按钮直接把depId和proId发过去
			location.href = "dep2pro?type=add&depId=${dep.id}&proId="+proId;
		}) 
		//使用function，当nolist为空时，让add按钮失效  
		<c:if test="${f:length(noList)==0}">
		$("#add").unbind("click");
		$("#add").addClass("disabled");
		</c:if>
//或者可以使用jQuery判断
//if($("#selectPro").children().length==0){
	//$("#add").unbind("click");
	//$("#add").addClass("disabled");
//}
		$("#delete").click(function() {
			if (selectId > -1) {
				location.href = "dep2pro?type=delete&depId=${dep.id}&proId=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		
		
		//选中一行数据 ,选中的时候,先把所有行的这个类给移除，再给他加上select类修饰，让它变色,
		$("tr").click(function() {
			//toggleClass就是点的时候没有class就给加上，有就去掉
			$("tr").removeClass("select");
			$(this).addClass("select");
			//$(this).toggleClass("select");
			//然后依靠date-id来拿到选中的这一行的数据
			//selectId=$(this).children().eq(0).text();这是用id那一列来拿数据，如果不显示id的话就拿不了了 
			selectId = $(this).data("id");
		})

		
		//让首列不能被点 
		$("#xinxi").unbind("dblclick");
		$("#xinxi").unbind("click");
		
		
	
	})
</script>
</head>
<body>

	<div id="main">
		<div id="biaoti">${dep.name}</div>

		<table id=pro class="table table-bordered table-striped table-hover">
			<thead>
				<tr id=xinxi>
					<th>id</th>
					<th>名称</th>
				</tr>
			</thead>
			<tbody>
				<!--遍历list，pro是自己定义的每次循环出来的变量  -->
				<c:forEach items="${list}" var="pro">
					<!-- data-id是为了如果id这一列不显示的话依然存在一个id可以被用来拿数据 -->
					<tr data-id="${pro.id}">
						<td>${pro.id}</td>
						<td>${pro.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<div class="col-sm-4">
				<select class="form-control" id="selectPro">
					<c:forEach items="${noList}" var="pro">
						<option value="${pro.id}">${pro.name}</option>
					</c:forEach>
				</select>
			</div>


			<input type="button" value="增加" id="add" class="btn btn-primary" />
			<input type="button" value="删除" id="delete" class="btn btn-primary" />

		</div>

	</div>
</body>
</html>