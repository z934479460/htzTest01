<%@page import="util.Pagination"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
/*#dep{
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
/*考虑到优先级问题，加上dep提高优先级 */
#dep .select {
	background: #337ab7
}
#biaoti{
font-size:20px;
text-align: center;
margin:20px 20px;
font-weight: bold;
}

#dep .td{
width:200px;

}
#dep input{
width:100px;

}
#dep select{
width:100px;
height:30px;
}

</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var selectId = -1;
		$("#showAdd").click(function() {
			//点击按钮去访问departmentServlet里的showAdd方法
			location.href = "department?type=showAdd";
		})
		$("#showUpdate").click(function() {
			//如果不选就直接修改，selectId就是-1 
			if (selectId > -1) {
				//点击按钮去访问departmentServlet里的showUpdate方法，但是需要把选中的那一行当做参数传过去
				location.href = "department?type=showUpdate&id=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#delete").click(function() {
			if (selectId > -1) {
				location.href = "department?type=delete&id=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#manageProject").click(function() {
			if (selectId > -1) {
				location.href = "dep2pro?depId=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#manageProject2").click(function() {
			if (selectId > -1) {
				location.href = "dep2pro?type=m2&depId=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#manageProject3").click(function() {
			if (selectId > -1) {
				location.href = "dep2pro?type=m3&depId=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#manageProject4").click(function() {
			if (selectId > -1) {
				location.href = "dep2pro?type=m4&depId=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#manageProject5").click(function() {
			if (selectId > -1) {
				location.href = "dep2pro?type=m5&depId=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
	
		
		
		//选中一行数据 ,选中的时候,先把所有行的这个类给移除，再给他加上select类修饰，让它变色,
		$("tr").click(function() {
			//因为要做批量删除，所以不能有removeclass，那是单选，toggleClass就是点的时候没有class就给加上，有就去掉
			//$("tr").removeClass("select");
			$(this).toggleClass("select");
			//然后依靠date-id来拿到选中的这一行的数据
			//selectId=$(this).children().eq(0).text();这是用id那一列来拿数据，如果不显示id的话就拿不了了 
			selectId = $(this).data("id");
			
		})

		
		//让首列不能被点 
		$("#xinxi").unbind("dblclick");
		$("#xinxi").unbind("click");
		
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
		<div id="biaoti">部门信息表</div>
		
		
	  <form action="department" class="form-horizontal" role="form"
			method="post" >
			
			<div class="form-group">
				
				<div class="col-sm-3">
					<input type="text" class="form-control" name="name"
						placeholder="请输入名称" value=${c.name}>
					</div>
				<div class="col-sm-3">
					<input type="text" class="form-control" name="empCount"
						placeholder="请输入人数" value=${c.empCount!=-1?c.empCount:''}>
				</div>
					<div class=" col-sm-3">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>

		
		</form>
		
		
		<table id=dep class="table table-bordered table-striped table-hover">
			<thead>
				<tr id=xinxi>
					<th>id</th>
					<th>名称</th>
					<th>人数</th>
					
				</tr>
			</thead>
			<tbody>
			<!--遍历list，dep是自己定义的每次循环出来的变量  -->
				<c:forEach items="${list}" var="dep">
				<!-- data-id是为了如果id这一列不显示的话依然存在一个id可以被用来拿数据 -->
				<tr  data-id="${dep.id}">
					<td >${dep.id}</td>
					<td >${dep.name}</td>
					<td ><a href="employee?depId=${dep.id}">${dep.empCount}</a></td>

				</tr>

				</c:forEach>
			</tbody>
		</table>
		
<ul class="pagination">

    <li id="pre"><a href="department?ye=${p.ye-1}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">上一页;</a></li>
    <!--这是传统的for循环，有限次的， beginYe和endYe是显示出来的首尾页 ,status.index就是原来的i -->
    <c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
    <li <c:if test="${p.ye==status.index}">class="active"</c:if>>
    <a href="department?ye=${status.index}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}" >${status.index}</a></li>

</c:forEach>
    <li id="next"><a href="department?ye=${p.ye+1}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">下一页;</a></li>
</ul>

<div>

		<input type="button" value="增加" id="showAdd" class="btn btn-primary" />
		<input type="button" value="修改" id="showUpdate" class="btn btn-primary" /> 
		<input type="button" value="删除" id="delete" class="btn btn-primary" />
	    <input type="button" value="项目管理" id="manageProject" class="btn btn-primary" />
        <input type="button" value="项目管理2" id="manageProject2" class="btn btn-primary" />
        <input type="button" value="项目管理3" id="manageProject3" class="btn btn-primary" />
		<input type="button" value="项目管理4" id="manageProject4" class="btn btn-primary" />
		<input type="button" value="项目管理5" id="manageProject5" class="btn btn-primary" />
	   
</div>
			
	</div>
</body>
</html>