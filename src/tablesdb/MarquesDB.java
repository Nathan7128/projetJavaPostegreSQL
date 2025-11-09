package tablesdb;

import database.DB;
import tablesjava.Marque;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarquesDB {

    public static int add(Marque marque) {
        var sql = "INSERT INTO public.\"Marque\"(\n" +
                "\t\"IdMarque\", \"Nom\", \"SiteWeb\")\n" +
                "\tVALUES (?, ?, ?);";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, marque.getId());
            pstmt.setString(2, marque.getNom());
            pstmt.setString(3, marque.getSiteWeb());

            int insertedRow = pstmt.executeUpdate();
            if (insertedRow > 0) {
                var rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Marque> findAll() {
        var marques = new ArrayList<Marque>();
        var sql = "SELECT \"IdMarque\", \"Nom\", \"SiteWeb\"\n" +
                "\tFROM public.\"Marque\";";

        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var marque = new Marque(
                        rs.getInt("IdMarque"),
                        rs.getString("Nom"),
                        rs.getString("SiteWeb")
                );
                marques.add(marque);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marques;
    }

    public static int createNewId() {
        var sql = "SELECT MAX(\"IdMarque\") AS id_max\n" +
                "\tFROM public.\"Marque\";";
        int idMax = 0;

        try (var conn =  DB.connect();
             var stmt = conn.prepareStatement(sql)) {

            var rs = stmt.executeQuery();
            if (rs.next()) {
                idMax = rs.getInt("id_max") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idMax;
    }

    public static Map<String, Integer> getAllIDsMarques() {
        List<Marque> marques = findAll();
        Map<String, Integer> idsMarques = new HashMap<>();

        for (Marque marque : marques) {
            int id = marque.getId();
            String nom = marque.getNom();
            String cle = id + " - " + nom;
            idsMarques.put(cle, id);
        }

        return idsMarques;
    }

    public static Marque findById(int id){
        var sql = "SELECT \"IdMarque\", \"Nom\", \"SiteWeb\"\n" +
                "\tFROM public.\"Marque\" WHERE \"IdMarque\"=?;";
        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Marque(
                        rs.getInt("IdMarque"),
                        rs.getString("Nom"),
                        rs.getString("SiteWeb")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int update(int id, String nom, String siteWeb) {
        var sql = "UPDATE public.\"Marque\"\n" +
                "\tSET \"IdMarque\"=?, \"Nom\"=?, \"SiteWeb\"=?\n" +
                "\tWHERE \"IdMarque\"=?;";

        int affectedRows = 0;

        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nom);
            pstmt.setString(3, siteWeb);
            pstmt.setInt(4, id);

            affectedRows = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int delete(int id) {
        var sql = "DELETE FROM public.\"Marque\"\n" +
                "\tWHERE \"IdMarque\"=?;";
        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}