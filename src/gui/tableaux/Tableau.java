package gui.tableaux;

import tablesjava.Instrument;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;

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

    public void addDonnee(T donnee) {
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
