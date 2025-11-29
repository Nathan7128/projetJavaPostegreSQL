package gui.fenetresmodifier;


// Importation des bibliothèques internes
import gui.fenetresajouter.FenetreAjouterModele;
import gui.tableaux.TableauModeles;
import tablesdb.MarquesDB;
import tablesdb.ModelesDB;
import tablesjava.Marque;
import tablesjava.Modele;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Cette classe hérite de la classe mère "FenetreAjouterModele" et est utilisée pour modifier un modèle
 * déjà présent dans la BDD.
 * Elle hérite de cette classe car elle lui est très semblable : elle possède les mêmes champs, labels, etc.,
 * à la différence que certains d'entre eux sont remplis par défaut car le modèle a déjà été saisie.
 */
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

        bValider.removeActionListener(bValider.getActionListeners()[0]);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierModele();
            }
        });
    }

    private void modifierModele() {
        int idMarque = allIdsMarque.get((String) champMarque.getSelectedItem());
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
