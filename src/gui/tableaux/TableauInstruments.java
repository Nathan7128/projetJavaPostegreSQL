package gui.tableaux;


// Importation des bibliothèques internes
import tablesdb.InstrumentsDB;
import tablesjava.Instrument;


/**
 * Classe dérivée de la classe mère Tableau, et qui modélise le tableau
 * contenant les données de la table Instrument de la bdd
 */
public class TableauInstruments extends Tableau<Instrument> {

    public TableauInstruments() {
        super(
                InstrumentsDB.getInstruments(),
                new String[]{"ID Instrument", "Numéro de série", "Modèle", "Couleur", "Prix"}
        );
    }


    @Override
    public Object getValueAt(int indexLigne, int indexColonne) {
        Instrument instrument = donnees.get(indexLigne);

        return switch (indexColonne) {
            case 0 -> instrument.getId();
            case 1 -> instrument.getNumSerie();
            case 2 -> instrument.getNomModele();
            case 3 -> instrument.getCouleur();
            case 4 -> instrument.getPrix() + " €";
            default -> null;
        };
    }
}