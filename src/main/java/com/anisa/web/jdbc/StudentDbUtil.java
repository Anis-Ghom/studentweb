package com.anisa.web.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class StudentDbUtil {

    private DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();
            myStmt = myConn.createStatement();
            String sql = "select * from student order by last_name";
            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                int id = myRs.getInt("id");
                String firstName = myRs.getString("first_name");
                String lastName  = myRs.getString("last_name");
                String email     = myRs.getString("email");

                Student tempStudent = new Student(id, firstName, lastName, email);
                students.add(tempStudent);
            }

            return students;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addStudent(Student student) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO student (first_name, last_name, email) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, student.getFirstName());
            myStmt.setString(2, student.getLastName());
            myStmt.setString(3, student.getEmail());
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null)    myRs.close();
            if (myStmt != null)  myStmt.close();
            if (myConn != null)  myConn.close(); // retourne la connexion au pool
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
