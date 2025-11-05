package tablesjava;
import java.sql.Date;

public class LigneFacture {

    private int id;
    private int IdInstrument;


    public LigneFacture(int id, int idInstrument) {
        this.id = id;
        this.IdInstrument = idInstrument;
    }


//    public void setId(int id) {
//        this.id = id;
//    }
//
    public void setIdInstrument(int idInstrument) { this.IdInstrument = idInstrument; }


    public int getId() {
        return id;
    }

    public int getIdInstrument() {
        return IdInstrument;
    }
}
