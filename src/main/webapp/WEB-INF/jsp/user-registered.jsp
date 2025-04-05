<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Επιτυχής Εισαγωγή</title>
</head>
<body>

	<div>
		<h1>Επιτυχής Εγγραφή</h1>
		<p>Username: ${sessionScope.userInfo.username}</p>
	</div>	
	 	
	<div>
		<a href="${pageContext.request.contextPath}/login">Επιστροφή</a>
	</div> 	
</body>
</html>
