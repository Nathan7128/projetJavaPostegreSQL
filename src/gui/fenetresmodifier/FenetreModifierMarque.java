package gui.fenetresmodifier;


// Importation des bibliothèques internes
import gui.fenetresajouter.FenetreAjouterMarque;
import gui.tableaux.TableauMarques;
import tablesdb.MarquesDB;
import tablesjava.Marque;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Cette classe hérite de la classe mère "FenetreAjouterMarque" et est utilisée pour modifier une marque
 * déjà présent dans la BDD.
 * Elle hérite de cette classe car elle lui est très semblable : elle possède les mêmes champs, labels, etc.,
 * à la différence que certains d'entre eux sont remplis par défaut car la marque a déjà été saisie.
 */
public class FenetreModifierMarque extends FenetreAjouterMarque {

    int indexTableau;
    private Marque marque;


    public FenetreModifierMarque(JFrame parent, TableauMarques tableauMarques, int indexTableau) {
        super(parent, tableauMarques);

        this.indexTableau = indexTableau;
        int idMarque = (int) tableauMarques.getValueAt(indexTableau, 0);
        this.marque = MarquesDB.getById(idMarque);

        champNom.setText(marque.getNom());
        champSiteWeb.setText(marque.getSiteWeb());

        bValider.removeActionListener(bValider.getActionListeners()[0]);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierMarque();
            }
        });
    }

    private void modifierMarque() {
        String nom = champNom.getText().trim();
        String siteWeb = champSiteWeb.getText().trim();

        // Vérifie si un champ obligatoire est vide
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Nom).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            this.marque.setNom(nom);
            this.marque.setSiteWeb(siteWeb);
            MarquesDB.modifier(this.marque.getId(), nom, siteWeb);
            tableauMarques.modifierLigne(this.indexTableau, this.marque);
            dispose();
        }
    }
}
