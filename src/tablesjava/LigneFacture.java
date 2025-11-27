package tablesjava;


/**
 * Classe permettant de modéliser en Java la table "LigneFacture" de la bdd
 */
public class LigneFacture {

    private int idFacture;
    private int IdInstrument;


    public LigneFacture(int idFacture, int idInstrument) {
        this.idFacture = idFacture;
        this.IdInstrument = idInstrument;
    }


    public int getIdFacture() {
        return idFacture;
    }

    public int getIdInstrument() {
        return IdInstrument;
    }
}
