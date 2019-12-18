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

<!-- Navigation -->
<%@ include file="components/navigation.jspf" %>

<!-- Page Content -->


<main role="main" class="container">


    <div class="containerGiveStar">
        <h3>Give Star</h3>
        <form action="/Controller?command=giveStar" POST>
            <label for="receiver">receiver</label>
            <input type="text" id="receiver" name="receiver" placeholder="enter receiver id">
            <label for="tags">tags</label>
            <select name="tags" id="tags">
                <option value="great">great</option>
                <option value="awesome">awesome</option>
                <option value="too bad">too bad</option>
            </select>

            <label for="description">description</label>
            <textarea name="description" id="description" cols="30" rows="5" placeholder="">description</textarea>

            <input type="submit" value="submit">
        </form>


    </div>

    <div>
        <c:forEach var="star" items="${stars}">
            <p class="starText">${star.sender_name} has sent ${star.receiver_name} a star, saying "${star.comment}"</p>
            <ul class="tags">
                <c:forEach var="tag" items="${star.tags}">
                    <li class="starTag">${tag}</li>
                </c:forEach>
            </ul>
        </c:forEach>
    </div>

</main>
</body>
<!-- Footer -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
