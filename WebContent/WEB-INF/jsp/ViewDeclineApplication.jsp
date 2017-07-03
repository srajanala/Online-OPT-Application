<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decline Application</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
</head>
<body>
	<%@ include file="hello.jsp" %>
	<div>
	<form action="${pageContext.request.contextPath}/spring/UpdateDeclineApplication" id="actionForm" method="post">
			<input type="hidden"  name="formAction" id="formAction" value=""/>
		
	<h1>OPT Application Of Student ${std.student_id} </h1>
		<table>
			<tr>
				<td>FirstName :</td>
				<td>${std.firstName}</td>
			</tr>
			<tr>
				<td>LastName :</td>
				<td>${std.lastName}</td>
			</tr>
			<tr>
				<td>StudentID :</td>
				<td>${std.student_id}</td>
			</tr>
			<tr>
				<td>Email :</td>
				<td>${std.email}</td>
			</tr>
			<tr>
				<td>Address :</td>
				<td>${std.address}</td>
			</tr>
			<tr>
				<td>City :</td>
				<td>${std.city}</td>
			</tr>
			<tr>
				<td>State :</td>
				<td>${std.state}</td>
			</tr>
			<tr>
				<td>ZipCode :</td>
				<td>${std.zipcode}</td>
			</tr>
			<tr>
				<td>PhoneNumber :</td>
				<td>${std.phoneNumber}</td>
			</tr>
			<tr>
				<td>DateOfBirth :</td>
				<td>${std.dateOfBirth}</td>
			</tr>
			<tr>
				<td>Major :</td>
				<td>${std.major}</td>
			</tr>
			<tr>
				<td>I_94 :</td>
				<td><input type="text" name="i_94" value= "${apd.i_94}" /></td>
			</tr>
			<tr>
				<td>FirstEntered :</td>
				<td><input type="date" name="firstEntered" value= "${apd.firstEntered}"/></td>
			</tr>
			<tr>
				<td>LastEntered :</td>
				<td><input type="date" name="lastEntered" value= "${apd.lastEntered}"/></td>
			</tr>
			<tr>
				<td>Level of Education Being Sought :</td>
				<td><input type="text" name="level_of_education" value= "${apd.level_of_education}"/></td>
			</tr>
			<tr>
				<td>Desired beginning date of employment :</td>
				<td><input type="date" name="empAfterEdu" value= "${apd.empAfterEdu}"/></td>
			</tr>
			<tr>
				<td>Previous CPT Periods  :</td>
				<td><input type="date" name="preCptFrom" value= "${apd.preCptFrom}"/></td>
				<td><input type="date" name="preCptTo" value= "${apd.preCptTo}"/></td>	
			</tr>
			<tr>
				<td>Previous OPT Periods  :</td>
				<td><input type="date" name="preOptFrom" value= "${apd.preOptFrom}"/></td>
				<td><input type="date" name="preOptTo" value= "${apd.preOptTo}"/></td>	
			</tr>
			<tr>
				<td>Expected date of graduation :</td>
				<td><input type="date" name="exptDateGrad" value= "${apd.exptDateGrad}"/></td>
			</tr>
		</table>
		<div>
		<table>
			<tr>
				<td>Comments:</td></br>
			</tr>		
			<tr>
				<td>
				<label   name="comments">${apd.comments}</label></td></br>
			</tr>
			<tr>
				<td><input type="submit" value="Update" />				
			</tr>
			</table>
		</div>	
			<input type="hidden"  name="std_id" value="${std.student_id}"/>
			<input type="hidden"  name="applId" value="${applId}"/>
			
		</form>
	</div>
	
	
</body>
</html>