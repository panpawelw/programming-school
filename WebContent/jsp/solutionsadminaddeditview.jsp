<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>Solution add or edit</title>
</head>
<body>
<div class="content">
	<h3>Add / edit solution</h3>
	<form action="addeditsolution" method="post">
		<input type="hidden" name="id" value="${solutionId}"/>
		Exercise Id: <input type="number" name="exercise_id" min="1" step="1" value="${solutionExercise_id}"/><br>
		User Id: <input type="number" name="user_id" min="1" step="1" value="${solutionUser_id}"/><br>
		Description: <br><textarea rows="30" cols="50" name="description">${solutionDescription}</textarea><br>
		<br>
		<input type="submit" value="${button}"/>
	</form>
</div>
</body>
</html>