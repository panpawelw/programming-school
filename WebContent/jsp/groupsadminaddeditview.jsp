<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		<%@include file="/css/style.css" %>
	</style>
	<title>User group add or edit</title>
</head>
<body>
<div class="content">
	<h3>Add / edit user group</h3>
	<form action="addeditgroup" method="post">
		<input type="hidden" name="id" value="${groupId}"/>
		Group name: <input type="text" name="name" placeholder="${groupNamePH}"/><br>
		<br>
		<input type="submit" value="${buttonPH}"/>
	</form>
</div>
</body>
</html>