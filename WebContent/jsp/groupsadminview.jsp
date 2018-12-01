<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>User groups administration</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="content">
	<h1>User groups administration panel</h1>
	<h3>User groups list</h3>
	<p><a href="${pageContext.request.contextPath}/addeditgroup?id=0">Add user group</a></p>
	<p class="error">${errorMessage}</p>
	<br>
	<table class="myTable">
		<tr>
			<th>Group name</th>
			<th colspan="2">Actions</th>
		</tr>
		<c:forEach items="${groupslist}" var="group">
			<tr>
				<td>${group.name}</td>
				<td><a href="${pageContext.request.contextPath}/addeditgroup?id=${group.id}">Edit</a></td>
				<td><a href="${pageContext.request.contextPath}/deletegroup?id=${group.id}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<button type="button" name="back" onclick="history.back()">back</button>
</div>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>