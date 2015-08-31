<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ProductVO"%>
<%@ page import="com.UserVO" %>

<%
String userId= request.getAttribute("userid")==null?"":(String)request.getAttribute("userid");
String name= request.getAttribute("name")==null?"":(String)request.getAttribute("name");
String type= request.getAttribute("type")==null?"":(String)request.getAttribute("type");
String value= request.getAttribute("value")==null?"":(String)request.getAttribute("value");
List<ProductVO> results = request.getAttribute("results")==null?new ArrayList<ProductVO>():(List<ProductVO>)request.getAttribute("results");
UserVO user=request.getAttribute("user")==null?new UserVO():(UserVO)request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<title>Vz Employee Products</title>
<link rel="stylesheet" href="js/styles.css">
<link rel="stylesheet" href="js/vzOlx.css">
<script src="js/script.js"></script>
<script src="js/vzOlx.js"></script>
<style type="text/css">
.title{
	background-color: red;
	color:#FFFFFF;
}
.head{
	font-size:50px;
	color:red;
}
#detDiv{
	width:100%;
}

td.det{
	background-color:#DCDCDC;
}

.button{
	background-color:#CC0000;
	color:white;
	width:100%;
}
</style>
<script>
var userId = '<%=userId%>';
var name = '<%=name%>';

document.getElementById("userid").value=userId;
document.getElementById("name").value=name;
/*$(document).ready(function(){
	$('.addDet').hide();
	$('.prod').on("click",function(){
		alert("Click");
		var id=(this).attr("id");
		id="#addDet"+id;
		$('.addDet').hide();
		$(id).show();
	});
});*/

function viewUser(){
	$('.profile').menu({
		items:".menu"
	})
}
function contactSeller1(productId){
	$.ajax({
		url: "ProductServlet/doPost.do",
		type: "POST",
		data: {productId: productId, userId: userId, flag:"email"},
		success:function(data, textStatus, jqXHR){
			alert("data"+data);
		},
		error:function(jqXHR,textStatus,errorThrown){
			alert("error"+errorThrown)
		}
	})
}
function contactSeller2(productId){
$.get('ProductServlet',{productId: productId, userId: userId, flag:"email"},function(responseText) { 
	alert(responseText);
    $('#welcometext').text(responseText);         
});
}
function contactSeller(productId){
	var message = prompt("Please enter your Customized Message (if any)", "Hello, I'm intersted to buy/rent your Product.");
	message = message.replace(/&/g,"and");
	message = message.replace(/'/g," ");
	var dataString = "productid=" + productId+"&name=" + name+"&userid=" + userId+"&message="+message+"&flag=email";
	$.ajax({
	    type: "POST",
	    url: "ProductServlet",
	    data: dataString,
	    dataType: "text",
	    
	    //if received a response from the server
	    success: function( data, textStatus, jqXHR) {
	        alert(data);
	    },
	    
	    //If there was no resonse from the server
	    error: function(jqXHR, textStatus, errorThrown){
	         //capture Error
	    }
	});
}
function home(){
	document.getElementById("email").value='<%= user.getEmail()%>';
	document.getElementById("pwd").value='<%= user.getPwd()%>';
	var form = document.getElementById("form");
	form.action="RegisterServlet";
	form.submit();
}
</script>
</head>
<body>
<div>
<table><tr><th><img src="img/VerizonLogo.png"/></th><th>&nbsp;&nbsp;&nbsp;&nbsp;<label class="head">Vz Classified</label></th></tr></table>
<hr>
</div>
<div id="detHdr">
<table width="100%">
<tr>
<td align="left"><a class="mainLink" href="javascript:home();">Home</a>&nbsp;&nbsp;<a class="mainLink" href="javascript:addProducts();">Add New Products</a></td><td align="right"><span class="uName"><%=name.toUpperCase() %></span></td></tr>
<tr>
<td width="50%">
<input id="searchCrit" placeholder="Enter Product to Search" type="text"/><img width="25" height="25" src="img/search.png" onClick="javascript:prodDetails('search',$('#searchCrit').val());"></td>
<td align="right">
<div id='cssmenu'>
<ul>
   <li ><a href='#'>View Profile</a>
      <ul>
         <li><a href='#'><%=user.getName() %></a>
         </li>
         <li><a href='#'><%= user.getContact()%></a>
         </li>
         <li><a href='#'><%= user.getEmail()%></a>
         </li>
         <li><a href='javascript:login();'>SignOut </a>
         </li>
         
      </ul>
   </li>
</ul>
</div>
</td></tr>
</table>
<hr>
</div>
<form id="form" action="RegisterServlet" method="post">
<input type="hidden" name="flag" value="login"/>
<input type="hidden" name="type" id="type"/>
<input type="hidden" name="value" id="value"/>
<input type="hidden" name="userid" id="userid"/>
<input type="hidden" name="name" id="name"/>
<input type="hidden" name="email" id="email"/>
<input type="hidden" name="pwd" id="pwd"/>
</form>
<div id="head">
<%if(type.equalsIgnoreCase("mostviewed")){ %>
<span><h2 class="title"> Most Popular Products</h2></span>
<% }else if(type.equalsIgnoreCase("recent")){ %>
<span><h2 class="title"> Recently Added Products</h2></span>
<% }else if(type.equalsIgnoreCase("category")){ %>
<span><h2 class="title"> <%= value %> Category Products</h2></span>
<% }else{ %>
<span><h2 class="title"> Products matching Search Criteria: <%= value %></h2></span>
<%} %>
</div>
<div>
<% for(int i=0; i<results.size(); i++){%>
	<table><tr><td width="20%" align="right"><img width="200" height="100" src='<%=results.get(i).getImageLink()%>'></td><td width="70%"><div class="detDiv" id="detDiv"+'<%=i%>'>
	<div class="prod" id='<%=i%>'>
	<table width="100%" align="center"><tr><td rowspan="2" width="15%" align="center"><img src=""></td><td class="det" rowspan="2" align="center" width="50%"><%=results.get(i).getName() %></td><td rowspan="2" class="det" width="30%" align="center"><%=results.get(i).getCategory() %></td></tr></table>
	</div>
	<div class="addDet" id="addDet"+'<%=i%>'>
	<table width="100%" align="center"><tr><td rowspan="2" width="15%" align="center"></td><td class="det" rowspan="2" align="center" width="50%"><%=results.get(i).getDescription() %></td><td class="det" width="30%" align="center">For <%= results.get(i).getReason() %> @ Rs.<%=results.get(i).getPrice() %></td></tr>
	<tr><td width="30%" align="center"><input class="button" type="button" value="Contact Seller" onClick="javascript:contactSeller('<%= results.get(i).getProductId() %>');"/></td></tr></table>
	
	</div>
	</div>
	</td></tr></table>
	<br>

<%} %>
</div>
</body>
</html>