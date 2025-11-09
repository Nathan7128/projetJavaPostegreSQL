package tablesdb;

import database.DB;
import tablesjava.LigneFacture;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LignesFacturesDB {

    public static int add(LigneFacture ligneFacture) {
        var sql = "INSERT INTO public.\"LigneFacture\"(\n" +
                "\t\"IdFacture\", \"IdInstrument\")\n" +
                "\tVALUES (?, ?);";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, ligneFacture.getIdFacture());
            pstmt.setInt(2, ligneFacture.getIdInstrument());

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

    public static List<LigneFacture> findAll() {
        var lignesFactures = new ArrayList<LigneFacture>();
        var sql = "SELECT \"IdFacture\", \"IdInstrument\"\n" +
                "\tFROM public.\"LigneFacture\";";

        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var ligneFacture = new LigneFacture(
                        rs.getInt("IdFacture"),
                        rs.getInt("IdInstrument")
                );
                lignesFactures.add(ligneFacture);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lignesFactures;
    }

    public static int createNewId() {
        var sql = "SELECT MAX(\"IdFacture\") AS id_max\n" +
                "\tFROM public.\"LigneFacture\";";
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

    public static LigneFacture findById(int idFacture, int idInstrument){
        var sql = "SELECT \"IdFacture\", \"IdInstrument\"\n" +
                "\tFROM public.\"LigneFacture\" WHERE \"IdFacture\"=? AND \"IdInstrument\"=?;";
        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFacture);
            pstmt.setInt(2, idInstrument);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new LigneFacture(
                        rs.getInt("IdFacture"),
                        rs.getInt("IdInstrument")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<LigneFacture> trouverLignesFacture(int idFacture){
        var lignesFacture = new ArrayList<LigneFacture>();
        var sql = "SELECT \"IdFacture\", \"IdInstrument\"\n" +
                "\tFROM public.\"LigneFacture\" WHERE \"IdFacture\"=?";
        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFacture);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                var ligneFacture = new LigneFacture(
                        rs.getInt("IdFacture"),
                        rs.getInt("IdInstrument")
                );
                lignesFacture.add(ligneFacture);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lignesFacture;
    }

    public static void modifierFacture(int idFacture, List<Integer> idsInstrument) {
        supprFacture(idFacture);
        LigneFacture ligneFacture;
        for (int idInstrument : idsInstrument) {
            ligneFacture = new LigneFacture(idFacture, idInstrument);
            add(ligneFacture);
        }
    }

    public static int supprLigneFacture(int idFacture, int idInstrument) {
        var sql = "DELETE FROM public.\"LigneFacture\"\n" +
                "\tWHERE \"IdFacture\"=? AND \"IdInstrument\"=?;";
        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFacture);
            pstmt.setInt(1, idInstrument);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int supprFacture(int idFacture) {
        var sql = "DELETE FROM public.\"LigneFacture\"\n" +
                "\tWHERE \"IdFacture\"=?;";
        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFacture);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}