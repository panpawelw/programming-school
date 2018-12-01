<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>Users administration</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="content">
	<h1>Users administration panel</h1>
	<h3>Users list</h3>
	<p><a href="${pageContext.request.contextPath}/addedituser?id=0">Add user</a></p>
	<p class="error">${errorMessage}</p>
	<br>
	<table class="myTable">
		<tr>
			<th>Name</th>
			<th>Email</th>
			<th>Group Id</th>
			<th colspan="2">Actions</th>
		</tr>
		<c:forEach items="${userslist}" var="user">
			<tr>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.group_id}</td>
				<td><a href="${pageContext.request.contextPath}/addedituser?id=${user.id}">Edit</a></td>
				<td><a href="${pageContext.request.contextPath}/deleteuser?id=${user.id}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<button type="button" name="back" onclick="history.back()">back</button>
</div>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>