<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Authentification</title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
</head>
<body>

<%@ include file="jspf/headerLogedOut.jspf" %>
<div class='content'>

	<div class='authSection'>
	
		<c:if test="${requestScope.wrong == true}">
			<font color ='red'>Erreur : mauvais couple email/password</font>
			<br/>
		</c:if>
		
		<form action='auth' method='get'>
			<label for='email' class='formlabel'>Email :</label>
			<input type='text' name='email' id='email' value='' class='forminputLarger'/>
			<br/>
			<label for='password'  class='formlabel'>Mot de passe :</label>
			<input type='password' name='password' id='password' value='' class='forminputLarger'/>
			<br/>
			<br/>
			<input type='submit' value='Se connecter'/>
		</form>
		<br/>
		<a href='reset_password' class='link'>Mot de passe oublié</a>
		
	</div>

</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>