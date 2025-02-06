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

    <form action="login" method="post" id="login-form">
        <div>
            <p id="login-error-msg" style="color: red;"></p>
        </div>
        <div class="row">
            <i class="fas fa-user"></i>
            <input type="text" placeholder="username" id="login-input" name="login" required/>
        </div>
        <div class="row">
            <i class="fas fa-lock"></i>
            <input type="password" placeholder="Password" id="password-input" name="password" required/>
        </div>
        <div class="row button">
            <input type="submit" value="Login"/>
        </div>
        <div class="signup-link">Not a member? <a href="${pageContext.request.contextPath}/signup.jsp">Signup now</a>
        </div>
    </form>
</div>

<script type="text/javascript">
    const status = document.getElementById('status').value || '';
    let errorMessage = document.getElementById('errorMessage').value;
    if (errorMessage)
        errorMessage = " ";
    console.log(errorMessage);
    console.log(status);
    const errorNode = document.getElementById('login-error-msg');
    errorNode.textContent = errorMessage;
</script>
</body>
</html>
