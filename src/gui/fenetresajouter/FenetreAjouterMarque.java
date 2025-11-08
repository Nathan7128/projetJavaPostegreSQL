package gui.fenetresajouter;

import gui.tableaux.TableauMarques;
import tablesdb.MarquesDB;
import tablesjava.Marque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreAjouterMarque extends JDialog {

    private final JTextField champNom = new JTextField(15);
    private final JTextField champSiteWeb = new JTextField(15);
    private TableauMarques tableauMarques;

    public FenetreAjouterMarque(JFrame parent, TableauMarques tableauMarques) {
        super(parent, "Ajouter une marque", true);
        this.tableauMarques = tableauMarques;

        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 20, 20));

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);

        panelForm.add(new JLabel("Site Web :"));
        panelForm.add(champSiteWeb);

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
                creerMarque();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerMarque() {
        int idMarque = MarquesDB.createNewId();
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
            Marque marqueCreee = new Marque(idMarque, nom, siteWeb);
            MarquesDB.add(marqueCreee);
            tableauMarques.addDonnee(marqueCreee);
            dispose();
        }
    }
}