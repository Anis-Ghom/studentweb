package org.example.webstudentbook.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webstudentbook.DAO.DatabaseConfig;
import org.example.webstudentbook.DAO.StudentDBUtil;
import org.example.webstudentbook.models.Student;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;


@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    private StudentDBUtil studentDbUtil;
    private DataSource dataSource;


    private DataSource getDataSource() throws NamingException {
        String jndi="java:comp/env/jdbc/studentdb" ;
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup(jndi);
        return dataSource;
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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentControllerServlet() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            listStudents(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Student> students = studentDbUtil.getStudents();
        request.setAttribute("STUDENT_LIST", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fn = req.getParameter("firstName");
        String ln = req.getParameter("lastName");
        String email = req.getParameter("email");
        Student student = new Student(fn, ln, email);
        studentDbUtil.addStudent(student);
        try {
            listStudents(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

