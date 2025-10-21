package tablesDB;

import database.DB;
import tablesJava.Client;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsDB {

    public static int add(Client client) {
        var sql = "INSERT INTO public.\"Client\"(\n" +
                "\t\"IdClient\", \"Nom\", \"Prenom\", \"Adresse\", \"Email\")\n" +
                "\tVALUES (?, ?, ?, ?, ?);";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, client.getId());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getPrenom());
            pstmt.setString(4, client.getAdresse());
            pstmt.setString(5, client.getEmail());

            int insertedRow = pstmt.executeUpdate();
            if (insertedRow > 0) {
                var rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Client> findAll() {
        var clients = new ArrayList<Client>();
        var sql = "SELECT \"IdClient\", \"Nom\", \"Prenom\", \"Adresse\", \"Email\"\n" +
                "\tFROM public.\"Client\";";

        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var client = new Client(
                        rs.getInt("IdClient"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Adresse"),
                        rs.getString("Email")
                    );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public static int createNewId() {
        var sql = "SELECT MAX(\"IdClient\") AS id_max\n" +
                "\tFROM public.\"Client\";";
        int idMax = 0;

        try (var conn =  DB.connect();
             var stmt = conn.prepareStatement(sql)) {

            var rs = stmt.executeQuery();
            if (rs.next()) {
                idMax = rs.getInt("id_max");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idMax;
    }

    public static Map<String, Integer> getAllIDsClients() {
        List<Client> clients = findAll();
        Map<String, Integer> idsClients = new HashMap<>();

        for (Client client : clients) {
            int id = client.getId();
            String nom = client.getNom();
            String prenom = client.getPrenom();
            String cle = id + " (" + prenom + " " + nom + ")";
            idsClients.put(cle, id);
        }

        return idsClients;
    }

//    public static Client findById(int id){
//        var sql = "SELECT \"IdClient\", \"Nom\", \"Prenom\", \"Adresse\", \"Email\"\n" +
//                "\tFROM public.\"Client\" WHERE \"IdClient\"=?;";
//        try (var conn =  DB.connect();
//             var pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, id);
//            var rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return new Client(
//                        rs.getInt("IdClient"),
//                        rs.getString("Nom"),
//                        rs.getString("Prenom"),
//                        rs.getString("Adresse"),
//                        rs.getString("Mail")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public static int update(int id, String nom, String prenom, String adresse, String email) {
//        var sql = "UPDATE public.\"Client\"\n" +
//                "\tSET \"IdClient\"=?, \"Nom\"=?, \"Prenom\"=?, \"Adresse\"=?, \"Email\"=?\n" +
//                "\tWHERE \"IdClient\"=?;";
//
//        int affectedRows = 0;
//
//        try (var conn  = DB.connect();
//             var pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, id);
//            pstmt.setString(2, nom);
//            pstmt.setString(3, prenom);
//            pstmt.setString(4, adresse);
//            pstmt.setString(5, email);
//
//            affectedRows = pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return affectedRows;
//    }

    public static int delete(int id) {
        var sql = "DELETE FROM public.\"Client\"\n" +
                "\tWHERE \"IdClient\"=?;";
        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}