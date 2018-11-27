<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		<%@include file="/css/style.css" %>
	</style>
	<title>Exercise add or edit</title>
</head>
<body>
<div class="content">
	<h3>Add / edit exercise</h3>
	<form action="addeditexercise" accept-charset="UTF-8" method="post">
		<input type="hidden" name="id" value="${exerciseId}"/>
		Exercise title: <input type="text" name="title" value="${exerciseTitle}"/><br>
		Exercise description: <br><textarea rows="30" cols="50" name="description">${exerciseDescription}</textarea><br>
		<br>
		<input type="submit" value="${button}"/>
	</form>
</div>
</body>
</html>