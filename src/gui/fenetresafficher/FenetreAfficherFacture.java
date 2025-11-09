package gui.fenetresafficher;

import gui.tableaux.TableauLignesFacture;
import tablesdb.LignesFacturesDB;
import tablesjava.Facture;
import tablesjava.Instrument;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;

public class FenetreAfficherFacture extends JDialog {
    private final TableauLignesFacture tableauLignesFacture;
    private JTable jTableau;
    private JScrollPane tableauDefilant;

    public FenetreAfficherFacture(JFrame parent, Facture facture) {
        super(parent, "Lignes de la facture " + facture.getId());
        tableauLignesFacture = new TableauLignesFacture(facture.getId());

        construireTableau();

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 20, 20));
        panelForm.add(tableauDefilant);

        JPanel panelFacture = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFacture.add(new JLabel("Date " + facture.getDate().toString()));
        panelFacture.add(new JLabel("Prix total : " + tableauLignesFacture.calculerPrixTotal()));

        panelForm.add(panelFacture);

        add(panelForm, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
    }

    private void construireTableau() {
        jTableau = new JTable(tableauLignesFacture);
        TableRowSorter<TableModel> trieurTableau = new TableRowSorter<>(tableauLignesFacture);
        jTableau.setRowSorter(trieurTableau);
        trieurTableau.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
//        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
    }
}
