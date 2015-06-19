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
		<h1>Полный журнал событий:</h1>
		<br>
		<h3>Фильтр событий:</h3>
		<sf:form method="POST" modelAttribute="serchingEvent">
			<fieldset>
				<table  align="center">
					<tr>
						<th>Id события:</th>
						<th>Тип события:</th>
						<th>Id автора события:</th>
						<th>Имя автора события:</th>
						<th>Id пасспорта:</th>
					</tr>
					<tr>
						<td align="center"><sf:input path="id" size="10" value="${serchingEvent.getId()}"/></td>
						<td align="center">
							<sf:select path="typeEvent" id="typeEvent">
								<c:forEach var="typeEvent" items="${typesEvent}">
									<sf:option value="${typeEvent.getType()}">${typeEvent.getView()}</sf:option>
								</c:forEach>
							</sf:select>
						<td align="center"><sf:input path="idAuthor" size="10" value="${serchingEvent.getIdAuthor()}"/></td>
						<td align="center"><sf:input path="nameAuthor" size="30" value="${serchingEvent.getNameAuthor()}"/></td>
						<td align="center"><sf:input path="idPassport" size="30" value="${serchingEvent.getIdPassport()}"/></td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input  type="submit" name="serchBtn" value="Найти подходящие организации">
				</div>
			</fieldset>
		</sf:form>
		<br>
		<table cellspacing="15" rules="all" border="2">
			<tr>
			    <th align="center">Id события</th>
			    <th align="center">Id пасспорта</th>
			    <th align="center">Сообщение</th>
			    <th align="center">Дата</th>
			    <th align="center">Время</th>
			    <th align="center">Тип события</th>
			    <th></th>
			</tr>
	    	<c:forEach var="event" items="${findingEvents}">
				<tr height="30">
				    <td align="center">${event.getId()}</td>
				    <td align="center">${event.getIdPassport()}</td>
				    <td align="center">${event.getMessage()}</td>
				    <td align="center" width="100">${event.getDate()}</td>
				    <td align="center" width="100">${event.getTime()}</td>
				    <td align="center" width="200">${typesEvent[0].getTypeEvent(event.getTypeEvent()).getView()}</td>
				    <td align="center" width="100">
				    	<form action="/Presentation/admin/deleteEvent/${event.getId()}" method="GET">
				    		<input type="submit" value="Удалить">
				    	</form>
				    </td>
				</tr>
		  	</c:forEach>
		</table>
	</div>
</body>
</html>