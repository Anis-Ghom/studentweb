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
  <title>Web Student Tracker</title>
  <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
  <div id="header"><h2>List of Students</h2></div>
  <div id="content">
    <form action="AddStudentServlet" method="get" style="margin-bottom:10px;">
      <input type="submit" value="Add Student"/>
    </form>
    <table border="1" cellpadding="6" cellspacing="0">
      <tr>
        <th>First Name</th><th>Last Name</th><th>Email</th>
      </tr>
      <c:forEach var="tempStudent" items="${STUDENT_LIST}">
        <tr>
          <td>${tempStudent.firstName}</td>
          <td>${tempStudent.lastName}</td>
          <td>${tempStudent.email}</td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>
</body>
</html>
