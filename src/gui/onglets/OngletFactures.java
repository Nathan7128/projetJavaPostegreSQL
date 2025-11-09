package gui.onglets;

import gui.fenetresajouter.FenetreAjouterFacture;
import gui.fenetresimages.FenetreImageInstrument;
import gui.fenetresmodifier.FenetreModifierFacture;
import gui.tableaux.TableauFactures;
import tablesdb.FacturesDB;
import tablesdb.InstrumentsDB;
import tablesjava.Facture;
import tablesjava.Instrument;
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
                try {
                    afficherFacture();
                }
                catch (IOException ex) {
                }
            }
        });

        ;
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
            int index = selection[i];
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
            int index = selection[0];
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

        int index = selection[0];
        int idFacture = (int) tableau.getValueAt(index, 0);
        Facture facture = FacturesDB.findById(idFacture);
        String photo = instrument.getPhoto();
        String cheminPhoto = Constants.cheminPhotosInstruments + Constants.sep + photo;
        File fichierPhoto = new File(cheminPhoto);
        if (photo == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir une photo pour cet instrument.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else if (!(fichierPhoto.exists())) {
            JOptionPane.showMessageDialog(this,
                    "L'image " + photo + " est introuvable dans le dossier " + Constants.cheminPhotosInstruments + " .",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            ImageIcon imageInstrument = new ImageIcon(cheminPhoto);
            FenetreImageInstrument fenetreImageInstrument = new FenetreImageInstrument(imageInstrument);
            fenetreImageInstrument.setVisible(true);
        }
    }
}
