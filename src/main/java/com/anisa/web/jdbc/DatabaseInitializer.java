// Java
package com.anisa.web.jdbc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String SQL_SCRIPT = "database/init.sql";

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  Initialisation Base de Données       ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        DatabaseConfig.printConfig();
        initializeDatabase();
    }

    public static void initializeDatabase() {
        Connection conn = null;
        Statement stmt = null;

        try {
            System.out.println("→ Chargement du driver JDBC...");
            Class.forName(DatabaseConfig.getDriver());
            System.out.println("✓ Driver chargé\n");

            String originalUrl = DatabaseConfig.getUrl();
            String serverUrl = originalUrl; // par défaut
            boolean connected = false;

            // Tentative de connexion à l'URL configurée (peut échouer si DB inconnue)
            try {
                System.out.println("→ Tentative de connexion sur l'URL configurée...");
                conn = DriverManager.getConnection(originalUrl,
                        DatabaseConfig.getUsername(),
                        DatabaseConfig.getPassword());
                connected = true;
                System.out.println("✓ Connecté à " + originalUrl + "\n");
            } catch (SQLException e) {
                String msg = e.getMessage() == null ? "" : e.getMessage();
                System.err.println("⚠️  Connexion initiale échouée: " + msg);
                // Si la base est inconnue, on reconstruit une URL serveur sans /nom_de_la_db
                if (msg.contains("Unknown database") || e.getErrorCode() == 1049) {
                    serverUrl = originalUrl.replaceFirst("/[^/?]+", "");
                    System.out.println("→ Repli : connexion au serveur MySQL sans base: " + serverUrl);
                    conn = DriverManager.getConnection(serverUrl,
                            DatabaseConfig.getUsername(),
                            DatabaseConfig.getPassword());
                    connected = true;
                    System.out.println("✓ Connecté au serveur MySQL\n");
                } else {
                    throw e;
                }
            }

            if (!connected) {
                throw new RuntimeException("Impossible d'établir une connexion JDBC.");
            }

            // Garantir la base et sa sélection (si on est connecté au serveur, on peut créer la DB)
            stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS studentdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            stmt.execute("USE studentdb");

            System.out.println("→ Lecture du script " + SQL_SCRIPT + "...");
            InputStream is = DatabaseInitializer.class.getClassLoader()
                    .getResourceAsStream(SQL_SCRIPT);
            if (is == null) {
                throw new RuntimeException("✗ Fichier " + SQL_SCRIPT + " introuvable!");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
            reader.close();
            System.out.println("✓ Script chargé\n");

            System.out.println("→ Exécution des instructions SQL...\n");
            String[] statements = sqlBuilder.toString().split(";");
            int executedCount = 0;
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("--")) continue;
                try {
                    stmt.execute(trimmed);
                    executedCount++;
                    String preview = trimmed.length() > 60 ? trimmed.substring(0, 60) + "..." : trimmed;
                    System.out.println("  ✓ " + preview.replaceAll("\\s+", " "));
                } catch (Exception e) {
                    System.err.println("  ✗ Erreur (continuer): " + e.getMessage());
                }
            }

            System.out.println("\n✓ " + executedCount + " instructions exécutées avec succès!");
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║  Base de données prête à l'emploi! ✓  ║");
            System.out.println("╚════════════════════════════════════════╝");

        } catch (ClassNotFoundException e) {
            System.err.println("✗ Driver JDBC introuvable: " + e.getMessage());
            System.err.println("→ Vérifier que le connector MySQL est dans le POM et le classpath.");
        } catch (Exception e) {
            System.err.println("\n✗ ERREUR lors de l'initialisation:");
            System.err.println("  " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception ignore) {}
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
        }
    }

    public static boolean testConnection() {
        try {
            Class.forName(DatabaseConfig.getDriver());
            Connection conn = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUsername(),
                    DatabaseConfig.getPassword()
            );
            conn.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
