package com.anisa.web.jdbc;

import java.io.IOException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDbUtil studentDbUtil;
    private DataSource dataSource;

    public StudentControllerServlet() { super(); }

    private DataSource getDataSource() throws NamingException {
        String jndi = "java:comp/env/jdbc/studentdb";
        Context context = new InitialContext();
        return (DataSource) context.lookup(jndi);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dataSource = getDataSource();
            studentDbUtil = new StudentDbUtil(dataSource);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            listStudents(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Student> students = studentDbUtil.getStudents();
        request.setAttribute("STUDENT_LIST", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // traiter l'ajout d'un étudiant depuis add-student.jsp
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");

        Student newStudent = new Student(firstName, lastName, email);
        try {
            studentDbUtil.addStudent(newStudent);
            listStudents(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

