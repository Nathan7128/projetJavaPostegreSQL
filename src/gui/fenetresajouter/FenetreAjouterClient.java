package gui.fenetresajouter;


// Importation des bibliothèques internes
import gui.tableaux.TableauClients;
import tablesdb.ClientsDB;
import tablesjava.Client;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Cette classe représente une fenêtre de dialogue permettant d'ajouter un client.
 * Elle est instanciée lorsque l'utilisateur clique sur "Ajouter" un client, et permet de
 * saisir les informations du client via sa boite de dialogue.
 */
public class FenetreAjouterClient extends JDialog {

    protected JTextField champNom;
    protected JTextField champPrenom;
    protected JTextField champAdresse;
    protected JTextField champEmail;
    protected TableauClients tableauClients;
    protected JButton bValider;
    protected JButton bAnnuler;

    public FenetreAjouterClient(JFrame parent, TableauClients tableauClients) {
        super(parent, "Ajouter un client", true);
        this.tableauClients = tableauClients;
        setLayout(new BorderLayout(10, 10));

        creerChamps();
        creerBoutons();

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerChamps() {
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 20, 20));

        champNom = new JTextField(15);
        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);

        champPrenom = new JTextField(15);
        panelForm.add(new JLabel("Prénom :"));
        panelForm.add(champPrenom);

        champAdresse = new JTextField(15);
        panelForm.add(new JLabel("Adresse :"));
        panelForm.add(champAdresse);

        champEmail = new JTextField(15);
        panelForm.add(new JLabel("Email :"));
        panelForm.add(champEmail);

        add(panelForm, BorderLayout.CENTER);
    }

    private void creerBoutons() {
        JPanel panelBoutons = new JPanel();

        bValider = new JButton("Valider");
        panelBoutons.add(bValider);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerClient();
            }
        });

        bAnnuler = new JButton("Annuler");
        bAnnuler.addActionListener(e -> {
            dispose();
        });
        panelBoutons.add(bAnnuler);

        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void creerClient() {
        int idClient = ClientsDB.creerNouvelId();
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
            Client clientCree = new Client(idClient, nom, prenom, adresse, email);
            ClientsDB.ajouter(clientCree);
            tableauClients.ajouterDonnee(clientCree);
            dispose();
        }
    }
}