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

#main {
	width: 600px;
	margin: 20px auto;
}

#pro, #noPro {
	width: 700px;
	height: 200px;
	border: 1px solid #337ab7;
	border-radius: 3px;

}
#btn{
width:120px;
margin:20px auto;
}
#add{
margin-right:50px
}
.pro{
background:#337ab7;
height:40px;
line-height:40px;
float:left;
margin-left:5px;
color:white;
padding:0 20px;
margin-top:10px;
border-radius: 3px;
}
.select{
background:#d9534f;

}
</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		
    $(".pro").click(function(){
    	$(this).toggleClass("select");
    })
    
    $("#add").click(function(){
    
    	//判断选中的pro是不是下边div里的 
    	if($("#noPro").find(".select").length>0){
    		var proId=$("#noPro").find(".select").data("id");
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
					if(data=="true"){
						var pro=$("#noPro").find(".select");
						pro.removeClass("select");
						$("#pro").append(pro);
					}
				}
    				
    		})
    		
    	}else{
    		alert("请选择数据"); 
    	}
    	
    	
    })
    
     
    $("#delete").click(function(){
    
    	//判断选中的pro是不是上边div里的 
    	if($("#pro").find(".select").length>0){
    		var proId=$("#pro").find(".select").data("id");
    		$.ajax({
    			url:"dep2pro", 
				type:"post",
				data:{
					type:"delete2",
					depId:"${dep.id}",
					//第二个proId是变量，不用加引号 ，${dep.id}是数字可以不加引号
					proId:proId, 
				},
				dataType:"text",
				success:function(data){
					if(data=="true"){
						var pro=$("#pro").find(".select");
						pro.removeClass("select");
						$("#noPro").append(pro);
					}
				}
    				
    		})
    		
    	}else{
    		alert("请选择数据"); 
    	}
    	
    	
    })
    
    
	})
</script>
</head>
<body>

	<div id="main">
		<div id="biaoti">${dep.name}</div>
		<div id="pro">
		<c:forEach items="${list }" var="pro">
		<div class="pro" data-id="${pro.id }">${pro.name }</div>
		</c:forEach>
		</div>
		
		<div id="btn">
			<input type="button" value="↑" id="add" class="btn btn-primary" />
			<input type="button" value="↓" id="delete" class="btn btn-primary" />
		</div>
		
		<div id="noPro">
	    <c:forEach items="${noList}" var="pro">
		<div class="pro" data-id="${pro.id }">${pro.name }</div>
	    </c:forEach>
		</div>

	</div>
</body>
</html>