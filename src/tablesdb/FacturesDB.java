package tablesdb;


// Importation des bibliothèques internes
import database.DB;
import tablesjava.Facture;

// Importation des bibliothèques externes
import java.sql.Statement;
import java.util.*;


/**
 * Classe implémentant la table "Facture" de la bdd
 */
public class FacturesDB {

    public static int ajouter(Facture facture) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Facture> getFactures() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factures;
    }

    public static int creerNouvelId() {
        var sql = "SELECT MAX(\"IdFacture\") AS id_max\n" +
                "\tFROM public.\"Facture\";";
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

    /**
     * Renvoie un dictionnaire avec comme clés une combinaison de l'identifiant et
     * de la date de chaque facture, et comme valeurs les identifiants des factures
     */
    public static Map<String, Integer> getIdsFacture() {
        List<Facture> factures = getFactures();
        Map<String, Integer> idsFacture = new HashMap<>();

        for (Facture facture : factures) {
            int id = facture.getId();
            java.sql.Date date = facture.getDate();
            String cle = id + " (" + date.toString() + ")";
            idsFacture.put(cle, id);
        }

        return idsFacture;
    }

    public static Facture getById(int id){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int modifier(int id, int idClient, java.sql.Date date) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int supprimer(int id) {
        var sql = "DELETE FROM public.\"Facture\"\n" +
                "\tWHERE \"IdFacture\"=?;";
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