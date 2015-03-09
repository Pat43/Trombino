<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
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
	
	<div id='editInfos'>
	
		<form action='register' method='get'>
		
			<b>Infos persos :</b>
			<br/>
			<label for='firstName' class='formlabel'>Prénom * :</label>
			<input type='text' name='firstName' id='firstName' value='${requestScope.firstName}'  class='forminput'/>
			<br/>
			<label for='lastName' class='formlabel'>Nom * :</label>
			<input type='text' name='lastName' id='lastName' value='${requestScope.lastName}'  class='forminput'/>
			<br/>
			<label for='nickname' class='formlabel'>Surnom :</label>
			<input type='text' name='nickname' id='nickname' value='${requestScope.nickname}'  class='forminput'/>
			<br/>
			<br/>
			<b>Compte Trombino :</b>
			<br/>
			<label for='email' class='formlabel'>Email * :</label> 
			<input type='text' name='email' id='email' value='${requestScope.email}'  class='forminput'/>
			<br/>
			<input type='checkbox' name='email_hide' id="email_hide" value='ok' ${requestScope.email_hide == 'ok' ? 'checked' : ''}/>
			<label for='email_hide'>Cacher mon email</label>
			<br/>
			<br/>
			<label for='password'  class='formlabel'>Mot de passe * :</label>
			<input type='password' name='password' id='password' value='' class='forminput'/>
			<br/>
			<label for='password2'  class='formlabel'>Vérification * :</label>
			<input type='password' name='password2' id='password2' value='' class='forminput'/>
			<br/>
			<br/>
			<input type='checkbox' name='condition' id='condition' value='ok'/>
			<label for='condition'><b>Je certifie sur l'honneur être en Promo 11 (ou un promo 10 ou 9 invité d'honneur dans le trombino de la 11).</b></label>
			<br/>
			<br/>
			<input type='submit' value="S'inscrire" class='bigger'>
		</form>
		<i>Tu pourras compléter ton profil après avoir confirmé ton inscription.</i>
		
	</div>

</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>