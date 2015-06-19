<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Изменения информации о паспортах сделанные пользователями в данной организации</title>
</head>
<body>
	<div align="center">
		<form name="goReviewCompanyForm" action="<c:url value='/organization/company/mycompany' />" method='GET'>
			<input type="submit" name="reviewMyCompanyBtn" value="Назад к информации о своей компании">
		</form>
		<br>
		<table cellspacing="15" rules="all" border="2">
			<tr>
			    <td align="center">Id события</td>
			    <td align="center">Id пасспорта</td>
			    <td align="center">Сообщение</td>
			    <td align="center">Дата</td>
			    <td align="center">Время</td>
			    <td align="center">Тип события</td>
			</tr>
	    	<c:forEach var="event" items="${events}">
				<tr height="30">
				    <td align="center">${event.getId()}</td>
				    <td align="center">${event.getIdPassport()}</td>
				    <td align="center">${event.getMessage()}</td>
				    <td align="center" width="100">${event.getDate()}</td>
				    <td align="center" width="100">${event.getTime()}</td>
				    <td align="center" width="200">${typesEvent[0].getTypeEvent(event.getTypeEvent()).getView()}</td>
				</tr>
		  	</c:forEach>
		</table>
	</div>
	
</body>
</html>