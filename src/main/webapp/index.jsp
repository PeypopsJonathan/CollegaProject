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
    <title>Stargazing</title>
</head>
<body>

<!-- Navigation -->
<%@ include file="components/navigation.jspf" %>

<!-- Page Content -->


<main role="main" class="container">


    <c:if test="${!empty errors}">
        <div class="alert-danger">
            <c:forEach var="error" items="${errors}">
                <li>
                    <c:out value="${error}"></c:out>
                </li>
            </c:forEach>
        </div>
    </c:if>
    <h1>Stargazing</h1>
    <section class="starOverview">

        <form method="post" action="Controller?command=Index&iWantFilter=yes" autocomplete="off">

            <select id="selectFilter" name="tagss" id="tagss">
                <option>--Select a tag--</option>
                <option>All Tags</option>

                <c:forEach var="tag" items="${tags}">
                    <option name="<c:out value="${tag}"></c:out>" class="tagOptions"
                            value="<c:out value="${tag}"></c:out>">
                        <c:out value="${tag}"></c:out>
                    </option>
                </c:forEach>

                <article class="autocomplete">
                    <input id="receiverNameFilter" type="text" name="receiverNameFilter" placeholder="Names">
                </article>
                <input type="submit" value="filter">
            </select>
        </form>

            <article>
                <c:forEach var="star" items="${stars}">
                    <p class="starText"> <b>${star.sender_name}</b> has sent <b>${star.receiver_name}</b> a star, saying:<br/><i>"${star.comment}"</i></p>
                    <ul class="tags">
                        <c:forEach var="tag" items="${star.tags}">
                            <li class="starTag">${tag}</li>
                        </c:forEach>
                            <li class="starTag">${star.timestamp}</li>
                    </ul>
                </c:forEach>
            </article>
        </section>
    </section>

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
