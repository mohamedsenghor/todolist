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
    <form action="signup" method="post">
        <div>
            <p id="login-error-msg" style="color: red;"></p>
        </div>
        <div class="row">
            <i class="fas fa-user"></i>
            <input type="text" placeholder="username" name="login" required/>
        </div>
        <div class="row">
            <i class="fas fa-lock"></i>
            <input type="password" placeholder="Password" name="password" required/>
        </div>
        <div class="row button">
            <input type="submit" value="Sing up"/>
        </div>
        <div class="signup-link">Already a member? <a href="${pageContext.request.contextPath}/login.jsp">Log in now</a>
        </div>
    </form>
</div>


<script src="vendor/jquery/jquery.min.js"></script>
<script src="https://unpkg.co/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="alert/dist/sweetalert.css">

</body>
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
