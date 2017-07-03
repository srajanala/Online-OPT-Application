<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function getFileName() {
		var x = document.getElementById('fileUpload');
		document.getElementById('fileName').value = x.value;
	}
</script>
</head>
<body>
	<form method="post"
		action="${pageContext.request.contextPath}/spring/uploadFile"
		enctype="multipart/form-data">
		File to upload: <input type="file" id="fileUpload" name="file"
			value=""><br /> <a
			href="${pageContext.request.contextPath}/spring/downLoadFile">Click
			here To Download File</a> <input type="submit" value="Upload"
			onclick="getFileName()"> Press here to upload the file!
	</form>
</body>
</html>