<%@ page import="ucll.project.domain.user.User" %>
<%@ page import="ucll.project.db.ConnectionPool" %>
<%@ page import="java.sql.*" %>
<%@ page import="ucll.project.ui.controller.Profile" %>
<%@ page import="org.openqa.selenium.remote.SessionId" %>
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

    <section class="profileOverview">
        <p class="profileOverviewImage"><img src="static/images/ProfileAvatar.png"></p>
        <h1 class="profileOverviewItem">${firstname} ${lastname}<br></h1>
        <p class="profileOverviewItem">${email}</p>
        <p class="profileOverviewItem2">you have ${availableStars} stars left to give this month</p>
        <div class="profileStarOverview">
            <c:forEach var="star" items="${stars}">
                <p class="starText">${star.sender_name} has sent ${star.receiver_name} a star, saying "${star.comment}"</p>
                <ul class="tags">
                    <c:forEach var="tag" items="${star.tags}">
                        <li class="starTag">${tag}</li>
                    </c:forEach>
                </ul>
            </c:forEach>
        </div>
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
