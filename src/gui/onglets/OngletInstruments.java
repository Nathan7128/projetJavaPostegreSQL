package gui.onglets;

import gui.fenetresimages.FenetreImageInstrument;
import gui.fenetresmodifier.FenetreModifierInstrument;
import gui.tableaux.TableauInstruments;
import tablesdb.InstrumentsDB;
import gui.fenetresajouter.FenetreAjouterInstrument;
import tablesjava.Instrument;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OngletInstruments extends Onglet {
    private TableauInstruments tableau = new TableauInstruments();
    private JTable jTableau;
    private JScrollPane tableauDefilant;
    private JButton bAjouter, bSupprimer, bModifier, bAfficher;

    public OngletInstruments() {
        super("Instruments");

        construireTableau();

        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterInstrument();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerInstrument();
            }
        });
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierInstrument();
            }
        });
        bAfficher = new JButton("Afficher");
        bAfficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    afficherInstrument();
                }
                catch (IOException ex) {
                }
            }
        });

        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);
        boutons.add(bAfficher);

        add(boutons, BorderLayout.SOUTH);
    }

    public void construireTableau() {
        jTableau = new JTable(tableau);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableau);
        jTableau.setRowSorter(sorter);
        sorter.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableauDefilant, BorderLayout.CENTER);
    }

    public void ajouterInstrument() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterInstrument fenetreAjouterInstrument = new FenetreAjouterInstrument((JFrame) parent, tableau);
        fenetreAjouterInstrument.setVisible(true);
    }

    public void supprimerInstrument() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = selection[i];
            int idInstrument = (int) tableau.getValueAt(index, 0);
            tableau.supprDonnee(index);
            InstrumentsDB.delete(idInstrument);
        }
    }

    public void modifierInstrument() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 instrument à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = selection[0];
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierInstrument fenetreModifierInstrument = new FenetreModifierInstrument((JFrame) parent, tableau, index);
            fenetreModifierInstrument.setVisible(true);
        }
    }

    public void afficherInstrument() throws IOException {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 instrument à afficher.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }

        int index = selection[0];
        int idInstrument = (int) tableau.getValueAt(index, 0);
        Instrument instrument = InstrumentsDB.findById(idInstrument);
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
