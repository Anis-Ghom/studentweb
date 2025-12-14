package org.example.webstudentbook.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/AddStudentServlet")
public class AddStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérifier si l'utilisateur est connecté et est instructor
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"instructor".equals(role)) {
            response.sendRedirect("StudentControllerServlet");
            return;
        }

        request.getRequestDispatcher("/add-student.jsp").forward(request, response);
    }
}