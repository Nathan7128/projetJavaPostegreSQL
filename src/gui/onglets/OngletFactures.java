package gui.onglets;

import gui.fenetresajouter.FenetreAjouterFacture;
import gui.tableaux.TableauFactures;
import tablesDB.FacturesDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletFactures extends Onglet {
    private TableauFactures tableau = new TableauFactures();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer;

    public OngletFactures() {
        super("Factures", "src/gui/images/icone_factures.png");

        construireTableau();

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

        ;
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

    public void ajouterFacture() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterFacture fenetreAjouterFacture = new FenetreAjouterFacture((JFrame) parent, tableau);
        fenetreAjouterFacture.setVisible(true);
    }

    public void supprimerFacture() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = selection[i];
            int id_facture = (int) tableau.getValueAt(index, 0);
            tableau.supprDonnee(index);
            FacturesDB.delete(id_facture);
        }
    }
}
