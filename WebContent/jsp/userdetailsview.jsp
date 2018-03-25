<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
    <%@include file="/jsp/style.css" %>
</style>
<title>User details</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<h1 align='center'>User ${user.name} details</h1>
	<h4 align='center'> Email: ${user.email}</h4>
	<h4 align='center'> Group: ${groupname}</h4>
	<h3 align='center'> User's solutions</h3>
	<table align='center' class='myTable'>
		<tr>
			<th>Exercise title</th>
			<th>Last modified</th>
			<th>Details</th>
		</tr>
		<c:forEach items="${userssolutions}" var="usersSolution">
			<tr>	
				<td>${usersSolution.title}</td>
				<td>${usersSolution.modified}</td>
				<td><a href="/Workshop_3/solutiondetails?id=${usersSolution.id}">Details</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<div align='center'>
		<button type="button" name="back" onclick="history.back()">back</button>
	</div>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>