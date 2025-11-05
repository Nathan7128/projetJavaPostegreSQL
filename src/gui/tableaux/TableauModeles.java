package gui.tableaux;

import tablesdb.ModelesDB;
import tablesjava.Instrument;
import tablesjava.Modele;

import java.util.Comparator;


public class TableauModeles extends Tableau<Modele> {

    public TableauModeles() {
        super(
                ModelesDB.findAll(),
                new String[]{"ID Modèle", "Nom", "Marque"}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Modele modele = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> modele.getId();
            case 1 -> modele.getNom();
            case 2 -> modele.getNomMarque();
            default -> null;
        };
    }
}