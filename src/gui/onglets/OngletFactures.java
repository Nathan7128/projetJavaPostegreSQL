package gui.onglets;

import gui.dialogues.FenetreAjouterFacture;
import gui.tableaux.TableauFactures;
import tablesDB.FacturesDB;
import tablesJava.Facture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletFactures extends Onglet {
    private FacturesDB facturesDB = new FacturesDB();
    private TableauFactures tableau = new TableauFactures();
    private JButton bAjouter;

    public OngletFactures() {
        super("Factures", "src/gui/images/icone_factures.png");

        JScrollPane tableau_defilant = new JScrollPane(new JTable(tableau));
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // marges pour centrer visuellement

        add(tableau_defilant, BorderLayout.CENTER);
        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterFacture();
            }
        });

        add(bAjouter, BorderLayout.SOUTH);
    }

    public void ajouterFacture() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterFacture fenetreAjouterFacture = new FenetreAjouterFacture((JFrame) parent);
        Facture facture = fenetreAjouterFacture.afficherEtRecuperer();
        tableau.addDonnee(facture);
    }
}
