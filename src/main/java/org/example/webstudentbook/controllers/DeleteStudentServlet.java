package org.example.webstudentbook.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.webstudentbook.DAO.DatabaseConfig;
import org.example.webstudentbook.DAO.StudentDBUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;


@WebServlet("/DeleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDBUtil studentDbUtil;
    private DataSource dataSource;


    private DataSource getDataSource() throws NamingException {
        String jndi="java:comp/env/jdbc/studentdb" ;
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup(jndi);
        return dataSource;
    }

    public DeleteStudentServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName(DatabaseConfig.getDriver());
            this.dataSource= getDataSource();
            studentDbUtil = new StudentDBUtil(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        int id = Integer.parseInt(request.getParameter("studentId"));
        studentDbUtil.deleteStudent(id);
        response.sendRedirect("StudentControllerServlet");
    }
}