package gui.tableaux;

import tablesDB.ModelesDB;
import tablesJava.Modele;


public class TableauModeles extends Tableau<Modele> {

    public TableauModeles() {
        super(
                ModelesDB.findAll(),
                new String[]{"IdModele", "IdMarque", "Nom"}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Modele modele = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> modele.getId();
            case 1 -> modele.getIdMarque();
            case 2 -> modele.getNom();
            default -> null;
        };
    }
}