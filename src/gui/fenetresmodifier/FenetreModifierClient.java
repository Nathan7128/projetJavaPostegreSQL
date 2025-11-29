package gui.fenetresmodifier;


// Importation des bibliothèques internes
import gui.fenetresajouter.FenetreAjouterClient;
import gui.tableaux.TableauClients;
import tablesdb.ClientsDB;
import tablesjava.Client;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Cette classe hérite de la classe mère "FenetreAjouterClient" et est utilisée pour modifier un client
 * déjà présent dans la BDD.
 * Elle hérite de cette classe car elle lui est très semblable : elle possède les mêmes champs, labels, etc.,
 * à la différence que certains d'entre eux sont remplis par défaut car le client a déjà été saisie.
 */
public class FenetreModifierClient extends FenetreAjouterClient {

    int indexTableau;
    private Client client;


    public FenetreModifierClient(JFrame parent, TableauClients tableauClients, int indexTableau) {
        super(parent, tableauClients);
        this.indexTableau = indexTableau;
        int idClient = (int) tableauClients.getValueAt(indexTableau, 0);
        this.client = ClientsDB.getById(idClient);

        champNom.setText(client.getNom());
        champPrenom.setText(client.getPrenom());
        champAdresse.setText(client.getAdresse());
        champEmail.setText(client.getEmail());

        bValider.removeActionListener(bValider.getActionListeners()[0]);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierClient();
            }
        });
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
