<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>User details</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="content">
	<h1>User ${user.name} details</h1>
	<h4> Email: ${user.email}</h4>
	<h4> Group: ${groupname}</h4>
	<h3> User's solutions</h3>
	<table class="myTable">
		<tr>
			<th>Exercise title</th>
			<th>Last modified</th>
			<th>Details</th>
		</tr>
		<c:forEach items="${userssolutions}" var="usersSolution">
			<tr>
				<td>${usersSolution.title}</td>
				<td>${usersSolution.modified}</td>
				<td><a href="${pageContext.request.contextPath}/solutiondetails?id=${usersSolution.id}">Details</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<button type="button" name="back" onclick="history.back()">back</button>
</div>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>