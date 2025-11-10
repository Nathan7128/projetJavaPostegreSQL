package gui.fenetresafficher;

import gui.tableaux.TableauLignesFacture;
import tablesjava.Facture;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class FenetreAfficherFacture extends JDialog {
    private final TableauLignesFacture tableauLignesFacture;
    private JTable jTableau;
    private JScrollPane tableauDefilant;

    public FenetreAfficherFacture(JFrame parent, Facture facture) {
        super(parent, "Lignes de la facture " + facture.getId(), true);
        tableauLignesFacture = new TableauLignesFacture(facture.getId());

        construireTableau();

        JPanel panneauPrincipal = new JPanel(new BorderLayout(10, 10));
        panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- En-tête facture ---
        JPanel panelEntete = new JPanel(new GridLayout(1, 3, 5, 5));
        panelEntete.add(new JLabel("Client : " + facture.getNomClientComplet()));
        panelEntete.add(new JLabel("Date : " + facture.getDate()));
        panelEntete.add(new JLabel("Prix total : " + tableauLignesFacture.calculerPrixTotal() + " €"));

        // --- Ajout des composants dans le bon ordre ---
        panneauPrincipal.add(panelEntete, BorderLayout.NORTH);
        panneauPrincipal.add(tableauDefilant, BorderLayout.CENTER);

        // --- Bouton de fermeture ---
        JButton boutonFermer = new JButton("Fermer");
        boutonFermer.addActionListener(e -> dispose());
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBas.add(boutonFermer);
        panneauPrincipal.add(panelBas, BorderLayout.SOUTH);

        add(panneauPrincipal);

        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(600, 400));
    }

    private void construireTableau() {
        jTableau = new JTable(tableauLignesFacture);

        TableRowSorter<TableModel> trieurTableau = new TableRowSorter<>(tableauLignesFacture);
        jTableau.setRowSorter(trieurTableau);
        trieurTableau.toggleSortOrder(0);

        jTableau.setFillsViewportHeight(true);
        jTableau.setRowHeight(25);
        tableauDefilant = new JScrollPane(jTableau);
    }
}
