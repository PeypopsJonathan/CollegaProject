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
    <title>Profile</title>
</head>
<body onload="addActiveClassToCurrentButton()">

<!-- Navigation -->
<%@ include file="components/navigation.jspf" %>

<!-- Page Content -->
<main class="container">

    <section class="profileOverview">
        <p class="profileOverviewImage"><img id="profilePicture" src="static/images/salesman.png"></p>
        <h1 class="profileOverviewItem">${firstname} ${lastname}<br></h1>
        <p class="profileOverviewItem">${email}</p>
        <p class="profileOverviewItem2" style="text-align: center">Stars available: ${availableStars}</p>
        <button class="btn filterLeft" onclick="filterSelection('all')">No filter (${totalStars})</button>
        <button class="btn filterMiddle" onclick="filterSelection('receivedStar')">Filter on stars I received
            (${receivedStars})
        </button>
        <button class="btn filterRight" onclick="filterSelection('givenStar')">Filter on stars I sent (${givenStars})
        </button>
        <div class="profileStarOverview">
            <c:forEach var="star" items="${stars}">
                <c:choose>
                    <c:when test="${star.starWasReceivedBy(userId)}">
                        <div class="starDiv receivedStar">
                            <p class="starText receivedStar"> <b>${star.sender_name}</b> has sent <b>${star.receiver_name}</b> a star,
                                saying: <br/>  <i> "${star.comment}"</i></p>
                            <ul class="tags receivedStar">
                                <c:forEach var="tag" items="${star.tags}">
                                    <li class="starTag">${tag}</li>
                                </c:forEach>
                                    <li class="starTag">${star.timestamp}</li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="starDiv givenStar">
                            <p class="starText givenStar"><b>${star.sender_name}</b> has sent <b>${star.receiver_name} </b>a
                                star, saying: </br> <i>"${star.comment}"</i></p>
                            <ul class="tags givenStar">
                                <c:forEach var="tag" items="${star.tags}">
                                    <li class="starTag">${tag}</li>
                                </c:forEach>
                                    <li class="starTag">${star.timestamp}</li>
                            </ul>
                        </div>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </div>


        <script>
            filterSelection("all")

            function filterSelection(c) {
                var x, i, y;
                x = document.getElementsByClassName("starDiv");
                if (c == "all") c = "";
                for (i = 0; i < x.length; i++) {
                    removeFilterClass(x[i], "noshow");
                    if (!(x[i].className.indexOf(c) > -1)) {
                        addFilterClass(x[i], "noshow");
                    }
                }
            }

            function addFilterClass(element, name) {
                var i, arr1, arr2;
                arr1 = element.className.split(" ");
                arr2 = name.split(" ");
                for (i = 0; i < arr2.length; i++) {
                    if (arr1.indexOf(arr2[i]) == -1) {
                        element.className += " " + arr2[i];
                    }
                }
            }

            function removeFilterClass(element, name) {
                var i, arr1, arr2;
                arr1 = element.className.split(" ");
                arr2 = name.split(" ");
                for (i = 0; i < arr2.length; i++) {
                    while (arr1.indexOf(arr2[i]) > -1) {
                        arr1.splice(arr1.indexOf(arr2[i]), 1);
                    }
                }
                element.className = arr1.join(" ");
            }

            function addActiveClassToCurrentButton() {
                // Add active class to the current button (highlight it)
                var buttonContainer = document.getElementById("filterContainer");
                var buttons = buttonContainer.getElementsByClassName("btn");
                for (var i = 0; i < buttons.length; i++) {
                    buttons[i].addEventListener("click", function () {
                        var current = document.getElementsByClassName("active");
                        for (var j = 0; j < current.length; j++) {
                            current[j].classList.remove("active");
                        }

                        event.srcElement.classList.add("active")
                    });
                }
            }


        </script>
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
