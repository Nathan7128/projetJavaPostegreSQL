package gui.tableaux;


// Importation des bibliothèques internes
import tablesdb.FacturesDB;
import tablesjava.Facture;


/**
 * Classe dérivée de la classe mère Tableau, et qui modélise le tableau
 * contenant les données de la table Facture de la bdd
 */
public class TableauFactures extends Tableau<Facture> {

    public TableauFactures() {
        super(
                FacturesDB.getFactures(),
                new String[]{"ID Facture", "Client", "Date"}
        );
    }


    @Override
    public Object getValueAt(int indexLigne, int indexColonne) {
        Facture facture = donnees.get(indexLigne);

        return switch (indexColonne) {
            case 0 -> facture.getId();
            case 1 -> facture.getNomClientComplet();
            case 2 -> facture.getDate();
            default -> null;
        };
    }
}