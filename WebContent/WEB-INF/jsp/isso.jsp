<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, com.student.ApplicationInfo" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ISSO Verification Page</title>
<script type="text/javascript">
function myFunction(s,std_id){
	//alert('mohan'+std_id);
	document.getElementById('applId').value = s;
	document.getElementById('std_id').value = std_id;
	document.forms["applicationForm"].submit();
}

</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
</head>
<body>
	<%@ include file="hello.jsp" %>
	<h1>OPT Application</h1>
	<form id="applicationForm" method="post" 
	action="${pageContext.request.contextPath}/spring/viewapplication" >
	<table id="applList">
	<input type="hidden" name="applId" id="applId" value="" />
	<input type="hidden" name="std_id" id="std_id" value="" />
	<%List m = (ArrayList)request.getAttribute("aplicationList");
	for(int i=0;i<m.size();i++){
		ApplicationInfo app = (ApplicationInfo) m.get(i);
		int appId = app.getAppID();
		int std_id = app.getStudentId();%>
			<tr id="applList">
				<td name="std_id" style="border: 1px solid black;"><%=app.getStudentId()%></td>
				<td style="border: 1px solid black;"><%=app.getFirstName()%></td>
				<td style="border: 1px solid black;"><%=app.getLastName()%></td>
				<td style="border: 1px solid black;"><%=app.getEmail()%></td>
				<td style="border: 1px solid black;"><%=app.getMajor()%></td>
				<td style="border: 1px solid black;"><a href=
					"${pageContext.request.contextPath}/spring/downLoadFile?std_id=<%=app.getStudentId()%>">OPTApplication</a></td>				
				<td style="border: 1px solid black;">
					<input type="submit" value="View" onclick="myFunction('<%=appId%>','<%=std_id%>')"/>
				</td>
			</tr>		
	<%}
	%>
	
	</table>
	</form>
	
</body>
</html>