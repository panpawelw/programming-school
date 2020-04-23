<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>User add or edit</title>
</head>
<body>
<div class="content">
	<h3>Add / edit user</h3>
	<form action="addedituser" method="POST">
		<input type="hidden" name="id" value="${requestScope.userId}"/>
		<label for="name">User name:</label>
		<input type="text" name="name" id="name" autofocus value="${requestScope.userName}"/><br>
		<label for="email">User email:</label>
		<input type="text" name="email" id="email" value="${requestScope.userEmail}"/><br>
		<label for="password">User password:</label>
		<input type="text" name="password" id="password"/><br>
		<label for="group_id">Group id:</label>
		<input type="number" name="group_id" id="group_id" min="1" step="1"
						 value="${requestScope.userGroup_id}"/><br>
		<br>
		<input type="submit" value="${requestScope.button}"/>
	</form>
</div>
</body>
</html>