package gui.onglets;

import gui.fenetresajouter.FenetreAjouterClient;
import gui.tableaux.TableauClients;
import tablesDB.ClientsDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletClients extends Onglet {
    private TableauClients tableau = new TableauClients();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer;

    public OngletClients() {
        super("Clients", "src/gui/images/icone_clients.png");

        construireTableau();

        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterClient();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerClient();
            }
        });

        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);

        add(boutons, BorderLayout.SOUTH);
    }

    private void construireTableau() {
        jTableau = new JTable(tableau);
        tableau_defilant = new JScrollPane(jTableau);
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableau_defilant, BorderLayout.CENTER);
    }

    public void ajouterClient() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterClient fenetreAjouterClient = new FenetreAjouterClient((JFrame) parent, tableau);
        fenetreAjouterClient.setVisible(true);
    }

    public void supprimerClient() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = selection[i];
            int id_client = (int) tableau.getValueAt(index, 0);
            tableau.supprDonnee(index);
            ClientsDB.delete(id_client);
        }
    }
}
