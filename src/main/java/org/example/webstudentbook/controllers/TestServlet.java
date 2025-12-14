package org.example.webstudentbook.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webstudentbook.DAO.DatabaseConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    @Override
    public void init() {
        try {
            Class.forName(DatabaseConfig.getDriver());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Step1: set up the printwriter
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        // Step2: Get a connection to the database
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUsername(),
                    DatabaseConfig.getPassword()
            );
            // Step3: create SQL statements
            String sql = "select * from student";
            myStmt = myConn.createStatement();
            // Step4: Execute SQL query
            myRs = myStmt.executeQuery(sql);
            // Step5: Process the ResultSet
            while (myRs.next()) {
                String email = myRs.getString("email");
                out.println(email);
            }
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }
}