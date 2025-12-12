package com.anisa.web.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DatabaseConfig {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    // Chargement statique des propriétés au démarrage
    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                System.err.println("⚠️  Fichier " + CONFIG_FILE + " introuvable!");
                System.err.println("→ Copier config.properties.example vers config.properties");
                throw new RuntimeException("Fichier de configuration introuvable: " + CONFIG_FILE);
            }

            properties.load(input);
            System.out.println("✓ Configuration chargée depuis " + CONFIG_FILE);

        } catch (IOException e) {
            System.err.println("✗ Erreur lors du chargement de " + CONFIG_FILE);
            throw new RuntimeException("Erreur de chargement de la configuration", e);
        }
    }

    /**
     * Récupère le driver JDBC
     */
    public static String getDriver() {
        return properties.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
    }

    /**
     * Récupère l'URL de connexion à la base de données
     */
    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    /**
     * Récupère le nom d'utilisateur de la base de données
     */
    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    /**
     * Récupère le mot de passe de la base de données
     */
    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    /**
     * Récupère le nom JNDI de la ressource
     */
    public static String getJndiName() {
        return properties.getProperty("db.jndi.name", "jdbc/studentdb");
    }

    /**
     * Récupère le nombre maximum de connexions dans le pool
     */
    public static int getMaxTotal() {
        return Integer.parseInt(properties.getProperty("db.pool.maxTotal", "20"));
    }

    /**
     * Récupère le nombre maximum de connexions inactives
     */
    public static int getMaxIdle() {
        return Integer.parseInt(properties.getProperty("db.pool.maxIdle", "5"));
    }

    /**
     * Récupère le temps d'attente maximum pour obtenir une connexion (en ms)
     */
    public static int getMaxWaitMillis() {
        return Integer.parseInt(properties.getProperty("db.pool.maxWaitMillis", "10000"));
    }

    /**
     * Affiche toutes les propriétés (masque le mot de passe)
     */
    public static void printConfig() {
        System.out.println("\n=== Configuration Base de Données ===");
        System.out.println("Driver    : " + getDriver());
        System.out.println("URL       : " + getUrl());
        System.out.println("Username  : " + getUsername());
        System.out.println("Password  : " + maskPassword(getPassword()));
        System.out.println("JNDI Name : " + getJndiName());
        System.out.println("Max Total : " + getMaxTotal());
        System.out.println("Max Idle  : " + getMaxIdle());
        System.out.println("Max Wait  : " + getMaxWaitMillis() + " ms");
        System.out.println("=====================================\n");
    }

    /**
     * Masque le mot de passe pour l'affichage
     */
    private static String maskPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "(vide)";
        }
        return "****" + password.substring(Math.max(0, password.length() - 2));
    }
}