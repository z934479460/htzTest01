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

#btn {
	width: 120px;
	margin: 20px auto;
}

#add {
	margin-right: 50px
}

.pro {
	background: #337ab7;
	height: 40px;
	line-height: 40px;
	float: left;
	margin-left: 5px;
	color: white;
	padding: 0 20px;
	margin-top: 10px;
	border-radius: 3px;
}
}
</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script type="text/javascript">
	$().ready(function() {
		
    $(".pro").click(function(){
    	$(this).toggleClass("select");
    })
    //获得pro的坐标 
    var proLeft=$("#pro").offset().left;
    var proTop=$("#pro").offset().top;
    //获得pro的长宽 
    var proWidth=parseFloat($("#pro").css("width"));
    var proHeight=parseFloat($("#pro").css("height"));
    //获得noPro的坐标 
    var noProLeft=$("#noPro").offset().left;
    var noProTop=$("#noPro").offset().top;
    //获得noPro的长宽 
    var noProWidth=parseFloat($("#noPro").css("width"));
    var noProHeight=parseFloat($("#noPro").css("height"));
    var startLeft;
    var startTop;
     $(" .pro").draggable({
         start: function() {
      	    startLeft=$(this).offset().left;
    	    startTop=$(this).offset().top;
           },
           stop: function() {
        	   //获得坐标 
        	   var stopLeft=$(this).offset().left;
        	   var stopTop=$(this).offset().top;
        	 if(startLeft>=proLeft&&startLeft<=proLeft+proWidth&&startTop>=proTop&&startTop<=proTop+proHeight&&stopLeft>=proLeft&&stopLeft<=proLeft+proWidth&&stopTop>=proTop&&stopTop<=proTop+proHeight){
         		 // alert("1");
         		   $(this).offset({left:startLeft,top:startTop})
         		    
         		   
         	   }else if(stopLeft>=noProLeft&&stopLeft<=noProLeft+noProWidth&&stopTop>=noProTop&&stopTop<=noProTop+noProHeight&&startLeft>=noProLeft&&startLeft<=noProLeft+noProWidth&&startTop>=noProTop&&startTop<=noProTop+noProHeight){
         		   $(this).offset({left:startLeft,top:startTop})  
         	   }
         	   else if(stopLeft>=proLeft&&stopLeft<=proLeft+proWidth&&stopTop>=proTop&&stopTop<=proTop+proHeight){
        		//拿到当前的pro，成功后直接append它 
        		   var pro=$(this);
        		   var proId=$(this).data("id");
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
        					//因为在设置拖动的时候会自动加上relative position，
        					//所以要把pro设成流布局，append的时候才会自动加到最后
        					pro.css("position","static");
        					$("#pro").append(pro);
        					//最后要再把它设成relative，因为static是不能拖动的 
        					pro.css("position","relative");
        					pro.css("left","0");
        					pro.css("top","0");
        					}
        				}
        			   
        			   
        		   })
        	   }else if(stopLeft>=noProLeft&&stopLeft<=noProLeft+noProWidth&&stopTop>=noProTop&&stopTop<=noProTop+noProHeight){
        		 //拿到当前的pro，成功后直接append它 
        		   var pro=$(this);
        		   var proId=$(this).data("id");
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
        					//因为在设置拖动的时候会自动加上relative position，
        					//所以要把noPro设成流布局，append的时候才会自动加到最后
        					pro.css("position","static");
        					$("#noPro").append(pro);
        					//最后要再把它设成relative，因为static是不能拖动的 
        					pro.css("position","relative");
        					pro.css("left","0");
        					pro.css("top","0");
        					}
        				}
        			   
        			   
        		   })
        		   
        		     
        	   }
        	    
        	   else{  
        		   //设置坐标
        		   $(this).offset({left:startLeft,top:startTop})
        	   }
           }
         });

  
    
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
			<input type="button" value="↑" id="add" class="btn btn-primary" /> <input
				type="button" value="↓" id="delete" class="btn btn-primary" />
		</div>

		<div id="noPro">
			<c:forEach items="${noList}" var="pro">
				<div class="pro" data-id="${pro.id }">${pro.name }</div>
			</c:forEach>
		</div>

	</div>
</body>
</html>