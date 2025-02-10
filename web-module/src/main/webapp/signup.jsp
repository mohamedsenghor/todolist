<%--
  Created by IntelliJ IDEA.
  User: mohamed
  Date: 30-01-2025
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Signup</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>

<input type="hidden" id="status" name="status" value="<%=session.getAttribute("status")%>"/>
<input type="hidden" id="errorMessage" name="errorMessage" value="<%=session.getAttribute("message")%>"/>

<div class="wrapper">
    <div class="title"><span>Signup Form</span></div>
    <form action="signup" method="post" id="signupForm">
        <div>
            <p id="login-error-msg" style="color: red;"></p>
        </div>
        <div class="row">
            <i class="fas fa-user"></i>
            <input type="text" placeholder="username" name="login" id="login" required/>
        </div>
        <div class="row">
            <i class="fas fa-lock"></i>
            <input type="password" placeholder="Password" name="password" id="password" required/>
        </div>
        <div class="row button">
            <input type="submit" value="Sign up"/>
        </div>
        <div class="signup-link">Already a member? <a href="${pageContext.request.contextPath}/login.jsp">Log in now</a>
        </div>
    </form>
</div>

</body>
<script type="text/javascript">
    // const status = document.getElementById('status').value || '';
    // let errorMessage = document.getElementById('errorMessage').value;
    // if (errorMessage)
    // errorMessage = " ";
    // console.log(errorMessage);
    // console.log(status);
    // const errorNode = document.getElementById('login-error-msg');
    // errorNode.textContent = errorMessage;

    <%--document.getElementById("signupForm").addEventListener("submit", function(e) {--%>
    <%--    e.preventDefault();--%>

    <%--    var login = document.getElementById("login").value;--%>
    <%--    var password = document.getElementById("password").value;--%>

    <%--    var formData = new FormData();--%>
    <%--    formData.append("login", login);--%>
    <%--    formData.append("password", password);--%>

    <%--    fetch("${pageContext.request.contextPath}/signup", {--%>
    <%--        method: "POST",--%>
    <%--        body: formData--%>
    <%--    })--%>
    <%--        .then(response => response.json())  // Parse the JSON response--%>
    <%--        .then(data => {--%>
    <%--            // Handle the response in the client-side--%>
    <%--            if (data.status) {--%>
    <%--                // Success - redirect to login or show success message--%>
    <%--                alert(data.message);--%>
    <%--                window.location.href = "/login.jsp";  // Redirect to login page--%>
    <%--            } else {--%>
    <%--                // Failure - Show error messages--%>
    <%--                alert(data.message);--%>
    <%--            }--%>
    <%--        })--%>
    <%--        .catch(error => {--%>
    <%--            console.error("Error:", error);--%>
    <%--        });--%>
    <%--});--%>

    document.getElementById("signupForm").addEventListener("submit", function(e) {
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
        fetch("${pageContext.request.contextPath}/signup", {
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
                    // Success - redirect to login page
                    alert(data.message);
                    window.location.href = "${pageContext.request.contextPath}/login.jsp";  // Redirect to home page
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
