<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Поиск организаций</title>
</head>
<body>
 	<div align="center" >
			<br>
			<form method="GET" action="/Presentation/">
				<input type="submit" name="goToMainPageBtn" value="Перейти на главную страницу"/>
			</form>
			<br>
			<H1>Панель управления администратора</H1>
			<br>
			<form method="GET" action="findingCompany">
				<input type="submit" name="findingCompanyBtn" value="Поиск компаний"/>
			</form>
			<br>
			<form method="GET" action="/Presentation/passport/findlistpassports">
				<input type="submit" name="serchPassportsBtn" value="Поиск паспортов"/>
			</form>
			<br>
			<form method="GET" action="allEvents">
				<input type="submit" name="goToLogEventBtn" value="Посмотреть журнал всех событий"/>
			</form>
			<br>
			<c:if test="${myCompany != null}">
				<form method="GET" action="/Presentation/organization/company/${myCompany.getId()}">
					<input type="submit" name="goToLastCompanyBtn" value="Посмотреть последнюю просмотренную организацию"/>
				</form>
			</c:if>
 	</div>
</body>
</html>