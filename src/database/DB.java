package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DB {
    private static HikariDataSource sourceDonnees;

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

    public static Connection connect() throws Exception {
        return sourceDonnees.getConnection();
    }

    public static void deconnect() {
        if (sourceDonnees != null) {
            sourceDonnees.close();
        }
    }
}