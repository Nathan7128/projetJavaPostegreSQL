package gui.dialogues;

import tablesDB.MarquesDB;
import tablesJava.Marque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreAjouterMarque extends JDialog {

    private Marque marqueCreee = null;

    private final JSpinner champIdMarque = new JSpinner((new SpinnerNumberModel(0, 0, 100, 1)));
    private final JTextField champNom = new JTextField(15);
    private final JTextField champSiteWeb = new JTextField(15);

    public FenetreAjouterMarque(JFrame parent) {
        super(parent, "Ajouter une marque", true);
        setLayout(new BorderLayout(10, 10));


        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        panelForm.add(new JLabel("Identifiant de la marque :"));
        panelForm.add(champIdMarque);

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
        int id_marque = (int) champIdMarque.getValue();
        String nom = champNom.getText().trim();
        String site_web = champSiteWeb.getText().trim();

        // Vérifie si l'identifiant existe déjà
        if (MarquesDB.getAllIDsMarques().containsValue(id_marque)) {
            JOptionPane.showMessageDialog(this,
                    "L'identifiant " + id_marque + " existe déjà.",
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
            marqueCreee = new Marque(id_marque, nom, site_web);
            MarquesDB.add(marqueCreee);
            dispose();
        }
    }

    public Marque afficherEtRecuperer() {
        setVisible(true);
        return marqueCreee;
    }
}