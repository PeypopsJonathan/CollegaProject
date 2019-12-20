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
<main class="container">
<h1>Set your password</h1>
<div id="errors">
    <c:if test="${errorClass}">
        <p>${error}</p>
    </c:if>
</div>

    <div class="setPassword">
<form action="Controller?command=SetPassword" class="setPassword" method="post">
    <span id="password">
    <label for="password">Password</label>
    <input type="password" name="password" placeholder="Password"><br>
    </span>

    <span id="confirmPassword">
    <label for="confirmPassword">Confirm Password</label>
    <input type="password" name="confirmPassword" placeholder="Confirm Password">
    </span>
    <input type="submit" value="set password" id="btn">
</form>

    </div>

</main>
</body>
</html>
