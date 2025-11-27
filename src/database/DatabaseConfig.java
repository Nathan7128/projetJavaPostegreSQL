package database;

// Importation des bibliothèques externes
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Classe permettant de configurer la base de données PostgreSQL
 */
public class DatabaseConfig {

    private static final Properties proprietes = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("database/db.proprietes")) {
            if (input == null) {
                System.out.println("Erreur, impossible de trouver db.proprietes");
                System.exit(1);
            }
            // Lire le fichier des propriétés
            proprietes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getDbUrl() {
        return proprietes.getProperty("db.url");
    }

    public static String getDbUsername() {
        return proprietes.getProperty("db.username");
    }

    public static String getDbPassword() {
        return proprietes.getProperty("db.password");
    }
}