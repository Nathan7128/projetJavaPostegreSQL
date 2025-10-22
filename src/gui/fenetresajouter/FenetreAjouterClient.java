package gui.fenetresajouter;

import gui.tableaux.TableauClients;
import tablesDB.ClientsDB;
import tablesJava.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreAjouterClient extends JDialog {

    private Client clientCree = null;

    private final JTextField champNom = new JTextField(15);
    private final JTextField champPrenom = new JTextField(15);
    private final JTextField champAdresse = new JTextField(15);
    private final JTextField champEmail = new JTextField(15);
    private TableauClients tableauClients;

    public FenetreAjouterClient(JFrame parent, TableauClients tableauClients) {
        super(parent, "Ajouter un client", true);
        this.tableauClients = tableauClients;
        setLayout(new BorderLayout(10, 10));


        JPanel panelForm = new JPanel(new GridLayout(4, 2, 20, 20));

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);

        panelForm.add(new JLabel("Prénom :"));
        panelForm.add(champPrenom);

        panelForm.add(new JLabel("Adresse :"));
        panelForm.add(champAdresse);

        panelForm.add(new JLabel("Email :"));
        panelForm.add(champEmail);

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
                creerClient();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerClient() {
        int id_client = ClientsDB.createNewId();
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
            clientCree = new Client(id_client, nom, prenom, adresse, email);
            ClientsDB.add(clientCree);
            tableauClients.addDonnee(clientCree);
            dispose();
        }
    }
}