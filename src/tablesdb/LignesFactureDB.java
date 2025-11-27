package tablesdb;

// Importation des bibliothèques internes
import database.DB;
import tablesjava.LigneFacture;

// Importation des bibliothèques externes
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LignesFactureDB {

    public static int ajouter(LigneFacture ligneFacture) {
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

    public static List<LigneFacture> getLignesFacture(int idFacture){
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
        supprLignesFacture(idFacture);
        LigneFacture ligneFacture;
        for (int idInstrument : idsInstrument) {
            ligneFacture = new LigneFacture(idFacture, idInstrument);
            ajouter(ligneFacture);
        }
    }

    public static int supprLignesFacture(int idFacture) {
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