


	<div>
	<%String role = (String)request.getAttribute("role"); 
	int id = (Integer) request.getAttribute("id");
	session.setAttribute("id", id);
	session.setAttribute("role", role);
	%>	
		<div id="mainHeader">
			<h1 id="header">Online Opt Application</h1>
		</div>
		<div id='cssmenu'>
			<ul>
		   		<li><a href='${pageContext.request.contextPath}/spring/home'><span>Home</span></a></li>
		   		<%if(role.equals("STUDENT")){ %>
		   		<li><a href='${pageContext.request.contextPath}/spring/Requestopt'><span>RequestOpt</span></a></li>
		   		<li><a href='${pageContext.request.contextPath}/spring/Status'><span>Status</span></a></li>
		   		<li><a href='${pageContext.request.contextPath}/spring/EadRequest'><span>EADRequest</span></a></li>
		   		<%} else if(role.equals("ISSO")){ %>
		   		<li><a href='${pageContext.request.contextPath}/spring/isso'><span>OptApplication</span></a></li>
		   		<%}else if(role.equals("IS")){ %>
		   		<li><a href='${pageContext.request.contextPath}/spring/EadApplication'><span>EADApplication</span></a></li>
		   		<%} %>
		   		<li style="float:right;"><a style="font-size: 10px;" href='${pageContext.request.contextPath}/spring/SignOut'><span>Sign Out</span></a></li>
		   	</ul>
		</div>
	</div>
