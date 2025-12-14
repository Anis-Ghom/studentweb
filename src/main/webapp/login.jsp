<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/login.css">
    <title>Login - Student Management</title>
</head>
<body>
<div class="login-box">
    <h2>🎓 Student Management</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>

    <form action="LoginServlet" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username"
                   value="${savedUsername}" required autofocus>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="btn-login">Login</button>
    </form>

    <div class="info-box">
        <strong>Test accounts:</strong><br>
        Instructor: <code>instructor</code> / <code>pass123</code><br>
        Student: <code>student1</code> / <code>pass123</code>
    </div>
</div>
</body>
</html>