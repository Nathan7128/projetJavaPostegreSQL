package gui.onglets;

import gui.fenetresImages.FenetreImageInstrument;
import gui.tableaux.TableauInstruments;
import tablesDB.InstrumentsDB;
import gui.fenetresajouter.FenetreAjouterInstrument;
import tablesJava.Instrument;
import utils.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OngletInstruments extends Onglet {
    private TableauInstruments tableau = new TableauInstruments();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer, bModifier, bAfficher;
    private JLabel imageInstrument = new JLabel();

    public OngletInstruments() {
        super("Instruments", Constants.cheminIconeOngletInstruments);

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
                if (imageInstrument.getIcon() == null) {
                    System.out.println("good");
                    try {
                        afficherInstrument();
                    }
                    catch (IOException ex) {
                    }
                }
                else {
                    remove(imageInstrument);
                    imageInstrument.setIcon(null);
                    add(tableau_defilant);
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
        tableau_defilant = new JScrollPane(jTableau);
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableau_defilant, BorderLayout.CENTER);
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
            int id_instrument = (int) tableau.getValueAt(index, 0);
            tableau.supprDonnee(index);
            InstrumentsDB.delete(id_instrument);
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
        int id_instrument = (int) tableau.getValueAt(index, 0);
        Instrument instrument = InstrumentsDB.findById(id_instrument);
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
            BufferedImage photoInstrument = ImageIO.read(fichierPhoto);
            imageInstrument.setIcon(new ImageIcon(photoInstrument));
            remove(tableau_defilant);
            add(imageInstrument);
        }
    }
}
