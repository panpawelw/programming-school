<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User group add or edit</title>
</head>
<body>
	<form action='addeditgroup' method='post'>
		<input type='hidden' name='id' value='${groupId}'/>
		Group name: <input type='text' name='name' placeholder='${groupNamePH}'/><br>
		<input type='submit' value='${buttonPH}'/>
	</form>
</body>
</html>