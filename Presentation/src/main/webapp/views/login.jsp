<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Вход в систему</title>
	</head>
	<body>
		<div align="center" >
			<sec:authorize access="!isAuthenticated()">
				<c:url value="/j_spring_security_check" var="loginUrl" />
				<form name="loginForm" action="<c:url value='/j_spring_security_check' />" method='POST'>
					<div align="center">
						<H1>Вход в систему:</H1>
						<H2>${message}</H2>
						<c:if test="${param.error != null}">
							<H3>Неверный логин или пароль!!!</H3>
						</c:if>
						<c:if test="${param.logout != null}">
							<H3>Вы вышли из системы</H3>
						</c:if>
					</div>
					<table>
						<tr>
							<td><label for="user_login">Введите логин:</label></td>
							<td align="center">
								<input type="text" name="j_username" id="user_login">
							</td>
						</tr>
							
						<tr>
							<td><label for="user_password">Введите пароль:</label></td>
							<td align="center">
								<input type="password" name="j_password" id="user_password">
							</td>
						</tr>
					</table>
					<br>
					<div align="center">
						<input type="submit" name="authorizationBtn" value="Войти">
					</div>
				</form>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<H1><a>Вы уже вошли в систему, ваш логин: <sec:authentication property="principal.username" /></a></H1>
				<form name="logOutForm" action="<c:url value='/logout' />" method='GET'>
			   		<input type="submit" name="logOutBtn" value="Выйти из системы">
			    </form>
			</sec:authorize>
			<br>
			<form method="GET" action="/Presentation/">
				<input type="submit" name="goToMainPageBtn" value="Назад на главвную страницу"/>
			</form>
		</div>
	</body>
</html>