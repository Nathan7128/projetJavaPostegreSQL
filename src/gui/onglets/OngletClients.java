package gui.onglets;


// Importation des bibliothèques internes
import gui.fenetresajouter.FenetreAjouterClient;
import gui.fenetresmodifier.FenetreModifierClient;
import gui.tableaux.TableauClients;
import tablesdb.ClientsDB;

// Importation des bibliothèques externes
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe implémentant l'onglet permettant de gérer les clients dans l'application
 */
public class OngletClients extends Onglet {
    
    private TableauClients tableauClients = new TableauClients();
    private JTable jTableau;
    private JScrollPane tableauDefilant;
    private JButton bAjouter, bSupprimer, bModifier;


    public OngletClients() {
        super("Clients");

        construireTableau();

        construireBoutons();
    }


    /**
     * Construit les boutons présents dans l'onglet
     */
    private void construireBoutons() {
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

    /**
     * Construit le tableau contenant les clients et qui est affiché dans l'onglet
     */
    private void construireTableau() {
        jTableau = new JTable(tableauClients);
        TableRowSorter<TableModel> trieurLignesTableau = new TableRowSorter<>(tableauClients);
        jTableau.setRowSorter(trieurLignesTableau);
        trieurLignesTableau.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableauDefilant, BorderLayout.CENTER);
    }

    public void ajouterClient() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterClient fenetreAjouterClient = new FenetreAjouterClient((JFrame) parent, tableauClients);
        fenetreAjouterClient.setVisible(true);
    }

    public void supprimerClient() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = jTableau.convertRowIndexToModel(selection[i]);
            int idClient = (int) tableauClients.getValueAt(index, 0);
            tableauClients.supprDonnee(index);
            ClientsDB.supprimer(idClient);
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
            int index = jTableau.convertRowIndexToModel(selection[0]);
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierClient fenetreModifierClient = new FenetreModifierClient((JFrame) parent, tableauClients, index);
            fenetreModifierClient.setVisible(true);
        }
    }
}
