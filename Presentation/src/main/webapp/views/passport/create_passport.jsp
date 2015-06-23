<%@ page language="java" contentType="text/html; charset=Cp1251"
    pageEncoding="Cp1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
	<title>Просмотр пасспорта</title>
	</head>
	<body>
		<div align="center" >
			<form name="goReviewCompanyForm" action="<c:url value='/organization/company/mycompany' />" method='GET'>
		 		<input type="submit" name="reviewMyCompanyBtn" value="Назад к информации о своей компании">
			</form>
			<br>
			<div>
				<h1>Создание паспорта:</h1>	
				<c:forEach var="message" items="${messagesCreateEror}">
					<h4>${message}</h4>
				</c:forEach>
				<sf:form method="POST" modelAttribute="createdPassport" acceptCharset="Cp1251">
					<fieldset>
						<table>
							<tr>
								<td>Компания-владелец паспорта: </td>
								<td>${myCompany.getName()}</td>
							</tr>
						
							<tr>
								<th><label for="region">Введите регион поля: </label></th>
								<td>
									<sf:select path="region" id="region">
										<c:forEach var="region" items="${regions}">
											<sf:option value="${region.getRegion()}">${region.getView()}</sf:option>
										</c:forEach>
									</sf:select>
								</td>
							</tr>
							
							<tr>
								<th><label for="cadastrNumber">Введите кадастровый номер паспорта: </label></th>
								<td><sf:input path="cadastrNumber" size="20" id="cadastrNumber" value="${creatingPassport.getCadastrNumber()}"/></td>
							</tr>
							
							<tr>
								<th><label for="area">Введите площадь поля: </label></th>
								<td><sf:input path="area" size="50" id="area" value="${creatingPassport.getArea()}"/></td>
							</tr>
							
							<tr>
								<th><label for="type">Введите тип поля: </label></th>
								<td>
									<sf:select path="type" id="type">
										<c:forEach var="type" items="${types}">
											<sf:option value="${type.getType()}"/>
										</c:forEach>
									</sf:select>
								</td>
							</tr>
							
							<tr>
								<th><label for="comment">Введите комментарий к пасспорту: </label></th>
								<td><sf:input path="comment" size="50" id="comment" value="${creatingPassport.getComment()}"/></td>
							</tr>
							
							<tr>
								<td><input type="submit" name="changedBtn" value="Создать пасспорт"></td>
							</tr>
						</table>
					</fieldset>
				</sf:form>
			</div>
		</div>
	</body>
</html>