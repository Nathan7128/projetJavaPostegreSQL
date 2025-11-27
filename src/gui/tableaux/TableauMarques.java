package gui.tableaux;


// Importation des bibliothèques internes
import tablesdb.MarquesDB;
import tablesjava.Marque;


/**
 * Classe dérivée de la classe mère Tableau, et qui modélise le tableau
 * contenant les données de la table Marque de la bdd
 */
public class TableauMarques extends Tableau<Marque> {

    public TableauMarques() {
        super(
                MarquesDB.getMarques(),
                new String[]{"ID Marque", "Nom", "Site Web"}
        );
    }


    @Override
    public Object getValueAt(int indexLigne, int indexColonne) {
        Marque marque = donnees.get(indexLigne);

        return switch (indexColonne) {
            case 0 -> marque.getId();
            case 1 -> marque.getNom();
            case 2 -> marque.getSiteWeb();
            default -> null;
        };
    }
}