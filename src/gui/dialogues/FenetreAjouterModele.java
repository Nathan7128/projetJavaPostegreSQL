package gui.dialogues;

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

    private final JSpinner champIdModele = new JSpinner((new SpinnerNumberModel(0, 0, 100, 1)));
    private final Map<String, Integer> allIDsMarques = MarquesDB.getAllIDsMarques();
    private final JComboBox champMarque = new JComboBox(allIDsMarques.keySet().toArray());
    private final JTextField champNom = new JTextField(15);

    public FenetreAjouterModele(JFrame parent) {
        super(parent, "Ajouter un modèle", true);
        setLayout(new BorderLayout(10, 10));


        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        panelForm.add(new JLabel("Identifiant du modèle :"));
        panelForm.add(champIdModele);

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
        int id_modele = (int) champIdModele.getValue();
        int id_marque = allIDsMarques.get((String) champMarque.getSelectedItem());
        String nom = champNom.getText().trim();

        // Vérifie si l'identifiant existe déjà
        if (ModelesDB.getAllIDsModeles().containsValue(id_modele)) {
            JOptionPane.showMessageDialog(this,
                    "L'identifiant " + id_modele + " existe déjà.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Vérifie si un champ obligatoire est vide
        else if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            modeleCree = new Modele(id_modele, id_marque, nom);
            ModelesDB.add(modeleCree);
            dispose();
        }
    }

    public Modele afficherEtRecuperer() {
        setVisible(true);
        return modeleCree;
    }
}