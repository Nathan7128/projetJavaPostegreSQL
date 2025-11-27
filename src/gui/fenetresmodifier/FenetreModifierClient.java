package gui.fenetresmodifier;

import gui.tableaux.TableauClients;
import tablesdb.ClientsDB;
import tablesjava.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreModifierClient extends JDialog {

    private final JTextField champNom = new JTextField(15);
    private final JTextField champPrenom = new JTextField(15);
    private final JTextField champAdresse = new JTextField(15);
    private final JTextField champEmail = new JTextField(15);
    private TableauClients tableauClients;
    int indexTableau;
    private Client client;

    public FenetreModifierClient(JFrame parent, TableauClients tableauClients, int indexTableau) {
        super(parent, "Modifier le client", true);
        this.tableauClients = tableauClients;
        this.indexTableau = indexTableau;
        int idClient = (int) tableauClients.getValueAt(indexTableau, 0);
        this.client = ClientsDB.getById(idClient);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 20, 20));

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);
        champNom.setText(client.getNom());

        panelForm.add(new JLabel("Prénom :"));
        panelForm.add(champPrenom);
        champPrenom.setText(client.getPrenom());

        panelForm.add(new JLabel("Adresse :"));
        panelForm.add(champAdresse);
        champAdresse.setText(client.getAdresse());

        panelForm.add(new JLabel("Email :"));
        panelForm.add(champEmail);
        champEmail.setText(client.getEmail());

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
                modifierClient();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private void modifierClient() {
        String nom = champNom.getText().trim();
        String prenom = champPrenom.getText().trim();
        String adresse = champAdresse.getText().trim();
        String email = champEmail.getText().trim();

        // Vérifie si un champ obligatoire est vide
        if (nom.isEmpty() || prenom.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Nom et Prénom).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            this.client.setNom(nom);
            this.client.setPrenom(prenom);
            this.client.setAdresse(adresse);
            this.client.setEmail(email);
            ClientsDB.modifier(this.client.getId(), nom, prenom, adresse, email);
            tableauClients.modifierLigne(this.indexTableau, this.client);
            dispose();
        }
    }
}
