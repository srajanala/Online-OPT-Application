<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<style type="text/css">
h1#header{
	text-decoration: none;
  	color: #ffffff;
  	text-align: center;
}
div#mainHeader{
	height:100px;
	width:700px;
	background-color: #36b0b6;
	overflow: hidden;
	margin: 0;
	float:left;
}

</style>
</head>
<body>

<div style="float: left;">
<div id="mainHeader">
			<h1 id="header">Online Opt Application</h1>
		</div>
		
<div>
	<form action="${pageContext.request.contextPath}/spring/CheckLogin" method="post">
		<table>
		<tr>
             <td nowrap  colspan="3"><b>Welcome to the</b></td>
        </tr>
        <tr>
        	<td nowrap  colspan="3"><b>Online Opt Application</b></td></tr>
        
			<tr>
				<td>UserName</td>
				<td>&nbsp;</td>
				<td><input type="text" name="userName" size="20" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td>&nbsp;</td>
				<td><input type="password" name="passWord" /></td>
			</tr>
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
             	<td><input type="submit" value="Sign In"></td>
            </tr> 	
		</table>
	</form>
</div>
</div>
</body>
</html>