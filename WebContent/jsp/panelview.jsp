<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin panel</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<br>
	<h1 align='center'>Admin panel</h1>
	<br><br><br>
	<h2 align='center'><a href="/Workshop_3/groups">Manage user groups</a></h2>
	<h2 align='center'><a href="/Workshop_3/users">Manage users</a></h2>
	<h2 align='center'><a href="/Workshop_3/exercises">Manage exercises</a></h2>
	<h2 align='center'><a href="/Workshop_3/solutions">Manage solutions</a></h2>
	<div align='center'>
		<button type="button" name="back" onclick="history.back()">back</button>
	</div>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>