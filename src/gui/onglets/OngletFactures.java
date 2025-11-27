package gui.onglets;


// Importation des bibliothèques internes
import gui.fenetresafficher.FenetreAfficherFacture;
import gui.fenetresajouter.FenetreAjouterFacture;
import gui.fenetresmodifier.FenetreModifierFacture;
import gui.tableaux.TableauFactures;
import tablesdb.FacturesDB;
import tablesdb.LignesFactureDB;
import tablesjava.Facture;


// Importation des bibliothèques externes
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe implémentant l'onglet permettant de gérer les factures dans l'application
 */
public class OngletFactures extends Onglet {
    
    private TableauFactures tableauFactures = new TableauFactures();
    private JTable jTableau;
    private JScrollPane tableauDefilant;
    private JButton bAjouter, bSupprimer, bModifier, bAfficher;


    public OngletFactures() {
        super("Factures");

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
                ajouterFacture();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerFacture();
            }
        });
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierFacture();
            }
        });
        bAfficher = new JButton("Afficher");
        bAfficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherFacture();
            }
        });

        ;
        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);
        boutons.add(bAfficher);

        add(boutons, BorderLayout.SOUTH);
    }

    /**
     * Construit le tableau contenant les factures et qui est affiché dans l'onglet
     */
    private void construireTableau() {
        jTableau = new JTable(tableauFactures);
        TableRowSorter<TableModel> trieurLignesTableau = new TableRowSorter<>(tableauFactures);
        jTableau.setRowSorter(trieurLignesTableau);
        trieurLignesTableau.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableauDefilant, BorderLayout.CENTER);
    }

    public void ajouterFacture() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterFacture fenetreAjouterFacture = new FenetreAjouterFacture((JFrame) parent, tableauFactures);
        fenetreAjouterFacture.setVisible(true);
    }

    public void supprimerFacture() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = jTableau.convertRowIndexToModel(selection[i]);
            int idFacture = (int) tableauFactures.getValueAt(index, 0);
            tableauFactures.supprDonnee(index);
            LignesFactureDB.supprLignesFacture(idFacture);
            FacturesDB.supprimer(idFacture);
        }
    }

    public void modifierFacture() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 facture à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = jTableau.convertRowIndexToModel(selection[0]);
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierFacture fenetreModifierFacture = new FenetreModifierFacture((JFrame) parent, tableauFactures, index);
            fenetreModifierFacture.setVisible(true);
        }
    }

    public void afficherFacture() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 facture à afficher.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }

        int index = jTableau.convertRowIndexToModel(selection[0]);
        int idFacture = (int) tableauFactures.getValueAt(index, 0);
        Facture facture = FacturesDB.getById(idFacture);
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAfficherFacture fenetreAfficherFacture = new FenetreAfficherFacture((JFrame) parent, facture);
        fenetreAfficherFacture.setVisible(true);
    }
}
