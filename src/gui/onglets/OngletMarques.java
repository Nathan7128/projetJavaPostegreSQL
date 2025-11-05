package gui.onglets;

import gui.fenetresajouter.FenetreAjouterMarque;
import gui.fenetresmodifier.FenetreModifierMarque;
import gui.tableaux.TableauMarques;
import tablesdb.MarquesDB;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OngletMarques extends Onglet {
    private TableauMarques tableau = new TableauMarques();
    private JTable jTableau;
    private JScrollPane tableauDefilant;
    private JButton bAjouter, bSupprimer, bModifier;

    public OngletMarques() {
        super("Marques");

        construireTableau();

        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMarque();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerMarque();
            }
        });
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierMarque();
            }
        });

        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);

        add(boutons, BorderLayout.SOUTH);
    }

    private void construireTableau() {
        jTableau = new JTable(tableau);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableau);
        jTableau.setRowSorter(sorter);
        sorter.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableauDefilant, BorderLayout.CENTER);
    }

    public void ajouterMarque() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterMarque fenetreAjouterMarque = new FenetreAjouterMarque((JFrame) parent, tableau);
        fenetreAjouterMarque.setVisible(true);
    }

    public void supprimerMarque() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = selection[i];
            int idMarque = (int) tableau.getValueAt(index, 0);
            tableau.supprDonnee(index);
            MarquesDB.delete(idMarque);
        }
    }

    public void modifierMarque() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 marque à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = selection[0];
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierMarque fenetreModifierMarque = new FenetreModifierMarque((JFrame) parent, tableau, index);
            fenetreModifierMarque.setVisible(true);
        }
    }
}
