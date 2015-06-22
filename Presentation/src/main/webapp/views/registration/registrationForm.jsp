<%@ page language="java" contentType="text/html; charset=Cp1251"
    pageEncoding="Cp1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<title>Регистрация нового пользователя</title>
</head>
<body>
	<div align="center" >
		<br>
		<form method="GET" action="/Presentation/">
			<input type="submit" name="goToMainPageBtn" value="Назад на главвную страницу"/>
		</form>
		<br>
		<h1>Регистрация нового пользователя:</h1>
		<sec:authorize access="!isAuthenticated()">
			<c:forEach var="message" items="${listErorRegistration}">
				<h4>${message}</h4>
			</c:forEach>
			<sf:form method="POST" modelAttribute="userOrganization" acceptCharset="Cp1251">
				<fieldset>
					<table>
						<tr>
							<th><label for="user_login">Введите логин:</label></th>
							<td>
								<sf:input path="login" size="20" id="user_login" value="${reviwingOrganization.getLogin()}"/>
								<small id ="login_msg">Логин должен состоять из латинских символов и цифр. Максимальная длинна логина 15 символов, минимальная - 5</small>
							</td>
						</tr>
						
						<tr>
							<th><label for="user_password">Введите пароль:</label></th>
							<td>
								<sf:password path="password" size="20" id="user_password"/>
								<small id ="password_msg">Пароль должен состоять из латинских символов и цифр. Максимальная длинна пароля 15 символов, минимальная - 5</small>
							</td>
						</tr>
						
						<tr>
							<th><label for="user_repassword">Введите пароль повторно:</label></th>
							<td>
								<sf:password path="repassword" size="20" id="user_repassword"/>
							</td>
						</tr>
						
						<tr>
							<th><label for="name_organization">Введите имя компании: </label></th>
							<td><sf:input path="organizationName" size="20" id="name_organization" value="${reviwingOrganization.getOrganizationName()}"/></td>
						</tr>
						
						<tr>
							<th><label for="inn">Введите ИНН компании: </label></th>
							<td><sf:input path="inn" size="20" id="inn" value="${reviwingOrganization.getInn()}"/></td>
						</tr>
						
						<tr>
							<th><label for="organization_address">Введите адрес компании: </label></th>
							<td><sf:input path="address" size="50" id="organization_address" value="${reviwingOrganization.getAddress()}"/></td>
						</tr>
						
						<tr>
							<td><input type="submit" name="regestrationBtn" value="Зарегистрироваться"></td>
						</tr>
					</table>
				</fieldset>
			</sf:form>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<H1><a>Вы уже вошли в систему, ваш логин: <sec:authentication property="principal.username" /></a></H1>
			<form name="logOutForm" action="<c:url value='/logout' />" method='GET'>
				<input type="submit" name="logOutBtn" value="Выйти из системы">
			</form>
		</sec:authorize>
	</div>
</body>
</html>