<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
<input type="hidden" id="status" name="status" value="<%=session.getAttribute("status")%>"/>
<input type="hidden" id="errorMessage" name="errorMessage" value="<%=session.getAttribute("message")%>"/>

<div class="wrapper">
    <div class="title"><span>Login Form</span></div>

    <form action="login" method="post" id="loginForm">
        <div>
            <p id="login-error-msg" style="color: red;"></p>
        </div>
        <div class="row">
            <i class="fas fa-user"></i>
            <input type="text" placeholder="username" id="login" name="login" required/>
        </div>
        <div class="row">
            <i class="fas fa-lock"></i>
            <input type="password" placeholder="Password" id="password" name="password" required/>
        </div>
        <div class="row button">
            <input type="submit" value="Login"/>
        </div>
        <div class="signup-link">Not a member? <a href="${pageContext.request.contextPath}/signup.jsp">Signup now</a>
        </div>
    </form>
</div>

<script type="text/javascript">

    document.getElementById("loginForm").addEventListener("submit", function(e) {
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
