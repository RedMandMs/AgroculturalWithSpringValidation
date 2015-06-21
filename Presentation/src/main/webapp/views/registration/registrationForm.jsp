<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Регистрация нового пользователя</title>
</head>
<body>
	<div align="center" >
		<h1>Регистрация нового пользователя:</h1>
		<c:forEach var="message" items="${listErorRegistration}">
			<h4>${message}</h4>
		</c:forEach>
		<sf:form method="POST" modelAttribute="userOrganization">
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
	</div>
</body>
</html>