package tablesdb;

import database.DB;
import tablesjava.LigneFacture;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LignesFactures {

    public static int add(LigneFacture ligneFacture) {
        var sql = "INSERT INTO public.\"LigneFacture\"(\n" +
                "\t\"IdFacture\", \"IdInstrument\")\n" +
                "\tVALUES (?, ?);";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, ligneFacture.getId());
            pstmt.setInt(2, ligneFacture.getIdInstrument());

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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idMax;
    }

    public static LigneFacture findById(int id){
        var sql = "SELECT \"IdFacture\", \"IdInstrument\"\n" +
                "\tFROM public.\"LigneFacture\" WHERE \"IdFacture\"=?;";
        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new LigneFacture(
                        rs.getInt("IdFacture"),
                        rs.getInt("IdInstrument")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int update(int id, int idInstrument) {
        var sql = "UPDATE public.\"LigneFacture\"\n" +
                "\tSET \"IdFacture\"=?, \"IdInstrument\"=?\n" +
                "\tWHERE \"IdFacture\"=?;";

        int affectedRows = 0;

        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, idInstrument);
            pstmt.setInt(4, id);

            affectedRows = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int delete(int id) {
        var sql = "DELETE FROM public.\"LigneFacture\"\n" +
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