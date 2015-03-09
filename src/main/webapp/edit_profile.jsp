<%@page import="constants.Constants"%>
<%@page import="constants.Lists"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Profile</title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
<script type="text/javascript" src="js/js.js"></script>
</head>
<body>

<%@ include file="jspf/headerLogedOn.jspf" %>
<div class='content'>

	<c:if test="${not empty requestScope.errors}">
		<p>
		<c:forEach var="error" items="${requestScope.errors}" >
			<font color='red'>${error}</font>
			<br/>
		</c:forEach>
		</p>
	</c:if>

<b>Editer les photos :</b>

<div class='editProfileSection'>

	<c:if test="${requestScope.photoPath != null}">
		<div class='editPhoto'>
			<b>Photo:</b>
			<br/>
			<img src='${requestScope.photoPath}' alt='photo'>
			<br/>
		
			<form action='edit_profile' method='get'>
				<input type='hidden' name='deleteImg' value="deleteImg">
				<input type='submit' value="Supprimer la photo">
			</form>
		</div>
	</c:if>
	
	<c:if test="${requestScope.blousePath != null}">
		<div class='editPhoto'>
			<b>Blouse:</b>
			<br/>
			<img src='${requestScope.blousePath}' alt='blouse'>
			<br/>
			<form action='edit_profile' method='get'>
				<input type='hidden' name='deleteBlouse' value="deleteBlouse">
				<input type='submit' value="Supprimer la blouse">
			</form>
		</div>
	</c:if>
	
	<br/>
	<br/>
	
	<form action='edit_profile' method='post' enctype='multipart/form-data'>
	<c:choose>
		<c:when test="${requestScope.photoPath != null}">
			Changer de photo :
		</c:when>
		<c:otherwise>
			Ajouter une photo :
		</c:otherwise>
	</c:choose>
	<input type='file' name='newImg'>
	<br/>
	<c:choose>
		<c:when test="${requestScope.blousePath != null}">
			Changer de blouse :
		</c:when>
		<c:otherwise>
			Ajouter une blouse :
		</c:otherwise>
	</c:choose>
	<input type='file' name='newBlouse'>
	<br/>
	<br/>
	<input type='submit' value="Envoyer" class='bigger'>
	</form>
</div>
<br/>
<br/>

<b>Editer les informations du compte :</b>

