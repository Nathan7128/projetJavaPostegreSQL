package tablesdb;


// Importation des bibliothèques internes
import database.DB;
import tablesjava.Instrument;


// Importation des bibliothèques externes
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Classe implémentant la table "Instrument" de la bdd
 */
public class InstrumentsDB {

    public static int ajouter(Instrument instrument) {
        var sql = "INSERT INTO public.\"Instrument\"(\n" +
                "\t\"IdInstrument\", \"NumSerie\", \"IdModele\", \"Couleur\", \"Prix\", \"Photo\")\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?);";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, instrument.getId());
            pstmt.setString(2, instrument.getNumSerie());
            pstmt.setInt(3, instrument.getIdModele());
            pstmt.setString(4, instrument.getCouleur());
            pstmt.setInt(5, instrument.getPrix());
            pstmt.setString(6, instrument.getPhoto());

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

    public static List<Instrument> getInstruments() {
        var instruments = new ArrayList<Instrument>();
        var sql = "SELECT \"IdInstrument\", \"NumSerie\", \"IdModele\", \"Couleur\", \"Prix\", \"Photo\"\n" +
                "\tFROM public.\"Instrument\";";

        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var instrument = new Instrument(
                        rs.getInt("IdInstrument"),
                        rs.getString("NumSerie"),
                        rs.getInt("IdModele"),
                        rs.getString("Couleur"),
                        rs.getInt("Prix"),
                        rs.getString("Photo")
                    );
                instruments.add(instrument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instruments;
    }

    public static int creerNouvelId() {
        var sql = "SELECT MAX(\"IdInstrument\") AS id_max\n" +
                "\tFROM public.\"Instrument\";";
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
     * du numéro de série de chaque instrument, et comme valeurs les identifiants des instruments
     */
    public static Map<String, Integer> getIdsInstrument() {
        List<Instrument> instruments = getInstruments();
        Map<String, Integer> idsInstrument = new HashMap<>();

        for (Instrument instrument : instruments) {
            int id = instrument.getId();
            String numSerie = instrument.getNumSerie();
            String cle = id + " - " + numSerie;
            idsInstrument.put(cle, id);
        }

        return idsInstrument;
    }

    /**
     * Renvoie un dictionnaire avec comme clés une combinaison de l'identifiant et
     * du numéro de série de chaque instrument, et comme valeurs les identifiants des instruments
     * liés au modèle identifié par idModele
     */
    public static Map<String, Integer> getIdsInstrument(int idModele) {
        List<Instrument> instruments = getInstruments();
        Map<String, Integer> idsInstrument = new HashMap<>();

        for (Instrument instrument : instruments) {
            if (instrument.getIdModele() == idModele) {
                int id = instrument.getId();
                String numSerie = instrument.getNumSerie();
                String cle = id + " - " + numSerie;
                idsInstrument.put(cle, id);
            }
        }

        return idsInstrument;
    }

    public static Instrument getById(int id){
        var sql = "SELECT \"IdInstrument\", \"NumSerie\", \"IdModele\", \"Couleur\", \"Prix\", \"Photo\"\n" +
                "\tFROM public.\"Instrument\" WHERE \"IdInstrument\"=?;";
        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Instrument(
                        rs.getInt("IdInstrument"),
                        rs.getString("NumSerie"),
                        rs.getInt("IdModele"),
                        rs.getString("Couleur"),
                        rs.getInt("Prix"),
                        rs.getString("Photo")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int modifier(int id, String numSerie, int idModele, String couleur, int prix, String photo) {
        var sql = "UPDATE public.\"Instrument\"\n" +
                "\tSET \"IdInstrument\"=?, \"NumSerie\"=?, \"IdModele\"=?, \"Couleur\"=?, \"Prix\"=?, \"Photo\"=?\n" +
                "\tWHERE \"IdInstrument\"=?;";

        int affectedRows = 0;

        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, numSerie);
            pstmt.setInt(3, idModele);
            pstmt.setString(4, couleur);
            pstmt.setInt(5, prix);
            pstmt.setString(6, photo);
            pstmt.setInt(7, id);

            affectedRows = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int supprimer(int id) {
        var sql = "DELETE FROM public.\"Instrument\"\n" +
                "\tWHERE \"IdInstrument\"=?;";
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