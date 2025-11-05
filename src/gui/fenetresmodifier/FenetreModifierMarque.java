package gui.fenetresmodifier;

import gui.tableaux.TableauMarques;
import tablesdb.MarquesDB;
import tablesjava.Marque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FenetreModifierMarque extends JDialog {

    private final JTextField champNom = new JTextField(15);
    private final JTextField champSiteWeb = new JTextField(15);
    private TableauMarques tableauMarques;
    int indexTableau;
    private Marque marque;

    public FenetreModifierMarque(JFrame parent, TableauMarques tableauMarques, int indexTableau) {
        super(parent, "Modifier la marque", true);
        this.tableauMarques = tableauMarques;
        this.indexTableau = indexTableau;
        int idMarque = (int) tableauMarques.getValueAt(indexTableau, 0);
        this.marque = MarquesDB.findById(idMarque);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 20, 20));

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);
        champNom.setText(marque.getNom());

        panelForm.add(new JLabel("Site Web :"));
        panelForm.add(champSiteWeb);
        champSiteWeb.setText(marque.getSiteWeb());

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
                modifierMarque();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
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
            MarquesDB.update(this.marque.getId(), nom, siteWeb);
            tableauMarques.modifierLigne(this.indexTableau, this.marque);
            dispose();
        }
    }
}
