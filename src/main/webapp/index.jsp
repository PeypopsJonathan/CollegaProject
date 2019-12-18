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

    <h3>Give Star</h3>
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
        <p>you have ${availableStars} left to give this month</p>
        <form novalidate="novalidate" action="/Controller?command=Index&isForm=yes" method="post">
            <div class="autocomplete">
                <input id="receiverName" type="text" name="receiverName" placeholder="Names">
            </div>
            <h2>Choose/Enter Correct Name</h2>
            <h2 for="tags">tags</h2>

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

        function autocomplete(inp, arr) {
            /*the autocomplete function takes two arguments,
            the text field element and an array of possible autocompleted values:*/
            var currentFocus;
            /*execute a function when someone writes in the text field:*/
            inp.addEventListener("input", function(e) {
                var a, b, i, val = this.value;
                /*close any already open lists of autocompleted values*/
                closeAllLists();
                if (!val) { return false;}
                currentFocus = -1;
                /*create a DIV element that will contain the items (values):*/
                a = document.createElement("DIV");
                a.setAttribute("id", this.id + "autocomplete-list");
                a.setAttribute("class", "autocomplete-items");
                /*append the DIV element as a child of the autocomplete container:*/
                this.parentNode.appendChild(a);
                /*for each item in the array...*/
                for (i = 0; i < arr.length; i++) {
                    /*check if the item starts with the same letters as the text field value:*/
                    if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                        /*create a DIV element for each matching element:*/
                        b = document.createElement("DIV");
                        /*make the matching letters bold:*/
                        b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                        b.innerHTML += arr[i].substr(val.length);
                        /*insert a input field that will hold the current array item's value:*/
                        b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                        /*execute a function when someone clicks on the item value (DIV element):*/
                        b.addEventListener("click", function(e) {
                            /*insert the value for the autocomplete text field:*/
                            inp.value = this.getElementsByTagName("input")[0].value;
                            /*close the list of autocompleted values,
                            (or any other open lists of autocompleted values:*/
                            closeAllLists();
                        });
                        a.appendChild(b);
                    }
                }
            });
            /*execute a function presses a key on the keyboard:*/
            inp.addEventListener("keydown", function(e) {
                var x = document.getElementById(this.id + "autocomplete-list");
                if (x) x = x.getElementsByTagName("div");
                if (e.keyCode == 40) {
                    /*If the arrow DOWN key is pressed,
                    increase the currentFocus variable:*/
                    currentFocus++;
                    /*and and make the current item more visible:*/
                    addActive(x);
                } else if (e.keyCode == 38) { //up
                    /*If the arrow UP key is pressed,
                    decrease the currentFocus variable:*/
                    currentFocus--;
                    /*and and make the current item more visible:*/
                    addActive(x);
                } else if (e.keyCode == 13) {
                    /*If the ENTER key is pressed, prevent the form from being submitted,*/
                    e.preventDefault();
                    if (currentFocus > -1) {
                        /*and simulate a click on the "active" item:*/
                        if (x) x[currentFocus].click();
                    }
                }
            });
            function addActive(x) {
                /*a function to classify an item as "active":*/
                if (!x) return false;
                /*start by removing the "active" class on all items:*/
                removeActive(x);
                if (currentFocus >= x.length) currentFocus = 0;
                if (currentFocus < 0) currentFocus = (x.length - 1);
                /*add class "autocomplete-active":*/
                x[currentFocus].classList.add("autocomplete-active");
            }
            function removeActive(x) {
                /*a function to remove the "active" class from all autocomplete items:*/
                for (var i = 0; i < x.length; i++) {
                    x[i].classList.remove("autocomplete-active");
                }
            }
            function closeAllLists(elmnt) {
                /*close all autocomplete lists in the document,
                except the one passed as an argument:*/
                var x = document.getElementsByClassName("autocomplete-items");
                for (var i = 0; i < x.length; i++) {
                    if (elmnt != x[i] && elmnt != inp) {
                        x[i].parentNode.removeChild(x[i]);
                    }
                }
            }
            /*execute a function when someone clicks in the document:*/
            document.addEventListener("click", function (e) {
                closeAllLists(e.target);
            });
        }

        /*An array containing all the country names in the world:*/
        var names = [];
        names = ${listName};
        console.log(names);
        //var names = [Roshan, "Pim", "Niels", "Daan", "Bubba"];
        /*initiate the autocomplete function on the "myInput" element, and pass along the countries array as possible autocomplete values:*/
        autocomplete(document.getElementById("receiverName"), names);
    </script>


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
