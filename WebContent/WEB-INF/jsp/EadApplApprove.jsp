<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EAD Application Approved</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
</head>
<body>
	<%@ include file="hello.jsp" %>
	<%if(request.getAttribute("formAction").equals("approve")) {%>
	<p> ${msg}, Mail has sent Successfully to provided mail ID ${mailId}</p>
	<%}else { %>
	<p>${msg}, Mail has sent Successfully to provided mail ID ${mailId}</p>
	<%} %>
</body>
</html>