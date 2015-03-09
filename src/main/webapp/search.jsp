<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
</head>
<body>

<%@ include file="jspf/headerLogedOn.jspf" %>
<div class='content'>

	<div id='editProfile'>
		<div id='editInfos'>
			<form method="get" action="search">
				<br/>
				<label for='search' class='formlabel'>Prénom, Nom ou surnom :</label>
				<input type='text' name='search' id='search' value='${requestScope.search}'  class='forminput'/>
				<input type='submit' value="Rechercher" class='bigger'>
			</form>		
		</div>
	</div>
	<br/>
	
	<c:choose>
		<c:when test="${not empty requestScope.results}">
			<c:forEach var="member" items="${requestScope.results}">
				<a href='profile?member=${member.id}'>${member.firstName} ${member.lastName} ${not empty member.nickname ? '('.concat(member.nickname).concat(')') : ''}</a>
				<br/>
			</c:forEach>
		</c:when>
		<c:otherwise>
			Il n'y a pas de résultat pour cette recherche.
		</c:otherwise>
	</c:choose>
	
</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>