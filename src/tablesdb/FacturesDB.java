package tablesdb;

import database.DB;
import tablesjava.Facture;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class FacturesDB {

    public static int add(Facture facture) {
        var sql = "INSERT INTO public.\"Facture\"(\n" +
                "\t\"IdFacture\", \"IdClient\", \"Date\")\n" +
                "\tVALUES (?, ?, ?);";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, facture.getId());
            pstmt.setInt(2, facture.getIdClient());
            pstmt.setDate(3, facture.getDate());

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

    public static List<Facture> findAll() {
        var factures = new ArrayList<Facture>();
        var sql = "SELECT \"IdFacture\", \"IdClient\", \"Date\"\n" +
                "\tFROM public.\"Facture\";";

        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var facture = new Facture(
                        rs.getInt("IdFacture"),
                        rs.getInt("IdClient"),
                        rs.getDate("Date")
                );
                factures.add(facture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factures;
    }

    public static int createNewId() {
        var sql = "SELECT MAX(\"IdFacture\") AS id_max\n" +
                "\tFROM public.\"Facture\";";
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

    public static Map<String, Integer> getAllIDsFactures() {
        List<Facture> factures = findAll();
        Map<String, Integer> idsFactures = new HashMap<>();

        for (Facture facture : factures) {
            int id = facture.getId();
            java.sql.Date date = facture.getDate();
            String cle = id + " (" + date.toString() + ")";
            idsFactures.put(cle, id);
        }

        return idsFactures;
    }

    public static Facture findById(int id){
        var sql = "SELECT \"IdFacture\", \"IdClient\", \"Date\"\n" +
                "\tFROM public.\"Facture\" WHERE \"IdFacture\"=?;";
        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Facture(
                        rs.getInt("IdFacture"),
                        rs.getInt("IdClient"),
                        rs.getDate("Date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int update(int id, int idClient, java.sql.Date date) {
        var sql = "UPDATE public.\"Facture\"\n" +
                "\tSET \"IdFacture\"=?, \"IdClient\"=?, \"Date\"=?\n" +
                "\tWHERE \"IdFacture\"=?;";

        int affectedRows = 0;

        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, idClient);
            pstmt.setDate(3, date);
            pstmt.setInt(4, id);

            affectedRows = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int delete(int id) {
        var sql = "DELETE FROM public.\"Facture\"\n" +
                "\tWHERE \"IdFacture\"=?;";
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