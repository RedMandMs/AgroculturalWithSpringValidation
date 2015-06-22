<%@ page language="java" contentType="text/html; charset=Cp1251"
    pageEncoding="Cp1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
		<title>Изменение информации о компании</title>
	</head>
	<body>
		<div align="center" >
			<form name="goReviewCompanyForm" action="<c:url value='/organization/company/mycompany' />" method='GET'>
		 		<input type="submit" name="reviewMyCompanyBtn" value="Назад к информации о своей компании">
			</form>
			<br>
			<div>
				<h1>Изменение данных о компании:</h1>	
				<c:forEach var="message" items="${editOrganizationErors}">
					<h4>${message}</h4>
				</c:forEach>
				<sf:form method="POST" modelAttribute="changedCompany" acceptCharset="Cp1251">
					<fieldset>
						<table>
							<tr>
								<th><label for="id_organization">Id компании: </label></th>
								<td><sf:input readonly="true" path="id" size="20" id="id_organization" value="${myCompany.getId()}"/></td>
							</tr>
							
							<tr>
								<th><label for="name_organization">Введите имя компании: </label></th>
								<td><sf:input path="name" size="20" id="name_organization" value="${myCompany.getName()}"/></td>
							</tr>
							
							<tr>
								<th><label for="inn">Введите ИНН компании: </label></th>
								<td><sf:input path="inn" size="20" id="inn" value="${myCompany.getInn()}"/></td>
							</tr>
							
							<tr>
								<th><label for="organization_address">Введите адрес компании: </label></th>
								<td><sf:input path="address" size="50" id="organization_address" value="${myCompany.getAddress()}"/></td>
							</tr>
							
							<tr>
								<td><input type="submit" name="changedBtn" value="Изменить информацию о компании"></td>
							</tr>
						</table>
					</fieldset>
				</sf:form>
			</div>
		</div>
	</body>
</html>