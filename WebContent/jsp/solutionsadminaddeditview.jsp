<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Solution add or edit</title>
<style type="text/css">
    <%@include file="/jsp/style.css"%>
</style>
</head>
<body>
	<form action='addeditsolution' method='post'>
		<input type='hidden' name='id' value='${solutionId}'/>
		Exercise Id: <input type='number' name='exercise_id' min='1' step='1' value='${solutionExercise_idPH}'/><br>
		User Id: <input type='number' name='user_id' min='1' step='1' value='${solutionUser_idPH}'/><br>
		Description: <br><textarea rows='30' cols='50' name='description'>${solutionDescriptionPH}</textarea><br>
		<input type='submit' value='${buttonPH}'/>
	</form>
</body>
</html>