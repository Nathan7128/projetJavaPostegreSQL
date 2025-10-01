package tablesDB;

import database.DB;
import tablesJava.Instrument;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDB {

    public static int add(Instrument instrument) {
        var sql = "INSERT INTO Instrument(" +
                "IdInstrument, NumSerie, IdModele, Couleur, Prix, Photo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, instrument.getId());
            pstmt.setString(2, instrument.getNumSerie());
            pstmt.setString(3, instrument.getIdModele());
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Instrument> findAll() {
        var instruments = new ArrayList<Instrument>();

        var sql = "SELECT IdInstrument, NumSerie, IdModele, Couleur, Prix, Photo " +
                "FROM Instrument ORDER BY IdInstrument";

        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var instrument = new Instrument(
                        rs.getString("IdInstrument"),
                        rs.getString("NumSerie"),
                        rs.getString("IdModele"),
                        rs.getString("Couleur"),
                        rs.getInt("Prix"),
                        rs.getString("Photo")
                    );
                instruments.add(instrument);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instruments;
    }

    public static Instrument findById(int id){
        var sql = "SELECT IdInstrument, NumSerie, IdModele, Couleur, Prix, Photo " +
                "FROM Instrument WHERE IdInstrument=?";
        try (var conn =  DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Instrument(
                        rs.getString("IdInstrument"),
                        rs.getString("NumSerie"),
                        rs.getString("IdModele"),
                        rs.getString("Couleur"),
                        rs.getInt("Prix"),
                        rs.getString("Photo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int update(String id, String numSerie, String idModele, String couleur, int prix, String photo) {
        var sql = "UPDATE Instrument " +
                "SET IdInstrument=?, NumSerie=?, IdModele=?, Couleur=?, Prix=?, Photo=? " +
                "WHERE IdInstrument=";

        int affectedRows = 0;

        try (var conn  = DB.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, numSerie);
            pstmt.setString(3, idModele);
            pstmt.setString(4, couleur);
            pstmt.setInt(5, prix);
            pstmt.setString(6, photo);

            affectedRows = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static int delete(int id) {
        var sql = "DELETE FROM Instrument WHERE IdInstrument=?";
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