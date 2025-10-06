package gui.onglets;

import gui.dialogues.FenetreAjouterMarque;
import gui.tableaux.TableauMarques;
import tablesDB.MarquesDB;
import tablesJava.Marque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletMarques extends Onglet {
    private MarquesDB marquesDB = new MarquesDB();
    private TableauMarques tableau = new TableauMarques();
    private JButton bAjouter;

    public OngletMarques() {
        super("Marques", "src/gui/images/icone_marques.png");

        JScrollPane tableau_defilant = new JScrollPane(new JTable(tableau));
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // marges pour centrer visuellement

        add(tableau_defilant, BorderLayout.CENTER);
        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMarque();
            }
        });

        add(bAjouter, BorderLayout.SOUTH);
    }

    public void ajouterMarque() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterMarque fenetreAjouterMarque = new FenetreAjouterMarque((JFrame) parent);
        Marque marque = fenetreAjouterMarque.afficherEtRecuperer();
        tableau.addDonnee(marque);
    }
}
