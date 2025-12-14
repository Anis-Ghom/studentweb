package org.example.webstudentbook.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.webstudentbook.DAO.DatabaseConfig;
import org.example.webstudentbook.models.User;
import org.example.webstudentbook.DAO.UserDbUtil;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDbUtil userDbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Charger le driver MySQL
            Class.forName(DatabaseConfig.getDriver());

            // Créer le UserDbUtil avec une connexion directe
            userDbUtil = new UserDbUtil();

            System.out.println("✓ LoginServlet initialisé avec succès");
        } catch (Exception e) {
            System.err.println("✗ Erreur lors de l'initialisation de LoginServlet");
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Afficher la page de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDbUtil.authenticate(username, password);

            if (user != null) {
                // Authentification réussie
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());

                System.out.println("✓ Utilisateur connecté: " + user.getUsername());

                // Rediriger vers la liste des étudiants
                response.sendRedirect("StudentControllerServlet");
            } else {
                // Authentification échouée
                System.out.println("✗ Échec de connexion pour: " + username);
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.err.println("✗ Erreur lors de l'authentification: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}