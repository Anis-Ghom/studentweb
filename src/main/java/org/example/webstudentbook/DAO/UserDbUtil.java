package org.example.webstudentbook.DAO;

import org.example.webstudentbook.models.User;

import java.sql.*;

public class UserDbUtil {

    public UserDbUtil() {
        // Constructeur vide - on utilisera des connexions directes
    }

    /**
     * Obtient une connexion à la base de données
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.getUrl(),
                DatabaseConfig.getUsername(),
                DatabaseConfig.getPassword()
        );
    }

    /**
     * Authentifie un utilisateur avec son username et password
     * @return User si authentification réussie, null sinon
     */
    public User authenticate(String username, String password) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            // Requête préparée pour éviter les injections SQL
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, username);
            myStmt.setString(2, password);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                int id = myRs.getInt("id");
                String user = myRs.getString("username");
                String pass = myRs.getString("password");
                String role = myRs.getString("role");

                return new User(id, user, pass, role);
            }

            return null; // Authentification échouée

        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    /**
     * Crée un nouvel utilisateur
     */
    public void createUser(User user) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();
            String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, user.getUsername());
            myStmt.setString(2, user.getPassword());
            myStmt.setString(3, user.getRole());
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    /**
     * Ferme les ressources JDBC
     */
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) myRs.close();
            if (myStmt != null) myStmt.close();
            if (myConn != null) myConn.close();
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
        }
    }
}