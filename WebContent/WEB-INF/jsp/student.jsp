<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, com.student.ApplicationInfo" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<title>OnlineOpt Application</title>
<script type="text/javascript">
function myFunction(s,std_id){
	//alert('mohan'+std_id);
	document.getElementById('applId').value = s;
	document.getElementById('std_id').value = std_id;
	document.forms["applicationForm"].submit();
}

</script>
</head>
<body>
<div>
<%@ include file="hello.jsp" %>

<div>

<h1>Personal Info</h1>
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
			<td>ID :</td>
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
		<%if(role.equals("STUDENT")){ %>
		<tr>
			<td>Major :</td>
			<td>${std.major}</td>
		</tr>
		<%} %>
	</table>
	<%List m = (ArrayList)request.getAttribute("aplicationList"); 
	
	if(m.size()>0){%>
		
		<h1>Decline Application Details: </h1>	
		<form id="applicationForm" method="post" 
	action="${pageContext.request.contextPath}/spring/ViewDeclineApplication" >
	<table id="applList">
	<input type="hidden" name="applId" id="applId" value="" />
	<input type="hidden" name="std_id" id="std_id" value="" />
	
	
	<% for(int i=0;i<m.size();i++){
		ApplicationInfo app = (ApplicationInfo) m.get(i);
		int appId = app.getAppID();
		int std_id = app.getStudentId();%>
			<tr id="applList">
				<td name="std_id" style="border: 1px solid black;"><%=app.getStudentId()%></td>
				<td style="border: 1px solid black;"><%=app.getFirstName()%></td>
				<td style="border: 1px solid black;"><%=app.getLastName()%></td>
				<td style="border: 1px solid black;"><%=app.getEmail()%></td>
				<td style="border: 1px solid black;"><%=app.getMajor()%></td>
				<td style="border: 1px solid black;">
					<input type="submit" value="View" onclick="myFunction('<%=appId%>','<%=std_id%>')"/>
				</td>
			</tr>		
	<%}
	%>
	
	</table>
	</form>
	
		
	<%}
	
	%>
	
	
</div>
</div>
</body>
</html>