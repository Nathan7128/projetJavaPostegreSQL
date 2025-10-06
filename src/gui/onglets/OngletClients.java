package gui.onglets;

import gui.dialogues.FenetreAjouterClient;
import gui.tableaux.TableauClients;
import tablesDB.ClientsDB;
import tablesJava.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletClients extends Onglet {
    private ClientsDB clientsDB = new ClientsDB();
    private TableauClients tableau = new TableauClients();
    private JButton bAjouter;

    public OngletClients() {
        super("Clients", "src/gui/images/icone_clients.png");

        JScrollPane tableau_defilant = new JScrollPane(new JTable(tableau));
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // marges pour centrer visuellement

        add(tableau_defilant, BorderLayout.CENTER);
        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterClient();
            }
        });

        add(bAjouter, BorderLayout.SOUTH);
    }

    public void ajouterClient() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterClient fenetreAjouterClient = new FenetreAjouterClient((JFrame) parent);
        Client client = fenetreAjouterClient.afficherEtRecuperer();
        tableau.addDonnee(client);
    }
}
