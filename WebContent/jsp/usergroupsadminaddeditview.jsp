<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>User group add or edit</title>
</head>
<body>
<div class="content">
	<h3>Add / edit user group</h3>
	<form action="addeditgroup" method="post">
		<input type="hidden" name="id" value="${requestScope.groupId}"/>
		<label for="group_name">Group name</label>
		<input type="text" name="group_name" id="group_name" autofocus
			   value="${requestScope.groupName}"/><br>
		<br>
		<input type="submit" value="${requestScope.button}"/>
	</form>
</div>
</body>
</html>