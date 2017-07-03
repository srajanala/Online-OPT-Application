<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<title>View EAD Application</title>
<script type="text/javascript">
	function changeFormAction(s){
		//alert(s);
		if(s == 'approve')
			document.getElementById('formAction').value = s;
		else
			document.getElementById('formAction').value = s;
		//alert(document.getElementById('formAction').value);
	}

</script>
</head>
<body>
	<%@ include file="hello.jsp" %>
	<div>
	<form action="${pageContext.request.contextPath}/spring/EadApplApprove" id="actionForm" method="post">
			<input type="hidden"  name="formAction" id="formAction" value=""/>
			<table>
			<tr>
				<td>FirstName :</td>
				<td>${apd.firstName}</td>
			</tr>
			<tr>
				<td>LastName :</td>
				<td>${apd.lastName}</td>
			</tr>
			<tr>
				<td>Passport Number :</td>
				<td>${apd.passPortNum}</td>
			</tr>
			<tr>
				<td>Email :</td>
				<td>${apd.email}</td>
			</tr>
			<tr>
				<td>Address :</td>
				<td>${apd.address}</td>
			</tr>
			<tr>
				<td>Phone Number :</td>
				<td>${apd.phoneNumber}</td>
			</tr>
			<tr>
				<td>I_94 :</td>
				<td>${apd.i_94}</td>
			</tr>
		</table>
		<div>
		<table>
			<tr>
				<td>Comments:</td></br>
			</tr>		
			<tr>
				<td>
				<textarea rows="4" cols="50" name="comments" ></textarea></td></br>
			</tr>
			<tr>
				<td><input type="submit" value="Approve" onclick="changeFormAction('approve')" />
				<input type="submit" value="Decline" onclick="changeFormAction('decline')" /></td>
			</tr>
			</table>
			
			<input type="hidden"  name="applId" value="${applId}"/>
			<input type="hidden"  name="emailId" value="${apd.email}"/>
			
	</form>
	</div>
</body>
</html>