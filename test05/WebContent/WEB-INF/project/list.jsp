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
#biaoti{
font-size:20px;
text-align: center;
margin:20px 20px;
font-weight: bold;
}

#pro .td{
width:200px;

}
#pro input{
width:100px;

}
#pro select{
width:100px;
height:30px;
}

</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var selectId = -1;
		$("#showAdd").click(function() {
			//点击按钮去访问projectServlet里的showAdd方法
			location.href = "project?type=showAdd";
		})
		$("#showUpdate").click(function() {
			//如果不选就直接修改，selectId就是-1 
			if (selectId > -1) {
				//点击按钮去访问projectServlet里的showUpdate方法，但是需要把选中的那一行当做参数传过去
				location.href = "project?type=showUpdate&id=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#delete").click(function() {
			if (selectId > -1) {
				location.href = "project?type=delete&id=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		//统一一个方法，谁调它传一个type参数
		function doBatch(type) {
			//选中的行会有一个selectclass，用这个拿到选中的数据
			//#pro .select表示pro table后代中的select类，空格就表示后代，如果用>就表示子元素 
			//在这里table用bootstrap修饰了，它会在table后边加上一个tbody，所以table和select就不是父子关系，是后代 
			//注意这里是JavaScript的代码，所以length要用var，不写也行，不能用int
			var length = $("#pro .select").length;
			//如果不选就直接修改，length就是0 
			if (length > 0) {
				//var ids="";
				//each是对这个选择器里边的元素进行遍历 ,拿出选中的元素的data-id，在用字符串拼接
				//或者可以传数组 ,它跟字符串拼接的格式是一样的，而且不用截取最后一个逗号，别的都不用改
				var ids = new Array();
				$("#pro .select").each(function(index, element) {
					//ids+=$(this).data("id")+",";
					ids.push($(this).data("id"));
				})
				//把ids截一下，让它不显示最后一个元素的逗号，截开头不截结尾 
				//ids=ids.substring(0,ids.length-1);
				location.href = "project?type=" + type + "&ids=" + ids;
			} else {
				alert("请选择数据");
			}
		}
	
	
	
		
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
		<div id="biaoti">项目信息表</div>
		
		
	  <form action="project" class="form-horizontal" role="form"
			method="post" >
			
			<div class="form-group">
				
				<div class="col-sm-3">
					<input type="text" class="form-control" name="name"
						placeholder="请输入名称" value=${c.name}>
					</div>
				
					<div class=" col-sm-3">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>

		
		</form>
		
		
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
				<tr  data-id="${pro.id}">
					<td >${pro.id}</td>
					<td >${pro.name}</td>
				

				</tr>

				</c:forEach>
			</tbody>
		</table>
		
<ul class="pagination">

    <li id="pre"><a href="project?ye=${p.ye-1}&name=${c.name}">上一页;</a></li>
    <!--这是传统的for循环，有限次的， beginYe和endYe是显示出来的首尾页 ,status.index就是原来的i -->
    <c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
    <li <c:if test="${p.ye==status.index}">class="active"</c:if>>
    <a href="project?ye=${status.index}&name=${c.name}" >${status.index}</a></li>

</c:forEach>
    <li id="next"><a href="project?ye=${p.ye+1}&name=${c.name}">下一页;</a></li>
</ul>

<div>

		<input type="button" value="增加" id="showAdd" class="btn btn-primary" />
		<input type="button" value="修改" id="showUpdate" class="btn btn-primary" /> 
		<input type="button" value="删除" id="delete" class="btn btn-primary" />
	

		
</div>
			
	</div>
</body>
</html>