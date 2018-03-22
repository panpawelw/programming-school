<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
    <%@include file="/jsp/style.css" %>
</style>
<title>User groups</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<h1 align='center'>User Groups</h1>
	<table align='center' class='myTable'>
		<tr>
			<th>Group name</th>
			<th>Details</th>
		</tr>
		<c:forEach items="${usergroups}" var="UserGroup">
			<tr>	
				<td>${UserGroup.name}</td>
				<td><a href="/Workshop_3/userslist?id=${UserGroup.id}">User list</a></td>
			</tr>
		</c:forEach>
	</table>
	<div align='center'>
		<button type="button" name="back" onclick="history.back()">back</button>
	</div>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>