<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>Exercise add or edit</title>
</head>
<body>
<div class="content">
	<h3>Add / edit exercise</h3>
	<form action="addeditexercise" accept-charset="UTF-8" method="post">
		<input type="hidden" name="id" value="${requestScope.exerciseId}"/>
		<label for="title">Exercise title</label>
		<input type="text" name="title" id="title" autofocus
			   value="${requestScope.exerciseTitle}"/><br>
		<label for="description">Exercise description</label><br>
		<textarea rows="30" cols="50" name="description"
				  id="description">${requestScope.exerciseDescription}</textarea><br><br>
		<input type="submit" value="${requestScope.button}"/>
	</form>
</div>
</body>
</html>