<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Solutions administration</title>
<style type="text/css">
    <%@include file="/css/style.css"%>
</style>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<br>
	<h1 align="center">Solutions administration panel</h1>
	<h3 align="center">Solutions list</h3>
	<p align='center'><a href='/Workshop_3/addeditsolution?id=0'>Add solution</a></p>
	<br>
	<table align='center' class='myTable'>
		<tr>
			<th>Created</th>
			<th>Updated</th>
			<th>Solution description</th>
			<th>Exercise Id</th>
			<th>User Id</th>
			<th colspan='2'>Actions</th>
		</tr>
		<c:forEach items="${solutionslist}" var="solution">
			<tr>
				<td>${solution.created}</td>
				<td>${solution.updated}</td>
				<td>${solution.description}</td>
				<td>${solution.exercise_id}</td>
				<td>${solution.user_id}</td>
				<td><a href="/Workshop_3/addeditsolution?id=${solution.id}">Edit</a></td>
				<td><a href="/Workshop_3/deletesolution?id=${solution.id}">Delete</a></td>
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