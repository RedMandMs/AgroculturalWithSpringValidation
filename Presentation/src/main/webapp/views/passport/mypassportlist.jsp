<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Мои паспорта</title>
	</head>
	<body>
		<div align="center" >
			<table align="center" >
				<tr>
					<c:if test="${isAdmin}">
						<td>
							<form name="goToManagePanel" action="<c:url value='/admin/managing' />" method='GET'>
								<input type="submit" name="goToManagePanelBtn" value="Перейти в панель управления администратора">
							</form>
						</td>	
					</c:if>
					<td>
						<form name="goReviewCompanyForm" action="<c:url value='/organization/company/mycompany' />" method='GET'>
							<input type="submit" name="reviewMyCompanyBtn" value="Назад к информации о своей компании">
						</form>
					</td>
					<td>
						<form method="GET" action="/Presentation/passport/findlistpassports">
							<input type="submit" name="serchPassportsBtn" value="Поиск паспортов"/>
						</form>
					</td>
				</tr>
			</table>
			<br>
			<H1>Паспорта принадлежащие организации</H1>
			<H2>Список всех ваших паспортов:</H2>
			<table cellspacing="15" rules="all" border="2" >
			  <tr>
			  	<th align="center">Id паспорта</th>
			  	<th align="center">Имя организации-владельца</th>
			  	<th align="center" width="200">Регион поля</th>
			  	<th align="center" width="200">Кадастровый номер</th>
			  	<th align="center">Площадь поля</th>
			  	<th align="center" width="200">Тип поля</th>
			  	<th align="center">Комментарий к полю</th>
			  	<th></th>
			  </tr>	
		  	  <c:forEach var="passport" items="${myPassportsList}" >
				  <tr height="40">
				    <td align="center">${passport.getId()}</td>
				    <td align="center">${passport.getNameOwner()}</td>
				    <td align="center">${regions[0].getRegion(passport.getRegion()).getView()}</td>
				    <td align="center">${passport.getCadastrNumber()}</td>
				    <td align="center">${passport.getArea()}</td>
				    <td align="center">${types[0].getTypeOf(passport.getType()).getType()}</td>
				    <td align="center">${passport.getComment()}</td>
				    <td align="center">
				    	<form action="/Presentation/passport/${passport.getId()}" method="get">
				    		<input type="submit" value="Посмотреть">
				    	</form>
				    </td>
				  </tr>
			  </c:forEach>
			</table>
			<br>
			<form method="GET" action="/Presentation/passport/createPassport">
				<input type="submit" name="createPassportBtn" value="Создать новый пасспорт поля"/>
			</form>
		</div>
	</body>
</html>