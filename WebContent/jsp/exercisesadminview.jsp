<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Exercises administration</title>
<style type="text/css">
    <%@include file="/css/style.css"%>
</style>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<br>
	<h1 align="center">Exercises administration panel</h1>
	<h3 align="center">Exercises list</h3>
	<p align='center'><a href='${pageContext.request.contextPath}/addeditexercise?id=0'>Add exercise</a></p>
	<br>
	<table align='center' class='myTable'>
		<tr>
			<th>Exercise title</th>
			<th>Exercise description</th>
			<th colspan='2'>Actions</th>
		</tr>
		<c:forEach items="${exerciseslist}" var="exercise">
			<tr>	
				<td>${exercise.title}</td>
				<td>${exercise.description}</td>
				<td><a href="${pageContext.request.contextPath}/addeditexercise?id=${exercise.id}">Edit</a></td>
				<td><a href="${pageContext.request.contextPath}/deleteexercise?id=${exercise.id}">Delete</a></td>
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