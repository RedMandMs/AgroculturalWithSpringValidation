<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<br>
		<form name="goToManagePanel" action="<c:url value='/admin/managing' />" method='GET'>
			<input type="submit" name="goToManagePanelBtn" value="Перейти в панель управления администратора">
		</form>
		<br>
		<h1>Поиск организаций:</h1>
		<sf:form method="POST" modelAttribute="serchingOrganization">
			<fieldset>
				<table  align="center">
					<tr>
						<th>Id Организации:</th>
						<th>Название организации:</th>
						<th>ИНН организации:</th>
						<th>Адрес организации:</th>
					</tr>
					<tr>
						<td align="center"><sf:input path="id" size="10" value="${serchingOrganization.getId()}"/></td>
						<td align="center"><sf:input path="name" size="20" value="${serchingOrganization.getName()}"/></td>
						<td align="center"><sf:input path="inn" size="10" value="${serchingOrganization.getInn()}"/></td>
						<td align="center"><sf:input path="address" size="30" value="${serchingOrganization.getAddress()}"/></td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input  type="submit" name="serchBtn" value="Найти подходящие организации">
				</div>
			</fieldset>
		</sf:form>
		
		<H2  align="center">Найденые организации:</H2>
			<table  align="center" rules="all" border="2">
			  <tr>
			  	<th align="center" width="200" >Id организации</th>
			  	<th align="center" width="200" >Имя организации</th>
			  	<th align="center" width="200" >ИНН организации</th>
			  	<th align="center" width="200" >Адрес организации</th>
			  	<th align="center"></th>
			  </tr>	
			  <c:forEach var="organization" items="${findingOrganizations}">
				  <tr height="40">
				    <td align="center">${organization.getId()}</td>
				    <td align="center">${organization.getName()}</td>
				    <td align="center">${organization.getInn()}</td>
				    <td align="center">${organization.getAddress()}</td>
				    <td align="center">
				    	<form action="/Presentation/organization/company/${organization.getId()}" method="get">
				    		<input type="submit" value="Посмотреть">
				    	</form>
				    </td>
				  </tr>
			  </c:forEach>
			</table>
	</div>
</body>
</html>