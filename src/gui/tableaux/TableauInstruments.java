package gui.tableaux;

import tablesdb.InstrumentsDB;
import tablesjava.Instrument;

import java.util.Comparator;


public class TableauInstruments extends Tableau<Instrument> {

    public TableauInstruments() {
        super(
                InstrumentsDB.findAll(),
                new String[]{"ID Instrument", "Numéro de série", "Modèle", "Couleur", "Prix"}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Instrument instrument = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> instrument.getId();
            case 1 -> instrument.getNumSerie();
            case 2 -> instrument.getNomModele();
            case 3 -> instrument.getCouleur();
            case 4 -> instrument.getPrix();
            default -> null;
        };
    }
}