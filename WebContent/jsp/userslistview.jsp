<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>Group users list</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="content">
	<h1>Group ${requestScope.groupname} users list</h1>
	<table class="myTable">
		<tr>
			<th>User name</th>
			<th>Email</th>
			<th>Details</th>
		</tr>
		<c:forEach items="${requestScope.groupuserslist}" var="User">
			<tr>
				<td>${User.name}</td>
				<td>${User.email}</td>
				<td><a href="${pageContext.request.contextPath}/userdetails?id=${User.id}">User details</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<button type="button" name="back" onclick="history.back()">back</button>
</div>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>