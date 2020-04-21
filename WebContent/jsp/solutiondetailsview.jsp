<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>Solution details</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="content">
	<h3>Exercise ${requestScope.exercise.id} solution details:</h3>
	<h4>User: ${requestScope.user.name} Exercise title: ${requestScope.exercise.title} created on:
		${requestScope.solution.created}
		last updated
		on: ${requestScope.solution.updated}</h4>
	<div>
		<label for="description">Description</label>
		<textarea name="description" id="description" rows=50
				  cols=150>${requestScope.solution.description}</textarea>
	</div>
	<br>
	<button type="button" name="back" onclick="history.back()">back</button>
</div>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>