package gui.tableaux;

import tablesDB.ClientsDB;
import tablesDB.FacturesDB;
import tablesJava.Facture;


public class TableauFactures extends Tableau<Facture> {

    public TableauFactures() {
        super(
                FacturesDB.findAll(),
                new String[]{"IdFacture", "IdClient", "Date"}
        );
    }

    @Override
    public void rafraichir() {
        donnees.clear();
        donnees = FacturesDB.findAll();
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Facture modele = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> modele.getId();
            case 1 -> modele.getIdClient();
            case 2 -> modele.getDate();
            default -> null;
        };
    }
}