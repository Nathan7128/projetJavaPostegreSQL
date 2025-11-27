package gui.onglets;


// Importation des bibliothèques internes
import gui.fenetresajouter.FenetreAjouterModele;
import gui.fenetresmodifier.FenetreModifierModele;
import gui.tableaux.TableauModeles;
import tablesdb.ModelesDB;

// Importation des bibliothèques externes
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe implémentant l'onglet permettant de gérer les modèles dans l'application
 */
public class OngletModeles extends Onglet {
    
    private TableauModeles tableauModeles = new TableauModeles();
    private JTable jTableau;
    private JScrollPane tableauDefilant;
    private JButton bAjouter, bSupprimer, bModifier;

    public OngletModeles() {
        super("Modeles");

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
                ajouterModele();
            }
        });
        bSupprimer = new JButton("Supprimer");
        bSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerModele();
            }
        });
        bModifier = new JButton("Modifier");
        bModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierModele();
            }
        });

        JPanel boutons = new JPanel();
        boutons.add(bAjouter);
        boutons.add(bSupprimer);
        boutons.add(bModifier);

        add(boutons, BorderLayout.SOUTH);
    }

    /**
     * Construit le tableau contenant les modèles et qui est affiché dans l'onglet
     */
    private void construireTableau() {
        jTableau = new JTable(tableauModeles);
        TableRowSorter<TableModel> trieurLignesTableau = new TableRowSorter<>(tableauModeles);
        jTableau.setRowSorter(trieurLignesTableau);
        trieurLignesTableau.toggleSortOrder(0);

        tableauDefilant = new JScrollPane(jTableau);
        tableauDefilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(tableauDefilant, BorderLayout.CENTER);
    }

    public void ajouterModele() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterModele fenetreAjouterModele = new FenetreAjouterModele((JFrame) parent, tableauModeles);
        fenetreAjouterModele.setVisible(true);
    }

    public void supprimerModele() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = jTableau.convertRowIndexToModel(selection[i]);
            int idModele = (int) tableauModeles.getValueAt(index, 0);
            tableauModeles.supprDonnee(index);
            ModelesDB.supprimer(idModele);
        }
    }

    public void modifierModele() {
        int[] selection = jTableau.getSelectedRows();
        if (selection.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir exactement 1 modèle à modifier.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            int index = jTableau.convertRowIndexToModel(selection[0]);
            Window parent = SwingUtilities.getWindowAncestor(this);
            FenetreModifierModele fenetreModifierModele = new FenetreModifierModele((JFrame) parent, tableauModeles, index);
            fenetreModifierModele.setVisible(true);
        }
    }
}
