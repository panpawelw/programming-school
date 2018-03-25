<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User group add or edit</title>
<style type="text/css">
    <%@include file="/jsp/style.css"%>
</style>
</head>
<body>
<p>${groupId}</p>
	<form action='addeditgroup' method='post'>
		<input type='hidden' name='id' value='${groupId}'/>
		<input type='text' name='name' placeholder='${groupNamePH}'/>
		<input type='submit' value='${buttonPH}'/>
	</form>
</body>
</html>