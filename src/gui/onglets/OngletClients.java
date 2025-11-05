package gui.onglets;

import gui.fenetresajouter.FenetreAjouterClient;
import gui.fenetresmodifier.FenetreModifierClient;
import gui.tableaux.TableauClients;
import tablesdb.ClientsDB;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletClients extends Onglet {
    private TableauClients tableau = new TableauClients();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer, bModifier;

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
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierClient();
            }
        });

        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);

        add(boutons, BorderLayout.SOUTH);
    }

    private void construireTableau() {
        jTableau = new JTable(tableau);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableau);
        jTableau.setRowSorter(sorter);
        sorter.toggleSortOrder(0);

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

    public void modifierClient() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 client à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = selection[0];
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierClient fenetreModifierClient = new FenetreModifierClient((JFrame) parent, tableau, index);
            fenetreModifierClient.setVisible(true);
        }
    }
}
