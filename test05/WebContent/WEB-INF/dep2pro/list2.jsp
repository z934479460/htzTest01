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
			var i=0;
			//用val方法拿到proId  
			var proId=$("#selectPro").val();
		//在刚加载出这个页面search的时候就把dep发过来了，点击add按钮直接把depId和proId发过去
			//location.href = "dep2pro?type=add&depId=${dep.id}&proId="+proId;
			//用ajax的方式发送请求
			$.ajax({
				url:"dep2pro", 
				type:"post",
				data:{
					type:"add2",
					depId:"${dep.id}",
					//第二个proId是变量，不用加引号 ，${dep.id}是数字可以不加引号
					proId:proId, 
				},
				dataType:"text",
				success:function(data){
					//如果添加成功了返回来就是true，因为返回的类型是text，所以回来的值就是"true"
					if(data="true"){
						//这是让table里显示刚刚添加的项目 
						var proName="";
						//如果下拉框里的某一元素的val等于proId，就拿到这个元素的name
						//设置index是为了去除下拉框中已添加的数据，用索引去下拉框中找数据 
						$("#selectPro").children().each(function(index,element){
							if($(this).val()==proId){
								proName=$(this).text();
								i=index;
							} 
							
							
						})
						//然后把id跟name拼接成一行，在table里显示出来
						var tr="<tr><td>"+proId+"</td><td>"+proName+"</td></tr>"
						$("#pro").append(tr);
						$("#selectPro").children().eq(i).remove();
						if($("#selectPro").children().length==0){
							$("#add").unbind("click");
							$("#add").addClass("disabled");
						} 
					}
					  
					
					
				}
				
			
				
				
				
			})
			
		})  
		//使用function，当nolist为空时，让add按钮失效  
		//<c:if test="${f:length(noList)==0}"> 
		//$("#add").unbind("click");
		//$("#add").addClass("disabled");
		//</c:if>
	
	
		$("#delete").click(function() {
			if (selectId > -1) {
					var i=0;
					$.ajax({
						url:"dep2pro", 
						type:"post",
						data:{
							type:"delete2",
							depId:"${dep.id}",
							proId:selectId, 
						},
						dataType:"text",
						success:function(data){
							//如果添加成功了返回来就是true，因为返回的类型是text，所以回来的值就是"true"
							if(data="true"){
								//这是让table里显示刚刚添加的项目 
								var proName="";
								//如果选中的这行的data-id等于proId，就拿到这个元素的name
								$("tr").each(function(index,element){
									if($(this).data("id")==selectId){
										proName=$(this).children().eq(1).text();
										i=index;
									} 
									
								
								})
							
								var option="<option value='"+selectId+"'>"+proName+"</option>"
								//在下拉框中添加被删掉的那个项目 
								$("#selectPro").append(option);
								//在table中删除这行
								$("tr").eq(i).remove();
							
						
							}
							  
							
							
						}
						
					
						
						
						
					})
					
				 
			} else {
				alert("请选择数据");
			}
		})
			
			
		//选中一行数据 ,选中的时候,先把所有行的这个类给移除，再给他加上select类修饰，让它变色,
		//这种写法是对整个文档加事件，原来只是对tr加时间，那样新加上的tr就没有这个事件，因为是局部刷新 
		$(document).on("click","tr",function() {
		
			//toggleClass就是点的时候没有class就给加上，有就去掉
			$("tr").removeClass("select");
			$(this).addClass("select");
			//$(this).toggleClass("select");
			//然后依靠date-id来拿到选中的这一行的数据
			//selectId=$(this).children().eq(0).text();
			//这是用id那一列来拿数据，如果不显示id的话就拿不了了 
			selectId = $(this).data("id");
			
		})
		
		
		$("#xinxi").unbind("click");
		
		
	
		
	
	})
</script>
</head>
<body>

	<div id="main">
		<div id="biaoti">${dep.name}</div>

		<table id=pro class="table table-bordered table-striped table-hover">
			<thead>
				<tr id="xinxi" >
					<th >id</th>
					<th >名称</th>
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