package com.anisa.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    private DataSource dataSource;
    private DataSource getDataSource() throws NamingException {
        String jndi="java:comp/env/jdbc/studentdb";
        Context context = new InitialContext();
        return (DataSource) context.lookup(jndi);
    }

    @Override
    public void init() {
        try { dataSource = getDataSource(); } catch (NamingException e) { throw new RuntimeException(e); }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter();
             Connection myConn = dataSource.getConnection();
             Statement myStmt = myConn.createStatement();
             ResultSet myRs = myStmt.executeQuery("select email from student")) {
            while (myRs.next()) {
                out.println(myRs.getString("email"));
            }
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }
}

