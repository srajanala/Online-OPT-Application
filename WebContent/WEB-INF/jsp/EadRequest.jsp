<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EAD Request</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<script type="text/javascript">
	function getFileName(){
		//alert('Mohan');
	var x = document.getElementById('file');
	document.getElementById('fileName').value=x.value;
	
	}
</script>

</head>
<body>
<div>
<%@ include file="hello.jsp" %>
<div>
<form name="optAppl" action="${pageContext.request.contextPath}/spring/EadSubmit" method="post" 
		enctype="multipart/form-data">
		<table>
		<tr>
			<td>	
				<label>First Name :</label>
			</td>
			<td>	
				<input type="text" name="firstName"/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label>Last Name :</label>
			</td>
			<td>	
				<input type="text" name="lastName"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label>Passport Number :</label>
			</td>
			<td>	
				<input type="text" name="passPortNum"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label>Email :</label>
			</td>
			<td>	
				<input type="text" name="email"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label> Address :</label>
			</td>
			<td>	
				<input type="text" name="address"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label> Phone Number : </label>
			</td>
			<td>	
				<input type="text" name="phoneNumber" value=""/>
			</td>
		</tr>
		<tr>
			<td>	
				<label> I 94 Number : </label>
			</td>
			<td>	
				<input type="text" name="i_94" value=""/>
			</td>
		</tr>
		<tr>
			<td>
				File to upload: <input type="file" id="file" name="file">
				<input type="hidden" id="fileName" id="fileName" name="fileName">
			</td>						
		</tr>
		<tr>
			<td>
				<input type="submit" value ="Submit" onclick="getFileName()" />
				<input type="submit" value ="Reset"/>
			</td>
		</tr>
		</table>
</form>
</div>
</div>		
</body>
</html>