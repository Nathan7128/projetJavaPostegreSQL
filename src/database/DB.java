package database;

// Importation des bibliothèques externes
import java.sql.Connection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Classe permettant la connection avec la base de données PostgreSQL
 * La base de données est associée à une instance DatabaseConfig qui contient ses propriétés
 */
public class DB {

    private static HikariDataSource sourceDonnees;


    /**
     * Initialise la bdd en la configurant via les propriétés spécifiées
     */
    public static void init() {
        var jdbcUrl = DatabaseConfig.getDbUrl();
        var user = DatabaseConfig.getDbUsername();
        var password = DatabaseConfig.getDbPassword();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        sourceDonnees = new HikariDataSource(config);
    }

    /**
     * Retourne une connexion à la bdd
     */
    public static Connection connect() throws Exception {
        return sourceDonnees.getConnection();
    }

    /**
     * Déconnecte la source de données de la bdd
     */
    public static void deconnect() {
        if (sourceDonnees != null) {
            sourceDonnees.close();
        }
    }
}