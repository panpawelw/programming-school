<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Programming school home page</title>
<style type="text/css">
table.cinereousTable {
  border: 6px solid #948473;
  background-color: #FFE3C6;
  width: 50%;
  text-align: center;
}
table.cinereousTable td, table.cinereousTable th {
  border: 1px solid #948473;
  padding: 4px 4px;
}
table.cinereousTable tbody td {
  font-size: 13px;
}
table.cinereousTable thead {
  background: #948473;
  background: -moz-linear-gradient(top, #afa396 0%, #9e9081 66%, #948473 100%);
  background: -webkit-linear-gradient(top, #afa396 0%, #9e9081 66%, #948473 100%);
  background: linear-gradient(to bottom, #afa396 0%, #9e9081 66%, #948473 100%);
}
table.cinereousTable thead th {
  font-size: 17px;
  font-weight: bold;
  color: #F0F0F0;
  text-align: center;
  border-left: 2px solid #948473;
}
table.cinereousTable thead th:first-child {
  border-left: none;
}

table.cinereousTable tfoot td {
  font-size: 16px;
}
</style>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<br><br><br>
	<h1 align="center">Programming school home page</h1>
	<br>
	<h3 align="center">Most recent solutions</h3>
	<table align='center' class='cinereousTable'>
		<tr>
			<th>Exercise title</th>
			<th>Author</th>
			<th>Last modified</th>
			<th>Details</th>
		</tr>
		<c:forEach items="${lastSolutions}" var="lastSolution">
			<tr>	
				<td>${lastSolution.title}</td>
				<td>${lastSolution.name}</td>
				<td>${lastSolution.modified}</td>
				<td><a href="/Workshop_3/solutiondetails?id=${lastSolution.id}">Details</a></td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>