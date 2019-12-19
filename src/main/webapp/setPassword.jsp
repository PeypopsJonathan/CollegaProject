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
<!-- Navigation -->
<%@ include file="components/navigation.jspf" %>

<!-- Page Content -->
<body>
<h1>voeg persoon toe</h1>
<div id="errors">
    <c:if test="${errorClass}">
        <p>${error}</p>
    </c:if>
</div>

<form action="Controller?command=SetPassword" method="post">

    <label for="password">password</label>
    <input type="password" name="password" id="password">
    <label for="confirmPassword">confirm password</label>
    <input type="password" name="confirmPassword" id="confirmPassword">

    <input type="submit" value="set password">
</form>
</body>
</html>
