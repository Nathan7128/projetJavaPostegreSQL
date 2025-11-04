package gui.fenetresmodifier;

import gui.tableaux.TableauModeles;
import tablesdb.MarquesDB;
import tablesdb.ModelesDB;
import tablesjava.Marque;
import tablesjava.Modele;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class FenetreModifierModele extends JDialog {

    private Modele modeleCree = null;

    private final Map<String, Integer> allIDsMarques = MarquesDB.getAllIDsMarques();
    private final JComboBox champMarque = new JComboBox(allIDsMarques.keySet().toArray());
    private final JTextField champNom = new JTextField(15);
    private TableauModeles tableauModeles;
    int indexTableau;
    private Modele modele;

    public FenetreModifierModele(JFrame parent, TableauModeles tableauModeles, int indexTableau) {
        super(parent, "Modifier le modèle", true);
        this.tableauModeles = tableauModeles;
        this.indexTableau = indexTableau;
        int idModele = (int) tableauModeles.getValueAt(indexTableau, 0);
        this.modele = ModelesDB.findById(idModele);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        panelForm.add(new JLabel("Marque :"));
        panelForm.add(champMarque);
        Marque marque = MarquesDB.findById(modele.getIdMarque());
        champMarque.setSelectedItem(marque.getId() + " - " + marque.getNom());

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);
        champNom.setText(modele.getNom());

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
                modifierModele();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private void modifierModele() {
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
            ModelesDB.update(this.modele.getId(), idMarque, nom);
            tableauModeles.modifierLigne(this.indexTableau, this.modele);
            dispose();
        }
    }
}
