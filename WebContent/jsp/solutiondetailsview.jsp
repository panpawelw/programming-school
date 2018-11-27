<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		<%@include file="/css/style.css" %>
	</style>
	<title>Solution details</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="content">
	<h3>Exercise ${exercise.id} solution details:</h3>
	<h4>User: ${user.name} Exercise title: ${exercise.title} created on: ${solution.created} last updated
		on: ${solution.updated}</h4>
	<div>
		<textarea rows=50 cols=150>${solution.description}</textarea>
	</div>
	<br>
	<button type="button" name="back" onclick="history.back()">back</button>
</div>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>