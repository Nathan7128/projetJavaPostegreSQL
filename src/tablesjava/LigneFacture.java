package tablesjava;
import java.sql.Date;

public class LigneFacture {

    private int idFacture;
    private int IdInstrument;


    public LigneFacture(int idFacture, int idInstrument) {
        this.idFacture = idFacture;
        this.IdInstrument = idInstrument;
    }


//    public void setId(int id) {
//        this.id = id;
//    }
//
    public void setIdInstrument(int idInstrument) { this.IdInstrument = idInstrument; }


    public int getIdFacture() {
        return idFacture;
    }

    public int getIdInstrument() {
        return IdInstrument;
    }
}
