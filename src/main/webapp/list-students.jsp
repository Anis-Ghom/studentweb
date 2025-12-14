<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Web Student Tracker</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>ESILV Engineer School</h2>
        <div style="float: right; margin-right: 20px; color: white;">
            Welcome, <strong>${sessionScope.username}</strong> (${sessionScope.role})
            | <a href="LogoutServlet" style="color: white; text-decoration: underline;">Logout</a>
        </div>
    </div>
</div>
<div id="container">
    <div id="content">

        <!-- Bouton Add Student seulement pour instructor -->
        <c:if test="${sessionScope.role == 'instructor'}">
            <form action="AddStudentServlet" method="get">
                <input type="submit" value="Add Student" class="add-button"/>
            </form>
        </c:if>

        <table>
            <tr>
                <th>First Name </th>
                <th>Last Name</th>
                <th>Email </th>
                <c:if test="${sessionScope.role == 'instructor'}">
                    <th>Action</th>
                </c:if>
            </tr>
            <c:forEach var="tempStudent" items="${STUDENT_LIST}" >
                <c:url var="EditLink" value="EditStudentServlet">
                    <c:param name="studentId" value="${tempStudent.id}"/>
                </c:url>
                <c:url var="DeleteLink" value="DeleteStudentServlet">
                    <c:param name="studentId" value="${tempStudent.id}"/>
                </c:url>
                <tr>
                    <td> ${tempStudent.firstName}</td>
                    <td> ${tempStudent.lastName}</td>
                    <td> ${tempStudent.email}</td>
                    <c:if test="${sessionScope.role == 'instructor'}">
                        <td>
                            <a href="${EditLink}">Edit</a>
                            |
                            <a href="${DeleteLink}"
                               onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>