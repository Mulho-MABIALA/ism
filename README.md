# Gestion des Étudiants – JavaFX + JDBC + MySQL/MariaDB

Application JavaFX de gestion d'étudiants avec base de données MySQL/MariaDB via JDBC.

## Architecture du projet

```
projet_javafx_gestion_etudiants/
├── pom.xml                            ← Configuration Maven
├── sql/
│   └── gestion_etudiants.sql          ← Script de création BDD + données test
└── src/
    ├── Main.java                      ← Point d'entrée (Application JavaFX)
    ├── SetupDatabase.java             ← Utilitaire d'initialisation BDD (optionnel)
    ├── model/
    │   └── Etudiant.java              ← Classe métier (POJO)
    ├── dao/
    │   └── EtudiantDAO.java           ← Accès base de données (CRUD)
    ├── database/
    │   └── DatabaseConnection.java    ← Singleton de connexion JDBC
    ├── controller/
    │   └── EtudiantController.java    ← Contrôleur JavaFX (logique UI)
    └── view/
        └── etudiant.fxml              ← Interface graphique (FXML)
```

## Explication de l'architecture (MVC + DAO)

| Couche | Rôle |
|--------|------|
| **Model** (`Etudiant.java`) | Représente un étudiant avec ses attributs. POJO pur, sans logique métier. |
| **DAO** (`EtudiantDAO.java`) | Isole toutes les requêtes SQL. Implémente les 4 opérations CRUD. |
| **Database** (`DatabaseConnection.java`) | Singleton qui gère la connexion JDBC. Évite d'ouvrir plusieurs connexions. |
| **Controller** (`EtudiantController.java`) | Fait le lien entre la vue FXML et le DAO. Gère les événements boutons, la validation, le TableView. |
| **View** (`etudiant.fxml`) | Décrit l'interface graphique en XML (BorderPane, TableView, formulaire). |

## Prérequis

- Java 17 ou plus récent
- Maven 3.6+
- **MySQL** ou **MariaDB** (XAMPP inclut MariaDB)

## Mise en route

### 1. Créer la base de données

**Option A — depuis MySQL Workbench, HeidiSQL ou phpMyAdmin :**
```sql
-- Exécuter le fichier sql/gestion_etudiants.sql
```

**Option B — depuis le terminal MySQL :**
```bash
mysql -u root -p < sql/gestion_etudiants.sql
```

**Option C — via Maven (connexion JDBC directe) :**
```bash
mvn compile exec:java -Dexec.mainClass=SetupDatabase
```

### 2. Configurer la connexion

Éditer `src/database/DatabaseConnection.java` selon votre configuration :
```java
private static final String URL      = "jdbc:mariadb://127.0.0.1:3306/gestion_etudiants?connectTimeout=5000";
private static final String USER     = "root";      // votre utilisateur MySQL/MariaDB
private static final String PASSWORD = "";           // votre mot de passe (vide par défaut sur XAMPP)
```

> Le driver `mariadb-java-client` est compatible aussi bien avec **MySQL** qu'avec **MariaDB**.

### 3. Compiler et lancer

```bash
mvn clean javafx:run
```

## Fonctionnalités

- **Ajouter** un étudiant via le formulaire (Enregistrer)
- **Afficher** tous les étudiants dans le TableView
- **Modifier** un étudiant sélectionné (clic ligne → modifier les champs → Enregistrer)
- **Supprimer** un étudiant avec confirmation
- **Rechercher** par nom, prénom ou filière
- **Actualiser** la liste depuis la base de données
- **Validation** des champs obligatoires et format email

## Technologies utilisées

- Java 17
- JavaFX 21
- JDBC (driver MariaDB 3.3.3 — compatible MySQL et MariaDB)
- MySQL / MariaDB (XAMPP)
- Maven 3
