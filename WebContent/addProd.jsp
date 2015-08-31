<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.UserVO" %>
    
<% String userId= request.getAttribute("userid")==null?"0":(String)request.getAttribute("userid");
String name= request.getAttribute("name")==null?"":(String)request.getAttribute("name");
Boolean inserted= request.getAttribute("inserted")==null?false:(Boolean)request.getAttribute("inserted");
UserVO user=request.getAttribute("user")==null?new UserVO():(UserVO)request.getAttribute("user");
%>
 <!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add product</title>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" href="js/styles.css">
<link rel="stylesheet" href="js/vzOlx.css">
<script src="js/script.js"></script>
<script src="js/vzOlx.js"></script>
<style type="text/css">
.login{
	background-color: #e0e0e0;
	border-radius: 25px;
}
.title{
	background-color: red;
	color:#FFFFFF;
}
.head{
	font-size:50px;
	color:red;
}
</style>
<script>
var userId = '<%=userId %>';
var name = '<%=name %>';

$(document).ready(function(){
	$("#userid").val(userId);
	$("#name").val(name);
	<% if(inserted){ %>
		alert("Thanks! Product added to the Catalog!");
	<%}%>
});
function clearForm(){
	$('form').trigger('reset');
}
function home(){
	document.getElementById("email").value='<%= user.getEmail()%>';
	document.getElementById("pwd").value='<%= user.getPwd()%>';
	document.getElementById("userid").value=userId;
	document.getElementById("name").value=name;
	document.getElementById("flag").value="login";
	var form = document.getElementById("form");
	form.action="RegisterServlet";
	form.submit();
}
function addProd(){
	document.getElementById("userid").value=userId;
	document.getElementById("name").value=name;
	document.getElementById("type").value="newProd";
	var form = document.getElementById("form");
	form.action = "ProductServlet";
	form.submit();
}
</script>
</head>
<body>
<div>
<table width="100%"><tr><th align="left"><img src="img/VerizonLogo.png"/></th><th width="50%" align="left"><label class="head">Vz Classified</label></th><th width="30%" align="right"><%=name.toUpperCase() %><br><a href="javascript:viewUser();">View Profile</a></th></tr></table>
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
<form name='form' id='form' action="ProductServlet" method="post">
<input type="hidden" name="flag" id="flag" value="login"/>
<input type="hidden" name="type" id="type" value="newProd"/>
<input type="hidden" name="value" id="value"/>
<input type="hidden" name="userid" id="userid"/>
<input type="hidden" name="name" id="name"/>
<input type="hidden" name="email" id="email"/>
<input type="hidden" name="pwd" id="pwd"/>
<span><h2 class="title">Just a brief Details for others to know about your Product...</h2></span>
<div class="login" id="login">
<table align="left" width="100%">
<tr>
<td align="center">
<input type="text" name= "title" placeholder="Title of the Product"></td>
</tr>

<tr>
<td align="center">
<input name= "price" placeholder="Price for the product"></td>
</tr>
<tr>
<td align="center">
 <select name= "Category">
  <option value="">Select Category</option>
  <option value="Electronics">Electronics</option>
  <option value="Vehicles">Vehicles</option>
  <option value="Home & Furniture">Home & Furniture</option>
  <option value="Animals">Animals</option>
  <option value="Books,Sports & Hobbies">Books,Sports & Hobbies</option>
  <option value="Fashion & Beauty">Fashion & Beauty</option>
  <option value="Kids & Baby Products">Kids & Baby Products</option>
  <option value="Services">Services</option>
  <option value="Real Estate">Real Estate</option>
  <option value="Others">Others</option>
</select> </td>
</tr>
<tr>
<td align="center">
<input type="radio" name="reason" value="rent">Rent&nbsp;&nbsp;<input type="radio" name="reason" value="sell">Sell
</td>
</tr>
<tr>
<td align="center">
<textarea name= "desc" placeholder="Description"></textarea></td>
</tr>

<tr><td align="center"><input class="button" type="button" value="Submit" onClick="javascript:addProd();">&nbsp;&nbsp;
<input class="button" type="button" value="Clear" onClick='javascript:clearForm();'></td></tr>
</table>

</div>
</form>
</body>
</html>
 
 

