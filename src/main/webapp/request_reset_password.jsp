<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirmation</title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
</head>
<body>

<%@ include file="jspf/headerLogedOut.jspf" %>
<div class='content'>


	<c:if test="${requestScope.error == true}">
		<font color='red'>Aucun utilisateur n'est associé à cet email.</font>
	</c:if>
				
	<form method ="get" action ="reset_password">
		<label for='email'  class='formlabel'>Entrez votre email :</label>
		<input type='text' name='email' id='email' value="${requestScope.email}" class='forminput'>
		<input type='submit' value='Envoyer'/>
	</form>

</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>