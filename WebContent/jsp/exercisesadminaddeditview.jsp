<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Exercise add or edit</title>
</head>
<body>
	<form action='addeditexercise' method='post'>
		<input type='hidden' name='id' value='${exerciseId}'/>
		Exercise title: <input type='text' name='title' placeholder='${exerciseTitlePH}'/><br>
		Exercise description: <input type='text' name='description' placeholder='${exerciseDescriptionPH}'/><br>
		<input type='submit' value='${buttonPH}'/>
	</form>
</body>
</html>