<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/style.css'>
</head>
<body>

<c:choose>
	<c:when test="${sessionScope.user != null}">
		<%@ include file="jspf/headerLogedOn.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="jspf/headerLogedOut.jspf" %>
	</c:otherwise>
</c:choose>

<div class='content'>

Désolé, une erreur est survenue.

</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>