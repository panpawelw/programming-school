<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Exercise add or edit</title>
</head>
<body>
<form action="addeditexercise" accept-charset="UTF-8" method="post">
	<input type="hidden" name="id" value="${exerciseId}"/>
	Exercise title: <input type="text" name="title" value="${exerciseTitle}"/><br>
	Exercise description: <br><textarea rows="30" cols="50" name="description">${exerciseDescription}</textarea><br>
	<input type="submit" value="${button}"/>
</form>
</body>
</html>