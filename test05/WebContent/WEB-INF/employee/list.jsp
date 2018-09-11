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
/*#emp{
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
/*考虑到优先级问题，加上emp提高优先级 */
#emp .select {
	background: #337ab7
}
#biaoti{
font-size:20px;
text-align: center;
margin:20px 20px;
font-weight: bold;
}

#emp .td{
width:200px;

}
#emp input{
width:100px;

}
#emp select{
width:100px;
height:30px;
}
#bigPhoto{
display:none;
position:absolute;

}
#bigPhoto img{
width:200px;
height:200px;
}
#emp img{
width:50px;
height:50px;
}

</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var selectId = -1;
		$("#showAdd").click(function() {
			//点击按钮去访问EmployeeServlet里的showAdd方法
			location.href = "employee?type=showAdd";
		})
		$("#showAdd2").click(function() {
			//点击按钮去访问EmployeeServlet里的showAdd方法
			location.href = "employee?type=showAdd2";
		})
		$("#showUpdate").click(function() {
			//如果不选就直接修改，selectId就是-1 
			if (selectId > -1) {
				//点击按钮去访问EmployeeServlet里的showUpdate方法，但是需要把选中的那一行当做参数传过去
				location.href = "employee?type=showUpdate&id=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		$("#delete").click(function() {
			if (selectId > -1) {
				location.href = "employee?type=delete&id=" + selectId;
			} else {
				alert("请选择数据");
			}
		})
		//统一一个方法，谁调它传一个type参数
		function doBatch(type) {
			//选中的行会有一个selectclass，用这个拿到选中的数据
			//#emp .select表示emp table后代中的select类，空格就表示后代，如果用>就表示子元素 
			//在这里table用bootstrap修饰了，它会在table后边加上一个tbody，所以table和select就不是父子关系，是后代 
			//注意这里是JavaScript的代码，所以length要用var，不写也行，不能用int
			var length = $("#emp .select").length;
			//如果不选就直接修改，length就是0 
			if (length > 0) {
				//var ids="";
				//each是对这个选择器里边的元素进行遍历 ,拿出选中的元素的data-id，在用字符串拼接
				//或者可以传数组 ,它跟字符串拼接的格式是一样的，而且不用截取最后一个逗号，别的都不用改
				var ids = new Array();
				$("#emp .select").each(function(index, element) {
					//ids+=$(this).data("id")+",";
					ids.push($(this).data("id"));
				})
				//把ids截一下，让它不显示最后一个元素的逗号，截开头不截结尾 
				//ids=ids.substring(0,ids.length-1);
				location.href = "employee?type=" + type + "&ids=" + ids;
			} else {
				alert("请选择数据");
			}
		}
		//点击批量删除按钮 
		$("#deleteBatch").click(function() {
			doBatch("deleteBatch");
		})
		//点击批量修改1
		$("#showUpdateBatch1").click(function() {
			doBatch("showUpdateBatch1");
		})
		$("#showUpdateBatch2").click(function() {
			doBatch("showUpdateBatch2");
		})
		$("#updateBatch3").click(function() {
			var array=new Array();
			//var emps = "";
			//遍历表单里的元素，取他们的数据 
			//点击的时候用updateEmp类遍历每个点到的tr
			$(".updateEmp").each(function(index, element) {
				
				var id = $(this).data("id");
				var name = $(this).find("[name=name]").val();
				var sex = $(this).find("[name=sex]").val();
				var age = $(this).find("[name=age]").val();
				//把拿到的员工信息字符串拼接起来
				//emps += id + "," + name + "," + sex + "," + age + ";";
				
				
				//两个系统之间传递数据除了可以用拼接字符串以外还可以用xml和json，拼接字符串不符合行业规范，一般都用json
				//json：[{"":"","":"","":""},{"":"","":"","":""}]
				//前边的引号里边写定义的名称，后边写相应的值，json其实就是JavaScript的儿子
				//xml就是自己定义标签，跟html差不多，html是别人定义好的标签
			
				//前边是定义的变量名，后边是值，两个变量间用逗号 ，变量名要跟定义的变量的类型里定义的一致
				var emp={
						id:id,
						name:name,
						sex:sex,
						age:age
									
				}
				//把元素放入数组用push ,注意：JavaScript中数组中的元素可以是不同类型的 
				array.push(emp);
				
			})
			//emps = emps.substring(0, emps.length - 1);
			
			//然后把array转换成一个json,用JSON.stringify()方法 
			var str=JSON.stringify(array);
			//这里把数据传过去是用的get方式，但是url中不能有{}，所以要把它们替换成转义字符，
			//用replace方法，在java跟JavaScript中都有这个方法
			//其中，/{/g在java的方法中就是"{",如果在JavaScript中这么写，就表示只替换第一个元素中的{，下面这么写是替换所有 
			 str=str.replace(/{/g,"%7b");
			 str=str.replace(/}/g,"%7d");
			//发送路径
			window.location.href = "employee?type=updateBatch3&emps=" + str;
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
		//给tr添加一个双击事件，双击变成文本框 
		$("tr").dblclick(function(){
			//点了一次双击以后不能再点双击，解除绑定双击事件,因为第二次双击不能从text中拿值了
			$(this).unbind("dblclick");
			//把单击也解除了 
			$(this).unbind("click");
			//双击的时候给点到的tr也加一个class，便于updateBatch3里遍历的时候拿到这些tr
			$(this).addClass("updateEmp");
			//双击tr，找到它下边的第二个td,就是name属性，这是用结构去找，还可以用find name=，那是精准地找
			var name=$(this).children().eq(1).text();
			//上面那行是拿到name的内容，这行是把name td变成文本框
			$(this).children().eq(1).html("<input type='text' name='name' value='"+name+"'/>");
			
			var sex=$(this).children().eq(2).text();
			//双击后的性别显示什么要进行一个判断 
			var select="";
			if(sex=="男"){
				select="<select name='sex'><option selected value='男'>男</option><option value='女'>女</option></select>"
			}else{
				select="<select name='sex'><option value='男'>男</option><option  selected value='女'>女</option></select>"
			}
			//这里把性别选项设置成下拉框 
			$(this).children().eq(2).html(select);
			
			var age=$(this).children().eq(3).text();
			$(this).children().eq(3).html("<input type='text' name='age' value='"+age+"'/>");
		
			
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
		
		
		$("#emp img").hover(function(event){
			var photo=$(this).attr("src");
			$("#bigPhoto img").attr("src",photo);
			$("#bigPhoto").show();
			//控制放大图片显示的位置
			$("#bigPhoto").css({left:event.pageX,top:event.pageY})
		},function(){
			
			$("#bigPhoto").hide();
			
			
		})
		
		
		
	
		
	   
			
	
	})
</script>
</head>
<body>

	<div id="main">
		<div id="biaoti">员工信息表</div>
		
		
	  <form action="employee" class="form-horizontal" role="form"
			method="post" >
			
			<div class="form-group">
				
				<div class="col-sm-3">
					<input type="text" class="form-control" name="name"
						placeholder="请输入姓名" value=${c.name}>
					</div>
			   <div class="col-sm-2">
					<select name="sex" class="form-control">
					<option value="">性别</option>
					<option value="男" <c:if test="${c.sex eq'男'}">selected</c:if>>男</option>
					<option value="女" <c:if test="${c.sex eq'女'}">selected</c:if>>女</option>
				
					</select>
				</div>
				<div class="col-sm-3">
					<input type="text" class="form-control" name="age"
						placeholder="请输入年龄" value=${c.age!=-1?c.age:''}>
				</div>
				<div class="col-sm-2">
					<select name="depId" class="form-control">
					<option value="">部门</option>
					<c:forEach items="${depList}" var="dep">
					<!--  option中间显示的是给用户看的，value是程序员用的，所以value用id
					if是为了搜索之后下拉框的内容不被重置-->
					<option value="${dep.id }" <c:if test="${dep.id==c.dep.id}">selected</c:if>>${dep.name}</option>
					
				</c:forEach>
					</select>
				</div>
					<div class=" col-sm-2">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>

		
		</form>
		
		
		<table id="emp" class="table table-bordered table-striped table-hover">
			<thead>
				<tr id=xinxi>
					<th>id</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>部门</th>
					<th>照片</th>
				</tr>
			</thead>
			<tbody>
			<!--遍历list，emp是自己定义的每次循环出来的变量  -->
				<c:forEach items="${list}" var="emp">
				<!-- data-id是为了如果id这一列不显示的话依然存在一个id可以被用来拿数据 -->
				<tr  data-id="${emp.id}">
					<td >${emp.id}</td>
					<td >${emp.name}</td>
					<td >${emp.sex}</td>
					<td >${emp.age}</td>
                    <td >${emp.dep.name}</td>
                     <td ><c:if test="${not empty emp.photo}"><img src="pic/${emp.photo}"/></c:if></td>
				</tr>

				</c:forEach>
			</tbody>
		</table>
		
<ul class="pagination">
 <li id="frist"><a href="employee?ye=1&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">首页;</a></li>
    <li id="pre"><a href="employee?ye=${p.ye-1}&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">上一页;</a></li>
    <!--这是传统的for循环，有限次的， beginYe和endYe是显示出来的首尾页 ,status.index就是原来的i -->
    <c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
    <li <c:if test="${p.ye==status.index}">class="active"</c:if>>
    <a href="employee?ye=${status.index}&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}" >${status.index}</a></li>

</c:forEach>
    <li id="next"><a href="employee?ye=${p.ye+1}&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">下一页;</a></li>
 <li id="last"><a href="employee?ye=${p.maxYe}&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">尾页;</a></li>
</ul>

<div>

		<input type="button" value="增加" id="showAdd" class="btn btn-primary" />
		<input type="button" value="修改" id="showUpdate" class="btn btn-primary" /> 
		<input type="button" value="删除" id="delete" class="btn btn-primary" />
	    <input type="button" value="批量修改1" id="showUpdateBatch1" class="btn btn-primary" />
		<input type="button" value="批量修改2" id="showUpdateBatch2" class="btn btn-primary" />
		<input type="button" value="批量修改3" id="updateBatch3" class="btn btn-primary" />
		<input type="button" value="批量删除"  id="deleteBatch" class="btn btn-primary" />
		<input type="button" value="增加2" id="showAdd2" class="btn btn-primary" />
</div>
<div id="bigPhoto"><img src=""/></div>
			
	</div>
</body>
</html>