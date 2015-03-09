<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
</head>
<body>

<%@ include file="jspf/headerLogedOut.jspf" %>
<div class='content'>

	<c:if test="${not empty requestScope.errors}">
		<p>
		<c:forEach var="error" items="${requestScope.errors}" >
			<font color='red'>${error}</font>
			<br/>
		</c:forEach>
		</p>
	</c:if>
				
	<form method ="get" action ="email">
		<input type='hidden' name='resetpassword' value="${param.resetpassword}">
		<label for='password'  class='formlabel'>Nouveau mot de passe :</label>
		<input type='password' name='password' id='password' value="" class='forminput'>
		<label for='password2'  class='formlabel'>Confirmation :</label>
		<input type='password' name='password2' id='password2' value="" class='forminput'>
		<input type='submit' value='Envoyer'/>
	</form>

</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>