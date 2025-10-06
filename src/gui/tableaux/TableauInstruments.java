package gui.tableaux;

import tablesDB.InstrumentsDB;
import tablesJava.Instrument;


public class TableauInstruments extends Tableau<Instrument> {

    public TableauInstruments() {
        super(
                InstrumentsDB.findAll(),
                new String[]{"IdInstrument", "NumSerie", "IdModele", "Couleur", "Prix", "Photo"}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Instrument instrument = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> instrument.getId();
            case 1 -> instrument.getNumSerie();
            case 2 -> instrument.getIdModele();
            case 3 -> instrument.getCouleur();
            case 4 -> instrument.getPrix();
            case 5 -> instrument.getPhoto();
            default -> null;
        };
    }
}