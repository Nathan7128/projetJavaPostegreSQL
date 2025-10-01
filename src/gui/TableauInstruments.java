package gui;
import tablesDB.InstrumentDB;
import tablesJava.Instrument;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableauInstruments extends AbstractTableModel {
    private final List<Instrument> instruments;

    private final String[] entetes = {"IdInstrument", "NumSerie", "IdModele", "Couleur", "Prix", "Photo"};

    public TableauInstruments() {
        super();

        instruments = InstrumentDB.findAll();
    }

    public int getRowCount() {
        return instruments.size();
    }

    public int getColumnCount() {
        return entetes.length;
    }

    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return instruments.get(rowIndex).getId();
            case 1:
                return instruments.get(rowIndex).getNumSerie();
            case 2:
                return instruments.get(rowIndex).getIdModele();
            case 3:
                return instruments.get(rowIndex).getCouleur();
            case 4:
                return instruments.get(rowIndex).getPrix();
            case 5:
                return instruments.get(rowIndex).getPhoto();
            default:
                return null;
        }
    }
}
