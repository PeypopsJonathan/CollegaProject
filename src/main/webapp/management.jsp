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
    <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
    <title>Management</title>
</head>
<body style="font-family: 'Montserrat', sans-serif">

<!-- Navigation -->
<%@ include file="components/navigation.jspf" %>

<!-- Page Content -->


<main role="main" class="container">
    <div id="errors">
        <c:forEach items="${errors}" var="error">
            <p>${error}</p>
        </c:forEach>
    </div>

    <h3>Management Actions</h3>
    <select onchange="stateChange()">
        <option value="">--Select an action--</option>
        <option value="add">Add User</option>
    </select>


    <form action="Controller?command=AddUserVerification" method="post" id="addUser" style="display: none" class="addUserForm">
        <div class="addUserDiv">
            <span id="firstname">
            <label for="firstname">First name</label>
            <input placeholder="First Name" name="firstname" type="text" value="${previousFirstname}">
            </span>


            <span id="lastname" >
            <label for="lastname">Last name</label>
            <input placeholder="Last name" name="lastname" type="text" value="${previousLastname}">
            </span>

            <span id="email">
            <label for="email">E-mail</label>
            <input placeholder="E-mail"  name="email" type="text" value="${previousEmail}">
            </span>

            <span id="role">
            <label for="role">Role</label>
            <select name="role">
                <option value="USER" <c:if test="${USER}"> selected</c:if>>User</option>
                <option value="ADMIN"<c:if test="${ADMIN}"> selected</c:if>>Admin</option>
            </select>
            </span>

            <span id="manager">
            <label for="manager">Manager</label>
            <input name="manager" type="checkbox"
                   <c:if test="${previousManager}">checked </c:if> >
            </span>

            <span id="superuser">
            <label for="superuser">Superuser</label>
            <input name="superuser" type="checkbox" <c:if test="${previousSuperuser}"> checked </c:if>>
            </span>


            <input type="submit" value="Add" id="add">
        </div>
    </form>
</main>

<!-- Footer -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Bootstrap JS -->
<script>
    function stateChange() {
        var x = document.getElementById("addUser");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }
</script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
