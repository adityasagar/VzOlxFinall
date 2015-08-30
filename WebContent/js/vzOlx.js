/**
 * 
 */
function login(){
	window.location.replace("index.jsp");
}
function addProducts(){
	/*document.getElementById("det").style.display="none";
	document.getElementById("login").style.display="none";
	document.getElementById("addProd").style.display="block";*/
	//alert(userId);
	document.getElementById("type").value="addProj";
	var form = document.getElementById("form");
	form.action="ProductServlet";
	form.submit();
}
function prodDetails(type,value){
	//alert("type-"+type+", value-"+value);
	document.getElementById("type").value=type;
	document.getElementById("value").value=value;
	var form = document.getElementById("form");
	form.action="ProductServlet";
	form.submit();
}
