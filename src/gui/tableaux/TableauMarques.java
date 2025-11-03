package gui.tableaux;

import tablesdb.MarquesDB;
import tablesjava.Marque;


public class TableauMarques extends Tableau<Marque> {

    public TableauMarques() {
        super(
                MarquesDB.findAll(),
                new String[]{"IdMarque", "Nom", "SiteWeb"}
        );
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