<%@page import="constants.Lists"%>
<%@page import="java.util.List"%>
<%@page import="model.UserAsso"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import = "util.AppConfig" %> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trombi - ${sessionScope.user.firstName} ${sessionScope.user.lastName} </title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
<script type="text/javascript" src="js/js.js"></script>
</head>
<body>

<%@ include file="jspf/headerLogedOn.jspf" %>
<div class='content'>

<div id='title'>${requestScope.member.getNicknameOrFirstName().toUpperCase()}
	<c:if test="${requestScope.userIsMember == true}">
		<a href='edit_profile'>
			<img src='img/user_edit.png' id='edit_profile_icon' alt='edit_profile' title='Editer profil'/>
		</a>
	</c:if>
</div>
<br/>

<div id='userInfos'>
	<div id='photoDiv'>
		<a href="${requestScope.photoPath}">
			<img src='${requestScope.photoPath}' alt='user_picture'  id='photo'>
		</a>
		<br/>
		<c:if test="${requestScope.blousePath != null}">
			<a href="${requestScope.blousePath}">
				<img src='${requestScope.blousePath}' alt='blouse'  id='blouse'>
			</a>
		</c:if>	
	</div>
	
	<div>
		<b>${requestScope.member.getFirstName()}</b>
		<b>${member.getLastName()}</b>
		<br/>
		
		${requestScope.member.getPhone()}
		<br/>
		<c:if test="${requestScope.member.isHide_email() == false}">
			<a href="mailto:${requestScope.member.getEmail()}">${requestScope.member.getEmail()}</a>
		</c:if>
		<br/>
		${requestScope.member.getBirthdayAsString()}
		<br/>
		<br/>
		${requestScope.member.getDepartment()}
		${requestScope.member.getAllSpecialisations()}
		<br/>
		<br/>
		<font color='red' class="usertext">${requestScope.member.getPersonalPhrase()}</font>
		<br/>
		<br/>
		<c:if test="${requestScope.member.getGodfathers() != null}">
			<i class="usertext">Ancêtres : ${requestScope.member.getGodfathers()}</i>
		</c:if>
		<br/>
		<c:if test="${requestScope.member.getGodsons() != null}">
			<i class="usertext">Descendance : ${requestScope.member.getGodsons()}</i>
		</c:if>
	</div>
</div>

<br/>
<br/>

<!--  Associations -->
<c:if test="${not empty requestScope.ualist}">
	<b>Associations:</b>
	<br/>

	<div class='assoscontainer'>
		<div class='asso'>
			<% @SuppressWarnings("unchecked")
				List<UserAsso> userassos = (List<UserAsso>)request.getAttribute("ualist");
				int assoIndex = userassos.get(0).getAsso();
				String assoName = Lists.assos.get(assoIndex);
				request.setAttribute("assoname1", assoName);
			%>
			<img class='assologo' src='${requestScope.logoasso1}' alt='logo ${requestScope.assoname1}' title='${requestScope.assoname1}'>
			<div class='assoroles'>
				<div class='roles'>
					<i>${fn:replace(requestScope.ualist.get(0).getRole(), "//", "<br/>")}</i>
				</div>
			</div>
		</div>
		<c:if test="${requestScope.ualist.size() > 1}">
			<div class='asso'>
				<% assoIndex = userassos.get(1).getAsso();
					assoName = Lists.assos.get(assoIndex);
					request.setAttribute("assoname2", assoName);
				%>
				<img class='assologo' src='${requestScope.logoasso2}' alt='logo ${requestScope.assoname2}' title='${requestScope.assoname2}'>
				<div class='assoroles'>
					<div class='roles'>
						<i>${fn:replace(requestScope.ualist.get(1).getRole(), "//", "<br/>")}</i>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<c:if test="${requestScope.ualist.size() > 2}">
		<div class='assoscontainer'>
			<div class='asso'>
				<% assoIndex = userassos.get(2).getAsso();
					assoName = Lists.assos.get(assoIndex);
					request.setAttribute("assoname3", assoName);
				%>
				<img class='assologo' src='${requestScope.logoasso3}' alt='logo ${requestScope.assoname3}' title='${requestScope.assoname3}'>
				<div class='assoroles'>
					<div class='roles'>
						<i>${fn:replace(requestScope.ualist.get(2).getRole(), "//", "<br/>")}</i>
					</div>
				</div>
			</div>
			<c:if test="${requestScope.ualist.size() > 3}">
				<div class='asso'>
					<% assoIndex = userassos.get(3).getAsso();
						assoName = Lists.assos.get(assoIndex);
						request.setAttribute("assoname4", assoName);
					%>
					<img class='assologo' src='${requestScope.logoasso4}' alt='logo ${requestScope.assoname4}' title='${requestScope.assoname4}'>
					<div class='assoroles'>
						<div class='roles'>
							<i>${fn:replace(requestScope.ualist.get(3).getRole(), "//", "<br/>")}</i>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</c:if>
