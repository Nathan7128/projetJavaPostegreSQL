package gui.tableaux;


// Importation des bibliothèques internes
import tablesdb.ModelesDB;
import tablesjava.Modele;


/**
 * Classe dérivée de la classe mère Tableau, et qui modélise le tableau
 * contenant les données de la table Modele de la bdd
 */
public class TableauModeles extends Tableau<Modele> {

    public TableauModeles() {
        super(
                ModelesDB.getModeles(),
                new String[]{"ID Modèle", "Nom", "Marque"}
        );
    }


    @Override
    public Object getValueAt(int indexLigne, int indexColonne) {
        Modele modele = donnees.get(indexLigne);

        return switch (indexColonne) {
            case 0 -> modele.getId();
            case 1 -> modele.getNom();
            case 2 -> modele.getNomMarque();
            default -> null;
        };
    }
}