<div class='editProfileSection'>
	<form action='edit_profile' method='get'>

			<input type='hidden' name='htmlFormName' value='infos'/>
			<br/>
			
			<label for='firstName' class='formlabel'>Prénom :</label> <input type='text' id='firstName' value="${sessionScope.user.firstName}" disabled class='forminput'>
			<br/>
			<label for='lastName' class='formlabel'>Nom :</label> <input type='text' id='lastName' value="${sessionScope.user.lastName}" disabled class='forminput'>
			<br/>
			<label for='nickname' class='formlabel'>Surnom :</label>
			<input type='text' name='nickname' id='nickname' value="${requestScope.nickname}"  class='forminput'>
			<br/>
			<br/>
			<label for='phone' class='formlabel'>N° de téléphone :</label>
			<input type='text' name='phone' id='phone' value="${requestScope.phone}"  class='forminput'>
			<br/>
			<br/>
			<label for="day" class='formlabel'>Date de naissance :</label>
			<span class='forminput'>
				<select name='day' id='day'>
					<option value=''></option>
					<c:forEach  var="i" begin="1" end="31">
						<option value='${i}' ${i == requestScope.day ? 'selected="selected"' : ''}>${i}</option>
					</c:forEach>
				</select>
			
				<% request.setAttribute("monthes", Lists.monthes); %>
				<select name='month' id='month'>
					<c:forEach  var="month" items="${requestScope.monthes}" varStatus="status">
						<option value='${status.index}' ${status.index == requestScope.month ? 'selected="selected"' : ''}>${month}</option>
					</c:forEach>
				</select>
			
				<% request.setAttribute("calBegin", Constants.CALENDAR_REFERENCE_YEAR - Constants.CALENDAR_DOWN_OFFSET); %>
				<% request.setAttribute("calEnd", Constants.CALENDAR_REFERENCE_YEAR + Constants.CALENDAR_UP_OFFSET); %>
	
				<select name='year' id='year'>
					<option value=''></option>
					<c:forEach  var="i" begin="${requestScope.calBegin}" end="${requestScope.calEnd}">
						<option value='${i}' ${i == requestScope.year ? 'selected="selected"' : ''}>${i}</option>
					</c:forEach>
				</select>
			</span>
			<br/>
			<br/>
	
			<label for='phrase' class='formlabel'>Phrase perso :</label>
			<input type='text' name='phrase' id='phrase' value="${requestScope.phrase}" class='forminput' size='100'>
			<br/>
			<br/>
			<b><i>UTBM :</i></b>
			<br/>
			
			<% request.setAttribute("depts", Lists.departments); %>
			<label for='department'>Branche :</label>
			<select name='department' id='department' onchange="refreshFilieres();">
				<option value=''></option>
				<c:forEach  var="dept" items="${requestScope.depts}">
					<option value='${dept}'  ${dept.equals(requestScope.department) ? 'selected="selected"' : ''} >${dept}</option>
				</c:forEach>
			</select>
			<br/>
			<% int deptIndex = Lists.departments.indexOf(request.getAttribute("department"));
			request.setAttribute("index", deptIndex);
				if(deptIndex!=-1)
					request.setAttribute("filieres", Lists.filieres.get(deptIndex));
			%>
			<label for='filiere'>Filière :</label>
			<select name='filiere' id='filiere'>
				<option value=''></option>
				<c:forEach  var="filiere" items="${requestScope.filieres}">
					<option value='${filiere}' ${filiere.equals(requestScope.filiere) ? 'selected="selected"' : ''}>${filiere}</option>
				</c:forEach>
			</select>
			<br/>
			<label for='filiere2'>2nde Filière :</label>
			<select name='filiere2' id='filiere2'>
				<option value=''></option>
				<c:forEach  var="filiere2" items="${requestScope.filieres}">
					<option value='${filiere2}' ${filiere2.equals(requestScope.filiere2) ? 'selected="selected"' : ''}>${filiere2}</option>
				</c:forEach>
			</select>
			(si double filière)
			
			<br/>
			<br/>
			
			<label for='godfathers' class='formlabel'>Parains/Maraines :</label>
			<input type='text' name='godfathers' id='godfathers' value="${requestScope.godfathers}" class='forminput'>
			<br/>
			<i>(Séparés d'une virgule)</i>
			<br/>
			<br/>
			<label for='godsons' class='formlabel'>Fillots/Fillotes :</label>
			<input type='text' name='godsons' id='godsons' value="${requestScope.godsons}" class='forminput'>
			<br/>
			<i>(Séparés d'une virgule)</i>
			<br/>
			<br/>
			
			<b><i>Associations/Clubs :</i></b>
			<br/>
			<span class='formlabel'><i>Nom :</i></span>
			<span class='forminput'><i>Rôle :</i></span>
			<br/>
			
			<% request.setAttribute("assos", Lists.assos); %>
			
			<span class='formlabel'>
				<select name='asso1' id='asso1'>
					<c:forEach  var="asso" items="${requestScope.assos}" varStatus="status">
						<option value='${status.index}' ${status.index == requestScope.asso1 ? 'selected="selected"' : ''}>${asso}</option>
					</c:forEach>
				</select>
			</span>
			<input type='text' name='role1' id='role1' value="${requestScope.role1}" class='forminput'>
			
			<span class='formlabel'>
				<select name='asso2' id='asso2'>
					<c:forEach  var="asso" items="${requestScope.assos}" varStatus="status">
						<option value='${status.index}' ${status.index == requestScope.asso2 ? 'selected="selected"' : ''}>${asso}</option>
					</c:forEach>
				</select>
			</span>
			<input type='text' name='role2' id='role2' value="${requestScope.role2}" class='forminput'>
			
			<span class='formlabel'>
				<select name='asso3' id='asso3'>
					<c:forEach  var="asso" items="${requestScope.assos}" varStatus="status">
						<option value='${status.index}' ${status.index == requestScope.asso3 ? 'selected="selected"' : ''}>${asso}</option>
					</c:forEach>
				</select>
			</span>
			<input type='text' name='role3' id='role3' value="${requestScope.role3}" class='forminput'>
			
			<span class='formlabel'>
				<select name='asso4' id='asso4'>
					<c:forEach  var="asso" items="${requestScope.assos}" varStatus="status">
						<option value='${status.index}' ${status.index == requestScope.asso4 ? 'selected="selected"' : ''}>${asso}</option>
					</c:forEach>
				</select>
			</span>
			<input type='text' name='role4' id='role4' value="${requestScope.role4}" class='forminput'>
			<br/>
			<span class='formlabel'>&nbsp;</span>
			<i class='forminput'>(entrez // pour effectuer un retour à la ligne)</i>
			<br/>
			<br/>
			
			<b><i>Compte Trombino :</i></b>
			<br/>
			<br/>
			
			<label for='email' class='formlabel'>Email :</label>
			<input type='text' id='email' value="${sessionScope.user.email}" disabled class='forminput'>
			<br/>
			
			<input type='checkbox' id='email_hide' name='email_hide' value='ok' ${requestScope.email_hide == 'ok' ? 'checked' : ''}/>
			<label for='email_hide'>Cacher mon email</label>
			<br/>
			<br/>
			
			<i><u>Changer de mot de passe :</u></i>
			<br/>
			<br/>
			
			<label for='oldpassword' class='formlabel'>Ancien mot de passe :</label>
			<input type='password' name='oldpassword' id='oldpassword' value="" class='forminput'>
			<br/>
			<label for='password' class='formlabel'>Nouveau mot de passe :</label>
			<input type='password' name='password' id='password' value="" class='forminput'>
			<br/>
			<label for='password2' class='formlabel'>Vérification</label>
			<input type='password' name='password2' id='password2' value="" class='forminput'>
			<br/>
			<br/>
			<input type='submit' value="Enregistrer" class='bigger'>
	</form>
</div>

</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>