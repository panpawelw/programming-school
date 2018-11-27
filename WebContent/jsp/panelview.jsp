<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		<%@include file="/css/style.css" %>
	</style>
	<title>Admin panel</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="content">
	<h1>Admin panel</h1>
	<br><br><br>
	<h2><a href="${pageContext.request.contextPath}/groupsadminpanel">Manage user groups</a></h2>
	<h2><a href="${pageContext.request.contextPath}/usersadminpanel">Manage users</a></h2>
	<h2><a href="${pageContext.request.contextPath}/exercisesadminpanel">Manage exercises</a></h2>
	<h2><a href="${pageContext.request.contextPath}/solutionsadminpanel">Manage solutions</a></h2>
	<br>
	<div>
		<button type="button" name="back" onclick="history.back()">back</button>
	</div>
</div>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>