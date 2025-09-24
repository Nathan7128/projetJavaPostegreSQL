import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}