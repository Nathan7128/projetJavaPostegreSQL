package gui.fenetresajouter;

import gui.tableaux.TableauModeles;
import tablesdb.ModelesDB;
import tablesdb.MarquesDB;
import tablesjava.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FenetreAjouterModele extends JDialog {

    protected final Map<String, Integer> allIDsMarques = MarquesDB.getIdsMarque();
    protected final JComboBox champMarque = new JComboBox(allIDsMarques.keySet().toArray());
    protected final JTextField champNom = new JTextField(15);
    protected TableauModeles tableauModeles;

    public FenetreAjouterModele(JFrame parent, TableauModeles tableauModeles) {
        super(parent, "Ajouter un modèle", true);
        this.tableauModeles = tableauModeles;
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        panelForm.add(new JLabel("Marque :"));
        panelForm.add(champMarque);

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);

        add(panelForm, BorderLayout.CENTER);


        JPanel panelBoutons = new JPanel();
        JButton boutonValider = new JButton("Valider");
        JButton boutonAnnuler = new JButton("Annuler");

        panelBoutons.add(boutonValider);
        panelBoutons.add(boutonAnnuler);
        add(panelBoutons, BorderLayout.SOUTH);


        boutonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerModele();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
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
            int idMarque = allIDsMarques.get(marqueSelect);
            Modele modeleCree = new Modele(idModele, idMarque, nom);
            ModelesDB.ajouter(modeleCree);
            tableauModeles.ajouterDonnee(modeleCree);
            dispose();
        }
    }
}