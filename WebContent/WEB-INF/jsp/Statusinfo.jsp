<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Status Info</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
</head>
<body>
	<%@ include file="hello.jsp" %>
	<h1>Request Status</h1>
	<table>
	<%	List ls = (ArrayList)request.getAttribute("StatInfo"); 
		if(ls.size()>1){ %>
				
				<tr>
					<td>Request Status :</td>
					<td><%=ls.get(1) %></td>
				</tr>
				<tr>
					<td>Pending With :</td>
					<td><%=ls.get(2) %></td>
				</tr>
				<tr>
					<td>request Id :</td>
					<td><%=ls.get(3) %></td>
				</tr>
				<tr>
					<td>Approved Date :</td>
					<td><%=ls.get(0) %></td>
				</tr>
		<%}else {%>
			<tr>
					<td><%=ls.get(0) %></td>
					
				</tr>
		<%}
		%>
	
	</table>
</body>
</html>