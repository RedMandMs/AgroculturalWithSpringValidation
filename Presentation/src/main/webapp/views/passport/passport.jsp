<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Просмотр пасспорта</title>
	</head>
	<body>
		<div align="center" >
			<form method="GET" action="/Presentation/passport/${lastList}" >
				<input type="submit" name="backToPassportsListBtn" value="Назад к списку паспортов"/>
			</form>
			<br>
			<table rules="all" border="2">
				<tr>
					<td>Id пасспорта: </td>
					<td>${reviewingPassport.getId()}</td>
				</tr>
				
				<tr>
					<td>Компания-владелец паспорта: </td>
					<td>${reviewingPassport.getNameOwner()}</td>
				</tr>
				
				<tr>
					<td>Район поля: </td>
					<td>${regions[0].getRegion(reviewingPassport.getRegion()).getRegion()}</td>
				</tr>
				
				<tr>
					<td>Кадастровый номер: </td>
					<td>${reviewingPassport.getCadastrNumber()}</td>
				</tr>
					
				<tr>
					<td>Площадь поля: </td>
					<td>${reviewingPassport.getArea()}</td>
				</tr>
					
				<tr>
					<td>Тип поля: </td>
					<td>${types[0].getTypeOf(reviewingPassport.getType()).getType()}</td>
				</tr>
				
				<tr>
					<td>Комментарий: </td>
					<td>${reviewingPassport.getComment()}</td>	
			</table>
			<c:if test="${isMyPassport || isAdmin}">
				<br>
				<form method="GET" action="change_passport_info/${reviewingPassport.getId()}" >
					<input type="submit" name="changePassportInfoBtn" value="Изменить информацию о пасспорте"/>
				</form>
				<br>
				<form method="POST" action="delete" >
					<input type="hidden" value="${reviewingPassport.getId()}" name="idPassport"/>
					<input type="submit" name="deletePassportBtn" value="Удалить паспорт"/>
				</form>
			</c:if>
			
		</div>
	</body>
</html>