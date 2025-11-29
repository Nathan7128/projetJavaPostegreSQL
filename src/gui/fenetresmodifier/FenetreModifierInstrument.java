package gui.fenetresmodifier;

import gui.fenetresajouter.FenetreAjouterInstrument;
import gui.tableaux.TableauInstruments;
import tablesdb.InstrumentsDB;
import tablesdb.ModelesDB;
import tablesjava.Instrument;
import tablesjava.Modele;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class FenetreModifierInstrument extends FenetreAjouterInstrument {

    int indexTableau;
    private Instrument instrument;

    public FenetreModifierInstrument(JFrame parent, TableauInstruments tableauInstruments, int indexTableau) {
        super(parent, tableauInstruments);
        this.indexTableau = indexTableau;
        int idInstrument = (int) tableauInstruments.getValueAt(indexTableau, 0);
        this.instrument = InstrumentsDB.getById(idInstrument);

        champNumSerie.setText(instrument.getNumSerie());

        Modele modele = ModelesDB.getById(instrument.getIdModele());
        champModele.setSelectedItem(modele.getId() + " - " + modele.getNom());

        champCouleur.setText(instrument.getCouleur());

        champPrix.setValue(instrument.getPrix());

        bValider.removeActionListener(bValider.getActionListeners()[0]);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierInstrument();
            }
        });
    }

    private void modifierInstrument() {
        String numSerie = champNumSerie.getText().trim();
        int idModele = allIDsModeles.get((String) champModele.getSelectedItem());
        String couleur = champCouleur.getText().trim();
        int prix = (int) champPrix.getValue();

        // Vérifie si un champ obligatoire est vide
        if (numSerie.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Numéro de série).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            this.instrument.setNumSerie(numSerie);
            this.instrument.setIdModele(idModele);
            this.instrument.setCouleur(couleur);
            this.instrument.setPrix(prix);
            this.instrument.setPhoto(champPhoto);
            InstrumentsDB.modifier(this.instrument.getId(), numSerie, idModele, couleur, prix, champPhoto);
            tableauInstruments.modifierLigne(this.indexTableau, this.instrument);
            dispose();
        }
    }
}
