<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Header -->
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="/static/css/style.css" rel="stylesheet">
    <title>Hello world!</title>
</head>
<body>
<h1>voeg persoon toe</h1>
<div id="errors">
    <c:forEach items="${errors}" var="error">
        <p>${error}</p>
    </c:forEach>
</div>

<form action="Controller?command=AddUserVerification" method="post">

    <label for="firstname">first name</label>
    <input id="firstname" name="firstname" type="text" value="${previousFirstname}">

    <label for="lastname">last name</label>
    <input id="lastname" name="lastname" type="text"  value="${previousLastname}">

    <label for="email">e-mail</label>
    <input id="email" name="email" type="text"  value="${previousEmail}">

    <label for="role">role</label>
    <select name="role" id="role">
        <option value="USER" <c:if test="${USER}"> selected</c:if>>user</option>
        <option value="ADMIN"<c:if test="${ADMIN}"> selected</c:if>>admin</option>
    </select>

    <label for="manager">manager</label>
    <input  id="manager" name="manager" type="checkbox" <c:if test="${previousManager}">checked </c:if> >

    <label for="superuser">superuser</label>
    <input id="superuser" name="superuser" type="checkbox" <c:if test="${previousSuperuser}"> checked </c:if>>

    <input type="submit" value="voeg toe">
</form>
</body>
</html>
