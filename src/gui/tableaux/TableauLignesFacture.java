package gui.tableaux;


// Importation des bibliothèques internes
import tablesdb.InstrumentsDB;
import tablesdb.LignesFactureDB;
import tablesjava.Instrument;
import tablesjava.LigneFacture;


/**
 * Classe dérivée de la classe mère Tableau, et qui modélise le tableau
 * contenant les données de la table LigneFacture de la bdd
 */
public class TableauLignesFacture extends Tableau<LigneFacture> {

    public TableauLignesFacture(int idFacture) {
        super(
                LignesFactureDB.getLignesFacture(idFacture),
                new String[]{"Numéro de série", "Modèle", "Prix"}
        );
    }


    @Override
    public Object getValueAt(int indexLigne, int indexColonne) {
        LigneFacture ligneFacture = donnees.get(indexLigne);
        Instrument instrument = InstrumentsDB.getById(ligneFacture.getIdInstrument());

        return switch (indexColonne) {
            case 0 -> instrument.getNumSerie();
            case 1 -> instrument.getNomModele();
            case 2 -> instrument.getPrix() + " €";
            default -> null;
        };
    }

    public int calculerPrixTotal() {
        int prixTotal = 0;
        Instrument instrument;
        for (LigneFacture ligneFacture : donnees) {
            instrument = InstrumentsDB.getById(ligneFacture.getIdInstrument());
            prixTotal += instrument.getPrix();
        }
        return prixTotal;
    }
}