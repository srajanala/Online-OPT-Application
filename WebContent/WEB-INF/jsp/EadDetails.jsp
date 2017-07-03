<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, com.student.EADDetailsList" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EAD Request</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<script type="text/javascript">
function myFunction(s,std_id){
	//alert('mohan'+std_id);
	document.getElementById('applId').value = s;
	document.getElementById('i_94').value = std_id;
	document.forms["applicationForm"].submit();
}

</script>
</head>
<body>
	<%@ include file="hello.jsp" %>
	<h1>EAD Application</h1>
	<form id="applicationForm" method="post" 
	action="${pageContext.request.contextPath}/spring/viewEaddetails" >
	<table id="eadList">
	<input type="hidden" name="applId" id="applId" value="" />
	<input type="hidden" name="i_94" id="i_94" value="" />
	<%List m = (ArrayList)request.getAttribute("eadList");
	for(int i=0;i<m.size();i++){
		EADDetailsList app = (EADDetailsList) m.get(i);
		int appId = app.getApplId();
		String i_94 = app.getI_94();
		%>
			<tr id="eadList">
				
				<td style="border: 1px solid black;"><%=app.getFirstName()%></td>
				<td style="border: 1px solid black;"><%=app.getLastName()%></td>
				<td style="border: 1px solid black;"><%=app.getEmail()%></td>
				<td style="border: 1px solid black;"><%=app.getI_94()%></td>
				<td style="border: 1px solid black;"><a href=
					"${pageContext.request.contextPath}/spring/downLoadEadFile?i_94=<%=i_94%>">EAD Additional Info</a></td>				
				<td style="border: 1px solid black;">
					<input type="submit" value="View" onclick="myFunction('<%=appId%>','<%=i_94%>')"/>
				</td>
		 	</tr>		
	<%}
	%>
	</table>
	</form>
	
</body>
</html>