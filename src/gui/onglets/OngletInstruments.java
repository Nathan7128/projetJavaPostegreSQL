package gui.onglets;


// Importation des bibliothèques internes
import gui.fenetresafficher.FenetreAfficherInstrument;
import gui.fenetresmodifier.FenetreModifierInstrument;
import gui.tableaux.TableauInstruments;
import tablesdb.InstrumentsDB;
import gui.fenetresajouter.FenetreAjouterInstrument;


// Importation des bibliothèques externes
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


/**
 * Classe implémentant l'onglet permettant de gérer les instruments dans l'application
 */
public class OngletInstruments extends Onglet {
    
    private TableauInstruments tableauInstruments = new TableauInstruments();
    private JTable jTableau;
    private JScrollPane tableauDefilant;
    private JButton bAjouter, bSupprimer, bModifier, bAfficher;


    public OngletInstruments() {
        super("Instruments");

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
                ajouterInstrument();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerInstrument();
            }
        });
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierInstrument();
            }
        });
        bAfficher = new JButton("Afficher");
        bAfficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    afficherInstrument();
                }
                catch (Exception ex) {
                }
            }
        });

        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);
        boutons.add(bAfficher);

        add(boutons, BorderLayout.SOUTH);
    }

    /**
     * Construit le tableau contenant les instruments et qui est affiché dans l'onglet
     */
    private void construireTableau() {
        jTableau = new JTable(tableauInstruments);
        TableRowSorter<TableModel> trieurLignesTableau = new TableRowSorter<>(tableauInstruments);
        jTableau.setRowSorter(trieurLignesTableau);
        trieurLignesTableau.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableauDefilant, BorderLayout.CENTER);
    }

    public void ajouterInstrument() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterInstrument fenetreAjouterInstrument = new FenetreAjouterInstrument((JFrame) parent, tableauInstruments);
        fenetreAjouterInstrument.setVisible(true);
    }

    public void supprimerInstrument() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = jTableau.convertRowIndexToModel(selection[i]);
            int idInstrument = (int) tableauInstruments.getValueAt(index, 0);
            tableauInstruments.supprDonnee(index);
            InstrumentsDB.supprimer(idInstrument);
        }
    }

    public void modifierInstrument() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 instrument à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = jTableau.convertRowIndexToModel(selection[0]);
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierInstrument fenetreModifierInstrument = new FenetreModifierInstrument((JFrame) parent, tableauInstruments, index);
            fenetreModifierInstrument.setVisible(true);
        }
    }

    public void afficherInstrument() throws Exception {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 instrument à afficher.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }

        int index = jTableau.convertRowIndexToModel(selection[0]);
        int idInstrument = (int) tableauInstruments.getValueAt(index, 0);
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAfficherInstrument fenetreAfficherInstrument = new FenetreAfficherInstrument((JFrame) parent, idInstrument);
    }
}
