package gui.fenetresmodifier;

import gui.fenetresajouter.FenetreAjouterModele;
import gui.tableaux.TableauModeles;
import tablesdb.MarquesDB;
import tablesdb.ModelesDB;
import tablesjava.Marque;
import tablesjava.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FenetreModifierModele extends FenetreAjouterModele {

    int indexTableau;
    private Modele modele;

    public FenetreModifierModele(JFrame parent, TableauModeles tableauModeles, int indexTableau) {
        super(parent, tableauModeles);
        this.indexTableau = indexTableau;
        int idModele = (int) tableauModeles.getValueAt(indexTableau, 0);
        this.modele = ModelesDB.getById(idModele);

        Marque marque = MarquesDB.getById(modele.getIdMarque());
        champMarque.setSelectedItem(marque.getId() + " - " + marque.getNom());

        champNom.setText(modele.getNom());
    }

    @Override
    private void creerModele() {
        int idMarque = allIDsMarques.get((String) champMarque.getSelectedItem());
        String nom = champNom.getText().trim();

        // Vérifie si un champ obligatoire est vide
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Nom).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            this.modele.setIdMarque(idMarque);
            this.modele.setNom(nom);
            ModelesDB.modifier(this.modele.getId(), idMarque, nom);
            tableauModeles.modifierLigne(this.indexTableau, this.modele);
            dispose();
        }
    }
}
