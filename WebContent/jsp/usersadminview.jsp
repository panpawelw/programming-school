<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Users administration</title>
<style type="text/css">
    <%@include file="/css/style.css"%>
</style>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<br>
	<h1 align="center">Users administration panel</h1>
	<h3 align="center">Users list</h3>
	<p align="center"><a href="/Workshop_3/addedituser?id=0">Add user</a></p>
	<br>
	<table align='center' class='myTable'>
		<tr>
			<th>Name</th>
			<th>Email</th>
			<th>Group Id</th>
			<th colspan='2'>Actions</th>
		</tr>
		<c:forEach items="${userslist}" var="user">
			<tr>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.group_id}</td>
				<td><a href="/Workshop_3/addedituser?id=${user.id}">Edit</a></td>
				<td><a href="/Workshop_3/deleteuser?id=${user.id}">Delete</a></td>
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