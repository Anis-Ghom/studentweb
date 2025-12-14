package org.example.webstudentbook.DAO;

import org.example.webstudentbook.models.Student;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDBUtil {
    private DataSource dataSource;
    public StudentDBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Student> getStudents() throws Exception {
        List<Student> students= new ArrayList<Student>();
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = dataSource.getConnection();
            myStmt= myConn.createStatement();
            String sql= "select * from student order by last_name";
            myRs = myStmt.executeQuery(sql);
            while(myRs.next()){
                int id = myRs.getInt("id");
                String firstName=myRs.getString("first_name");
                String lastName=myRs.getString("last_name");
                String email = myRs.getString("email");
                Student tempStudent= new Student(id,firstName,lastName,email);
                students.add(tempStudent);
            }
            return students;
        } finally{
            close(myConn,myStmt,myRs);
        }
    }

    public void addStudent(Student student){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO Student (first_name, last_name, email) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            String email = student.getEmail();
            myStmt.setString(1, firstName);
            myStmt.setString(2, lastName);
            myStmt.setString(3, email);
            myStmt.execute();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteStudent(int id){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = dataSource.getConnection();
            String sql = "DELETE FROM Student WHERE id = ?;\n";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, id);
            myStmt.execute();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }

    public Student fetchStudent(int id) {
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        Student student=null;
        try {
            myConn = dataSource.getConnection();
            myStmt= myConn.createStatement();
            String sql= "select * from student where id="+id;
            myRs = myStmt.executeQuery(sql);
            while(myRs.next()){
                String firstName=myRs.getString("first_name");
                String lastName=myRs.getString("last_name");
                String email = myRs.getString("email");
                student = new Student(id,firstName,lastName,email);
            }
            return student;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        } finally {
            close(myConn,myStmt,myRs);
        }
    }

    public void updateStudent(Student student) {
        Connection myConn=null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "update student set first_name=?, last_name=?,email=? where id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, student.getFirstName());
            myStmt.setString(2, student.getLastName());
            myStmt.setString(3, student.getEmail());
            myStmt.setInt(4,student.getId());
            myStmt.execute();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,null);
        }
    }
}
