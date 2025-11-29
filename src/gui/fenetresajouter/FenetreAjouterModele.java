package gui.fenetresajouter;


// Importation des bibliothèques internes
import gui.tableaux.TableauModeles;
import tablesdb.ModelesDB;
import tablesdb.MarquesDB;
import tablesjava.Modele;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


/**
 * Cette classe représente une fenêtre de dialogue permettant d'ajouter un modèle.
 * Elle est instanciée lorsque l'utilisateur clique sur "Ajouter" un modèle, et permet de
 * saisir les informations du modèle via sa boite de dialogue.
 */
public class FenetreAjouterModele extends JDialog {

    protected Map<String, Integer> allIdsMarque;
    protected JComboBox champMarque;
    protected JTextField champNom;
    protected TableauModeles tableauModeles;
    protected JButton bValider;
    protected JButton bAnnuler;


    public FenetreAjouterModele(JFrame parent, TableauModeles tableauModeles) {
        super(parent, "Ajouter un modèle", true);

        this.tableauModeles = tableauModeles;

        setLayout(new BorderLayout(10, 10));

        creerChamps();
        creerBoutons();

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerChamps() {
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        allIdsMarque = MarquesDB.getIdsMarque();
        champMarque = new JComboBox(allIdsMarque.keySet().toArray());
        panelForm.add(new JLabel("Marque :"));
        panelForm.add(champMarque);

        champNom = new JTextField(15);
        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);

        add(panelForm, BorderLayout.CENTER);
    }

    private void creerBoutons() {
        bValider = new JButton("Valider");
        bAnnuler = new JButton("Annuler");

        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerModele();
            }
        });

        bAnnuler.addActionListener(e -> {
            dispose();
        });

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(bValider);
        panelBoutons.add(bAnnuler);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void creerModele() {
        int idModele = ModelesDB.creerNouvelId();
        String marqueSelect = (String) champMarque.getSelectedItem();
        String nom = champNom.getText().trim();

        // Vérifie si un champ obligatoire est vide
        if (nom.isEmpty() | marqueSelect == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Nom et Marque).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            int idMarque = allIdsMarque.get(marqueSelect);
            Modele modeleCree = new Modele(idModele, idMarque, nom);
            ModelesDB.ajouter(modeleCree);
            tableauModeles.ajouterDonnee(modeleCree);
            dispose();
        }
    }
}