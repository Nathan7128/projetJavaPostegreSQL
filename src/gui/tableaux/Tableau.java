package gui.tableaux;


// Importation des bibliothèques externes
import javax.swing.table.AbstractTableModel;
import java.util.List;


/**
 * Classe mère abstraite modélisant un tableau contenant les données d'une table de la bdd
 * Un tableau est affiché dans un onglet de l'application
 * Elle doit être dérivée pour implémenter des tableaux plus spécifiques, comme celui qui contient les données des clients, des factures, etc.
 */
abstract class Tableau<T> extends AbstractTableModel {

    protected List<T> donnees;
    private final String[] entetes;


    Tableau(List<T> donnees, String[] entetes) {
        this.donnees = donnees;
        this.entetes = entetes;
    }


    @Override
    public int getRowCount() {
        return donnees.size();
    }

    @Override
    public int getColumnCount() {
        return entetes.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    public void ajouterDonnee(T donnee) {
        donnees.add(donnee);

        fireTableRowsInserted(donnees.size() - 1, donnees.size() - 1);
    }

    public void modifierLigne(int ligne, T donnee) {
        donnees.set(ligne, donnee);
        fireTableDataChanged();
    }

    public void supprDonnee(int index) {
        donnees.remove(index);

        fireTableRowsDeleted(index, index);
    }

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);
}
