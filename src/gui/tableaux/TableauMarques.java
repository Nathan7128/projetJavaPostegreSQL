package gui.tableaux;

import tablesDB.ClientsDB;
import tablesDB.MarquesDB;
import tablesJava.Marque;


public class TableauMarques extends Tableau<Marque> {

    public TableauMarques() {
        super(
                MarquesDB.findAll(),
                new String[]{"IdMarque", "Nom", "SiteWeb"}
        );
    }

    @Override
    public void rafraichir() {
        donnees.clear();
        donnees = MarquesDB.findAll();
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Marque marque = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> marque.getId();
            case 1 -> marque.getNom();
            case 2 -> marque.getSiteWeb();
            default -> null;
        };
    }
}