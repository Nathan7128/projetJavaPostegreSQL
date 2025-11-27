package gui.onglets;


// Importation des bibliothèques internes
import gui.fenetresajouter.FenetreAjouterMarque;
import gui.fenetresmodifier.FenetreModifierMarque;
import gui.tableaux.TableauMarques;
import tablesdb.MarquesDB;


// Importation des bibliothèques externes
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe implémentant l'onglet permettant de gérer les marques dans l'application
 */
public class OngletMarques extends Onglet {
    
    private TableauMarques tableauMarques = new TableauMarques();
    private JTable jTableau;
    private JScrollPane tableauDefilant;
    private JButton bAjouter, bSupprimer, bModifier;

    public OngletMarques() {
        super("Marques");

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
                ajouterMarque();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerMarque();
            }
        });
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierMarque();
            }
        });

        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);

        add(boutons, BorderLayout.SOUTH);
    }

    /**
     * Construit le tableau contenant les marques et qui est affiché dans l'onglet
     */
    private void construireTableau() {
        jTableau = new JTable(tableauMarques);
        TableRowSorter<TableModel> trieurLignesTableau = new TableRowSorter<>(tableauMarques);
        jTableau.setRowSorter(trieurLignesTableau);
        trieurLignesTableau.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableauDefilant, BorderLayout.CENTER);
    }

    public void ajouterMarque() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterMarque fenetreAjouterMarque = new FenetreAjouterMarque((JFrame) parent, tableauMarques);
        fenetreAjouterMarque.setVisible(true);
    }

    public void supprimerMarque() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = jTableau.convertRowIndexToModel(selection[i]);
            int idMarque = (int) tableauMarques.getValueAt(index, 0);
            tableauMarques.supprDonnee(index);
            MarquesDB.supprimer(idMarque);
        }
    }

    public void modifierMarque() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 marque à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = jTableau.convertRowIndexToModel(selection[0]);
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierMarque fenetreModifierMarque = new FenetreModifierMarque((JFrame) parent, tableauMarques, index);
            fenetreModifierMarque.setVisible(true);
        }
    }
}
