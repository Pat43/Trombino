<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trombino</title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
</head>
<body>

<%@ include file="jspf/headerLogedOn.jspf" %>
<div class='content'>

	<div id='hometitle'>TROMBINO PROMO 11</div>
	
	<c:if test="${not empty requestScope.noCommentsList}">
		<i>Ces membres n'ont pas encore ton commentaire :</i>
		<br/>
		<br/>
		<c:forEach var="member" items="${requestScope.noCommentsList}">
			<a href='profile?member=${member.id}'>${member.firstName} ${member.lastName} ${not empty member.nickname ? '('.concat(member.nickname).concat(')') : ''}</a>	
			<br/>
		</c:forEach>
	</c:if>
	
</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>