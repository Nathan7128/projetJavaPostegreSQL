package gui.dialogues;

import tablesDB.ClientsDB;
import tablesJava.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FenetreAjouterClient extends JDialog {

    private Client clientCree = null;

    private final JSpinner champIdClient = new JSpinner((new SpinnerNumberModel(0, 0, 100, 1)));
    private final JTextField champNom = new JTextField(15);
    private final JTextField champPrenom = new JTextField(15);
    private final JTextField champAdresse = new JTextField(15);
    private final JTextField champEmail = new JTextField(15);

    public FenetreAjouterClient(JFrame parent) {
        super(parent, "Ajouter un client", true);
        setLayout(new BorderLayout(10, 10));


        JPanel panelForm = new JPanel(new GridLayout(5, 2, 20, 20));

        panelForm.add(new JLabel("Identifiant du client :"));
        panelForm.add(champIdClient);

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
        int id_client = (int) champIdClient.getValue();
        String nom = champNom.getText().trim();
        String prenom = champPrenom.getText().trim();
        String adresse = champAdresse.getText().trim();
        String email = champEmail.getText().trim();


        // Vérifie si l'identifiant existe déjà
        if (ClientsDB.getAllIDsClients().containsValue(id_client)) {
            JOptionPane.showMessageDialog(this,
                    "L'identifiant " + id_client + " existe déjà.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Vérifie si un champ obligatoire est vide
        else if (nom.isEmpty() || prenom.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            clientCree = new Client(id_client, nom, prenom, adresse, email);
            ClientsDB.add(clientCree);
            dispose();
        }
    }

    public Client afficherEtRecuperer() {
        setVisible(true);
        return clientCree;
    }
}