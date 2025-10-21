package tablesDB;

import database.DB;
import tablesJava.Modele;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelesDB {

    public static int add(Modele modele) {
        var sql = "INSERT INTO public.\"Modele\"(\n" +
                "\t\"IdModele\", \"IdMarque\", \"Nom\")\n" +
                "\tVALUES (?, ?, ?);";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, modele.getId());
            pstmt.setInt(2, modele.getIdMarque());
            pstmt.setString(3, modele.getNom());

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

    public static List<Modele> findAll() {
        var modeles = new ArrayList<Modele>();
        var sql = "SELECT \"IdModele\", \"IdMarque\", \"Nom\"\n" +
                "\tFROM public.\"Modele\";";

        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var modele = new Modele(
                        rs.getInt("IdModele"),
                        rs.getInt("IdMarque"),
                        rs.getString("Nom")
                );
                modeles.add(modele);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modeles;
    }

    public static int createNewId() {
        var sql = "SELECT MAX(\"IdModele\") AS id_max\n" +
                "\tFROM public.\"Modele\";";
        int idMax = 0;

        try (var conn =  DB.connect();
             var stmt = conn.prepareStatement(sql)) {

            var rs = stmt.executeQuery();
            if (rs.next()) {
                idMax = rs.getInt("id_max") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idMax;
    }

    public static Map<String, Integer> getAllIDsModeles() {
        List<Modele> modeles = findAll();
        Map<String, Integer> idsModeles = new HashMap<>();

        for (Modele modele : modeles) {
            int id = modele.getId();
            String nom = modele.getNom();
            String cle = id + " (" + nom + ")";
            idsModeles.put(cle, id);
        }

        return idsModeles;
    }

//    public static Modele findById(int id){
//        var sql = "SELECT \"IdModele\", \"IdMarque\", \"Nom\"\n" +
//                "\tFROM public.\"Modele\" WHERE \"IdModele\"=?;";
//        try (var conn =  DB.connect();
//             var pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, id);
//            var rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return new Modele(
//                        rs.getInt("IdModele"),
//                        rs.getInt("IdMarque"),
//                        rs.getString("Nom")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public static int update(int id, int idMarque, String nom) {
//        var sql = "UPDATE public.\"Modele\"\n" +
//                "\tSET \"IdModele\", \"IdMarque\", \"Nom\"=?\n" +
//                "\tWHERE \"IdModele\"=?;";
//
//        int affectedRows = 0;
//
//        try (var conn  = DB.connect();
//             var pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, id);
//            pstmt.setInt(2, idMarque);
//            pstmt.setString(3, nom);
//
//            affectedRows = pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return affectedRows;
//    }

    public static int delete(int id) {
        var sql = "DELETE FROM public.\"Modele\"\n" +
                "\tWHERE \"IdModele\"=?;";
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