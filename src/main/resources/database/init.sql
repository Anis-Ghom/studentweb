-- ====================================
-- Script d'initialisation de la base de données
-- Application: WebStudentBook
-- ====================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS studentdb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Sélectionner la base de données
USE studentdb;

-- Supprimer la table si elle existe (pour réinitialisation)
DROP TABLE IF EXISTS student;

-- Créer la table student
CREATE TABLE student (
                         id INT NOT NULL AUTO_INCREMENT,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         PRIMARY KEY (id),
                         INDEX idx_lastname (last_name),
                         INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insérer des données de test
INSERT INTO student (first_name, last_name, email) VALUES
                                                       ('John', 'Doe', 'john.doe@esilv.fr'),
                                                       ('Jane', 'Smith', 'jane.smith@esilv.fr'),
                                                       ('Alice', 'Johnson', 'alice.johnson@esilv.fr'),
                                                       ('Bob', 'Williams', 'bob.williams@esilv.fr'),
                                                       ('Charlie', 'Brown', 'charlie.brown@esilv.fr'),
                                                       ('Diana', 'Davis', 'diana.davis@esilv.fr');

-- Configuration du fuseau horaire (résout les problèmes de timezone)
SET GLOBAL time_zone = '+00:00';
SET SESSION time_zone = '+00:00';

-- Vérifier que tout s'est bien passé
SELECT 'Base de données initialisée avec succès!' AS Status;
SELECT COUNT(*) AS 'Nombre d\'étudiants' FROM student;
SELECT * FROM student ORDER BY last_name;