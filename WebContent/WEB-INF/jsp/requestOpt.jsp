<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Opt Application</title>
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
<form name="optAppl" action="${pageContext.request.contextPath}/spring/submit" method="post" 
		enctype="multipart/form-data">
	<input type="hidden" name="student_id" value="${std.student_id}"/>
<h1>Student Personal Info</h1>
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
		
	</table>
<div>
	<table>
		<tr>
			<td>	
				<label>I-94 Number :</label>
			</td>
			<td>	
				<input type="text" name="i_94"/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label>First Entered US or Became F-1 :</label>
			</td>
			<td>	
				<input type="date" name="firstEntered"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label>Last Entered US :</label>
			</td>
			<td>	
				<input type="date" name="lastEntered"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label>Level of education being sought :</label>
			</td>
			<td>	
				<input type="text" name="level_of_education"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label> Date of employment after graduation :</label>
			</td>
			<td>	
				<input type="date" name="empAfterEdu"/><br/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label> Previously approved CPT From:</label>
			</td>
			<td>	
				<input type="date" name="preCptFrom" value=""/>
			</td>
			<td>	
				<input type="date" name="preCptTo" value=""/>
			</td>		
		</tr>
		<tr>
			<td>	
				<label> Previously approved OPT From:</label>
			</td>
			<td>	
				<input type="date" name="preOptFrom" value=""/>				
			</td>
			<td>	
				<input type="date" name="preOptTo" value=""/>				
			</td>		
		</tr>
		<tr>
			<td>	
				<label> Date of employment after graduation :</label>
			</td>
			<td>	
				<input type="date" name="exptDateGrad"/><br/>
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
</div>	
	
	</form>
</div>
</div>
</body>
</html>
