package gui.tableaux;

import tablesdb.FacturesDB;
import tablesdb.InstrumentsDB;
import tablesdb.LignesFacturesDB;
import tablesjava.Facture;
import tablesjava.Instrument;
import tablesjava.LigneFacture;


public class TableauLignesFacture extends Tableau<LigneFacture> {

    public TableauLignesFacture(int idFacture) {
        super(
                LignesFacturesDB.trouverLignesFacture(idFacture),
                new String[]{"Numéro de série", "Modèle", "Prix"}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LigneFacture ligneFacture = donnees.get(rowIndex);
        Instrument instrument = InstrumentsDB.findById(ligneFacture.getIdInstrument());

        return switch (columnIndex) {
            case 0 -> instrument.getNumSerie();
            case 1 -> instrument.getNomModele();
            case 2 -> instrument.getPrix();
            default -> null;
        };
    }

    public int calculerPrixTotal() {
        int prixTotal = 0;
        Instrument instrument;
        for (LigneFacture ligneFacture : donnees) {
            instrument = InstrumentsDB.findById(ligneFacture.getIdInstrument());
            prixTotal += instrument.getPrix();
        }
        return prixTotal;
    }
}