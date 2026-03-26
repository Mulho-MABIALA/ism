import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Utilitaire de création de la base de données et de la table etudiant.
 * À exécuter une seule fois avant de lancer l'application principale.
 */
public class SetupDatabase {

    private static final String URL_NO_DB = "jdbc:mariadb://127.0.0.1:3306/?connectTimeout=5000&socketTimeout=10000";
    private static final String USER      = "root";
    private static final String PASSWORD  = "";  // Modifier si votre root MySQL a un mot de passe

    public static void main(String[] args) {
        System.out.println("=== Initialisation de la base de données ===");
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL_NO_DB, USER, PASSWORD);
                 Statement  stmt = conn.createStatement()) {

                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS gestion_etudiants "
                        + "CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
                System.out.println("[OK] Base de données 'gestion_etudiants' créée (ou déjà existante).");

                stmt.executeUpdate("USE gestion_etudiants");

                stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS etudiant (" +
                    "  id      INT          NOT NULL AUTO_INCREMENT," +
                    "  nom     VARCHAR(100) NOT NULL," +
                    "  prenom  VARCHAR(100) NOT NULL," +
                    "  email   VARCHAR(150)," +
                    "  filiere VARCHAR(100) NOT NULL," +
                    "  niveau  VARCHAR(20)  NOT NULL," +
                    "  CONSTRAINT pk_etudiant PRIMARY KEY (id)," +
                    "  CONSTRAINT uq_email    UNIQUE (email)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci"
                );
                System.out.println("[OK] Table 'etudiant' créée (ou déjà existante).");

                // Données de test (ignore les doublons email)
                String[] inserts = {
                    "INSERT IGNORE INTO etudiant (nom,prenom,email,filiere,niveau) VALUES ('Dupont','Alice','alice.dupont@email.com','Informatique','L2')",
                    "INSERT IGNORE INTO etudiant (nom,prenom,email,filiere,niveau) VALUES ('Martin','Bob','bob.martin@email.com','Mathématiques','L3')",
                    "INSERT IGNORE INTO etudiant (nom,prenom,email,filiere,niveau) VALUES ('Leroy','Claire','claire.leroy@email.com','Informatique','M1')",
                    "INSERT IGNORE INTO etudiant (nom,prenom,email,filiere,niveau) VALUES ('Moreau','David','david.moreau@email.com','Physique','L1')",
                    "INSERT IGNORE INTO etudiant (nom,prenom,email,filiere,niveau) VALUES ('Bernard','Emma','emma.bernard@email.com','Informatique','M2')",
                    "INSERT IGNORE INTO etudiant (nom,prenom,email,filiere,niveau) VALUES ('Petit','François','francois.petit@email.com','Économie','L3')"
                };
                int inserted = 0;
                for (String sql : inserts) {
                    inserted += stmt.executeUpdate(sql);
                }
                System.out.println("[OK] " + inserted + " étudiant(s) de test insérés.");
                System.out.println("\nBase de données prête. Vous pouvez lancer l'application.");
            }
        } catch (Exception e) {
            System.err.println("[ERREUR] " + e.getMessage());
            System.err.println("Vérifiez que XAMPP MySQL est démarré et que le mot de passe root est correct.");
            System.exit(1);
        }
    }
}
