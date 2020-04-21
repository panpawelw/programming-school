<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Solution add or edit</title>
</head>
<body>
<div class="content">
    <h3>Add / edit solution</h3>
    <form action="addeditsolution" method="post">
        <input type="hidden" name="id" value="${requestScope.solutionId}"/>
        <label for="exercise_id">Exercise Id</label>
        <input type="number" name="exercise_id" id="exercise_id" min="1" step="1" autofocus
               value="${requestScope.solutionExercise_id}"/><br>
        <label for="user_id">User Id</label>
        <input type="number" name="user_id" id="user_id" min="1" step="1"
               value="${requestScope.solutionUser_id}"/><br>
        <label for="description">Exercise Description</label><br>
        <textarea rows="30" cols="50" name="description"
                  id="description">${requestScope.solutionDescription}</textarea><br>
        <br>
        <input type="submit" value="${requestScope.button}"/>
    </form>
</div>
</body>
</html>