package gui.tableaux;

import tablesdb.FacturesDB;
import tablesjava.Facture;


public class TableauFactures extends Tableau<Facture> {

    public TableauFactures() {
        super(
                FacturesDB.findAll(),
                new String[]{"ID Facture", "Client", "Date"}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Facture facture = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> facture.getId();
            case 1 -> facture.getIdClient();
            case 2 -> facture.getDate();
            default -> null;
        };
    }
}