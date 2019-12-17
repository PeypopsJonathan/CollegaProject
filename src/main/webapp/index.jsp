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

    <h3>Geef Ster</h3>
    <c:if test="${!empty errors}">
        <div class="alert-danger">
            <c:forEach var="error" items="${errors}">
                <li>
                    <c:out value="${error}"></c:out>
                </li>
            </c:forEach>
        </div>
    </c:if>
    <div class="containerGiveStar">
        <form novalidate="novalidate" action="/Controller?command=GiveStar" method="post">
            <label for="receiver">receiver</label>
            <input type="text" id="receiver" name="receiver" placeholder="enter receiver id"
                   value="${previous_input_receiver}">
            <label for="tags">tags</label>

            <select id="select" name="tags" id="tags" onchange="addTag()">
                <option></option>
                <c:forEach var="tag" items="${tags}">
                    <option class="tagOptions" value="<c:out value="${tag}"></c:out>">
                        <c:out value="${tag}"></c:out>
                    </option>
                </c:forEach>
            </select>

            <p id="tagsAdded">
            <h2>Tags Added: </h2>
            </p>

            <input type="hidden" class="What a great worker." name="0" id="0">
            <input type="hidden" class="Awesome stuff." name="1" id="1">
            <input type="hidden" class="Have my children." name="2" id="2">
            <input type="hidden" class="Happy with your service." name="3" id="3">

            <label for="description" value="${previous_input_description}">description</label>
            <textarea name="description" id="description" cols="30" rows="10" placeholder="enter decription"></textarea>

            <input type="submit" value="submit">
        </form>

    </div>
    <script>
        window.addEventListener("load", initPage, false);

        var counterList;

        function initPage() {
            counterList = 0;
        }

        function addTag() {

            if (counterList > 3) {

            } else {
                var currentSelection = document.getElementById("select").value

                var button = document.createElement("button");
                button.innerHTML = currentSelection + "(Click to remove)"
                button.id = currentSelection;

                var hiddenTags = document.getElementsByClassName(currentSelection);
                hiddenTags[0].value = currentSelection;

                button.addEventListener("click", function () {
                    document.getElementById("tagsAdded").removeChild(button);
                    var buttonIdToRemove = button.id
                    var hiddenTagToRemove = document.getElementsByClassName(buttonIdToRemove);
                    hiddenTagToRemove[0].value = "";

                    counterList--;
                }, false);

                counterList++;
                document.getElementById("tagsAdded").appendChild(button)
            }
        }

    </script>
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
