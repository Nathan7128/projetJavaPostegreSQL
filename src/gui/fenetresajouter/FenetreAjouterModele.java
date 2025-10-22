package gui.fenetresajouter;

import gui.tableaux.TableauModeles;
import tablesDB.ModelesDB;
import tablesDB.MarquesDB;
import tablesJava.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FenetreAjouterModele extends JDialog {

    private Modele modeleCree = null;

    private final Map<String, Integer> allIDsMarques = MarquesDB.getAllIDsMarques();
    private final JComboBox champMarque = new JComboBox(allIDsMarques.keySet().toArray());
    private final JTextField champNom = new JTextField(15);
    private TableauModeles tableauModeles;

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
        int id_modele = ModelesDB.createNewId();
        int id_marque = allIDsMarques.get((String) champMarque.getSelectedItem());
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
            modeleCree = new Modele(id_modele, id_marque, nom);
            ModelesDB.add(modeleCree);
            tableauModeles.addDonnee(modeleCree);
            dispose();
        }
    }
}