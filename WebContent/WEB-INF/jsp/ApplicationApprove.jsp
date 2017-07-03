<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<title>Approved Successfully</title>
</head>
<body>
	<%@ include file="hello.jsp" %>
	<%if(request.getAttribute("formAction").equals("approve")) {%>	
	OPT Request for student ${std_id} approved Successfully.
	<%}else if(request.getAttribute("formAction").equals("declineUpdate")) {%>
		Details updated Successfully and submitted for further approval
	
	<%}else { %>
	OPT Request for student ${std_id} Declined, Submitted for Further Clarification
	<%} %>
</body>
</html>