</c:if>

<!--  Commentaires -->
<c:set var="hasCommented" value="false"/>
<c:if test="${fn:length(comments)>0}">
	<b>Commentaires:</b>

	<c:set var="style" value="pair"/>
	<c:forEach var="comment" items="${requestScope.comments}">
		
		<div class='${style}'>
		
			<div class='controls'>
			<c:if test="${user.admin}">
				<a class='control' href='move_remove_comment?id=${comment.id}&action=up' title='Monter'>&#8593;</a>
				<a class='control' href='move_remove_comment?id=${comment.id}&action=down' title='Descendre'>&#8595;</a>
			</c:if>
			<c:if test="${user.admin || comment.fromUser.id == user.id}">
				<a class='control' href='move_remove_comment?id=${comment.id}&action=remove' title='Supprimer'>&times;</a>
			</c:if>
			</div>
		
			<a href='profile?member=${comment.getFromUser().getId()}'>
				<b>${comment.getFromUser().getNicknameOrFullName()}</b>
			</a>
			<br/>
			${comment.getText()}
		</div>
				
		<c:if test="${sessionScope.user.getId() == comment.getFromUser().getId()}">
			<c:set var="hasCommented" value="true"/>
		</c:if>
		
		<c:choose>
			<c:when test="${style == 'pair'}">
				<c:set var="style" value="impair"/>
			</c:when>
			<c:otherwise>
				<c:set var="style" value="pair"/>
			</c:otherwise>
		</c:choose>
		
	</c:forEach>
	<br/>
</c:if>

	<c:if test="${(userIsMember==false) && (hasCommented==false)}">
		<br/>
		<form action='add_comment' method='post'>
			<input type='hidden' name='member' value='${requestScope.member.getId()}'>
			Ajouter un commentaire : (150 caractères max)
			<br/>
			<textarea name='comment' id='comment' class='forminput' onkeyup="javascript:MaxLengthTextarea(this, 150);"></textarea>
			<br/>
			<input type='submit' value='Commenter'>
		</form>
		<br/>
		<br/>
	</c:if>
	<c:if test="${(userIsMember==false) && (hasCommented==true)}">
		<br/>
		<i>Vous avez déjà commenté ce profil.</i>
		<br/>
		<br/>
	</c:if>


<!--  Commentaires laissés -->
<c:if test="${((sessionScope.user.admin==true) || (userIsMember==true)) && (fn:length(commentsLeft)>0) }">
	<b>Commentaires laissés:</b>
	<br/>
	<c:set var="style" value="pair"/>
	<c:forEach var="comment" items="${requestScope.commentsLeft}">
		<div class='${style}'>
			<a style='text-decoration: none;' href='profile?member=${comment.getToUser().getId()}'>
				<b>${comment.getToUser().getNicknameOrFullName()}</b>
			</a>
			<br/>
			${comment.getText()}
		</div>
		
		<c:choose>
			<c:when test="${style == 'pair'}">
				<c:set var="style" value="impair"/>
			</c:when>
			<c:otherwise>
				<c:set var="style" value="pair"/>
			</c:otherwise>
		</c:choose>
	
	</c:forEach>
	
</c:if>

</div>
<%@ include file="jspf/footer.jspf" %>

</body>
</html>