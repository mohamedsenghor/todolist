<%
    if (session.getAttribute("login") == null) {
        response.sendRedirect("login.jsp");
    }
%>

<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My TodoLists</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<div id="main">
    <button id="open-nav" onclick="openNav()">☰</button>

    <div id="mySidebar" class="sidebar">
        <h1 id="app-name">To do List</h1>
        <button type="button" id="close-nav" onclick="closeNav()">×</button>
        <form action="todolists" method="post" id="todolist-form">
            <input type="text" name="title" id="title" placeholder="Enter the title of the todolist"><br>
            <input type="submit" value="Add" id="add-todolist"/>
        </form>
        <div id="todoListsContainer">
            
        </div>

    <%--        <button type="button" class="todolist" id="todolist-1">Wake Up</button>--%>
    </div>

</div>

<script>
    function openNav() {
        document.getElementById("mySidebar").style.width = "250px";
        document.getElementById("main").style.marginLeft = "250px";
        document.getElementById("openbtn").style.display = "none";
    }

    function closeNav() {
        document.getElementById("mySidebar").style.width = "0";
        document.getElementById("main").style.marginLeft = "0";
        document.getElementById("openbtn").style.display = "block";
    }
</script>

<script>
    // document.getElementsByTagName('h1')[0].remove();
    function fetchTodoLists() {
        fetch("${pageContext.request.contextPath}/todolists")
            .then(response => response.json())
            .then(data => {
                let container = document.getElementById("todoListsContainer");
                container.innerHTML = "";

                data.forEach(todoList => {
                    let div = document.createElement("div");
                    div.classList.add("todolist");
                    div.innerHTML = `<h3>TodoList #${todoList.todolistId}</h3><ul>`;

                    todoList.tasks.forEach(task => {
                        div.innerHTML += `<li>${task.post} - <b>${task.isDone ? "Completed" : "Pending"}</b></li>`;
                    });

                    div.innerHTML += `</ul>`;
                    container.appendChild(div);
                });
            })
            .catch(error => console.error("Error fetching todolists:", error));
    }

    fetchTodoLists();
    
    document.getElementById("loginForm").addEventListener("submit", function (e) {
        e.preventDefault();

        var login = document.getElementById("login").value;
        var password = document.getElementById("password").value;

        var formData = new FormData();
        formData.append("login", login);
        formData.append("password", password);
        var params = new URLSearchParams();
        params.append("login", login);
        params.append("password", password);
        console.log("LoginServlet:", login);
        console.log("Password:", password);
        fetch("${pageContext.request.contextPath}/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params.toString()
        })
            .then(response => response.json())  // Parse the JSON response
            .then(data => {
                // Handle the response in the client-side
                if (data.status) {
                    // Success - redirect to home page or dashboard
                    alert(data.message);
                    window.location.href = "${pageContext.request.contextPath}/index.jsp";  // Redirect to home page
                } else {
                    // Failure - Show error messages
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });
</script>

</body>
</html>