package gui.onglets;

import gui.fenetresafficher.FenetreAfficherFacture;
import gui.fenetresajouter.FenetreAjouterFacture;
import gui.fenetresafficher.FenetreAfficherInstrument;
import gui.fenetresmodifier.FenetreModifierFacture;
import gui.tableaux.TableauFactures;
import tablesdb.FacturesDB;
import tablesjava.Facture;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OngletFactures extends Onglet {
    private TableauFactures tableau = new TableauFactures();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer, bModifier, bAfficher;

    public OngletFactures() {
        super("Factures");

        construireTableau();

        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterFacture();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerFacture();
            }
        });
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierFacture();
            }
        });
        bAfficher = new JButton("Afficher");
        bAfficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherFacture();
            }
        });

        ;
        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);
        boutons.add(bAfficher);

        add(boutons, BorderLayout.SOUTH);
    }

    private void construireTableau() {
        jTableau = new JTable(tableau);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableau);
        jTableau.setRowSorter(sorter);
        sorter.toggleSortOrder(0);

        tableau_defilant = new JScrollPane(jTableau);
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableau_defilant, BorderLayout.CENTER);
    }

    public void ajouterFacture() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterFacture fenetreAjouterFacture = new FenetreAjouterFacture((JFrame) parent, tableau);
        fenetreAjouterFacture.setVisible(true);
    }

    public void supprimerFacture() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = jTableau.convertRowIndexToModel(selection[i]);
            int id_facture = (int) tableau.getValueAt(index, 0);
            tableau.supprDonnee(index);
            FacturesDB.delete(id_facture);
        }
    }

    public void modifierFacture() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 facture à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = jTableau.convertRowIndexToModel(selection[0]);
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierFacture fenetreModifierFacture = new FenetreModifierFacture((JFrame) parent, tableau, index);
            fenetreModifierFacture.setVisible(true);
        }
    }

    public void afficherFacture() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 facture à afficher.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }

        int index = jTableau.convertRowIndexToModel(selection[0]);
        int idFacture = (int) tableau.getValueAt(index, 0);
        Facture facture = FacturesDB.findById(idFacture);
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAfficherFacture fenetreAfficherFacture = new FenetreAfficherFacture((JFrame) parent, facture);
        fenetreAfficherFacture.setVisible(true);
    }
}
