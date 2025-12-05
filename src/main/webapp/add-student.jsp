<%--
  Created by IntelliJ IDEA.
  User: anisa
  Date: 30/11/2025
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add a Student</title>
  <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
  <div id="header"><h2>Add New Student</h2></div>
  <div id="container">
    <form action="StudentControllerServlet" method="post">
      <table>
        <tr><td><label>FirstName:</label></td><td><input type="text" name="firstName" required/></td></tr>
        <tr><td><label>LastName:</label></td><td><input type="text" name="lastName" required/></td></tr>
        <tr><td><label>Email:</label></td><td><input type="email" name="email" required/></td></tr>
        <tr><td></td><td><input type="submit" value="Save"/></td></tr>
      </table>
    </form>
    <div style="margin-top:10px;"><a href="StudentControllerServlet">Back to List</a></div>
  </div>
</div>
</body>
</html>
