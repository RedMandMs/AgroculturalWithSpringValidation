<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<div align="center" >
			<table>
				<tr>
					<td colspan="3" align="center">
						<H1>Добро пожаловать на сайт отслеживания сельско-хозяйственных пасспортов!</H1>
					</td>
				</tr>
				<tr>
					<sec:authorize access="!isAuthenticated()">
						<td align="center">
					        <form name="goLogInForm" action="<c:url value='/login' />" method='GET'>
					        	<input type="submit" name="goLogInBtn" value="Войти в систему">
					        </form>
				        </td>
					    <td align="center">
					        <form name="goRegistrForm" action="<c:url value='/registration' />" method='GET'>
					        	<input type="submit" name="goRegistrBtn" value="Зарегистрироваться">
					        </form>
					    </td>
				    </sec:authorize>
				    <sec:authorize access="isAuthenticated()">
				    	<td align="center">
				 	       <p>Ваш логин: <sec:authentication property="principal.username" /></p>
				 	    </td>
				 	    <c:if test="${isAdmin}">
				 	    	<td align="center">
					 	    	<form name="goToManagePanel" action="<c:url value='/admin/managing' />" method='GET'>
					     			<input type="submit" name="goToManagePanelBtn" value="Перейти в панель управления системой">
					    		</form>
					    	</td>
				 	    </c:if>
				 	    <c:if test="${!isAdmin}">
					 	    <td align="center">
					    		<form name="goReviewCompanyForm" action="<c:url value='/organization/company/mycompany' />" method='GET'>
					     			<input type="submit" name="reviewMyCompanyBtn" value="Просмотр информации о своей компании">
					    		</form>
					    	</td>
				    	</c:if>
				 	    <td align="center">
				    		<form name="logOutForm" action="<c:url value='/logout' />" method='GET'>
				     			<input type="submit" name="logOutBtn" value="Выйти из системы">
				    		</form>
				    	</td>
				    </sec:authorize>
				</tr>
		    </table>
		   </div>
	</body>
</html>