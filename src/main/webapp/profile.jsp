<%@ page import="ucll.project.domain.user.User" %>
<%@ page import="ucll.project.db.ConnectionPool" %>
<%@ page import="java.sql.*" %>
<%@ page import="ucll.project.ui.controller.Profile" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<!-- Navigation -->
<%@ include file="components/navigation.jspf" %>

<!-- Page Content -->
<main>
    <a href="Controller?command=Signout">log out</a>
    <section class="profileOverview">
        <p class="profileOverviewImage"><img src="static/images/ProfileAvatar.png"></p>
        <h1 class="profileOverviewItem">${firstname} ${lastname}<br></h1>
        <p class="profileOverviewItem">${email}
        </p>
        <p class="profileOverviewItem">you have ${availableStars} available</p>
        <p style="grid-area: span 1; grid-column-start: 4; grid-row-start: 4; text-align: center">${stars}</p>
    </section>
</main>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
