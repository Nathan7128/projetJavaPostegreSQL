package gui.onglets;

import gui.dialogues.FenetreAjouterModele;
import gui.tableaux.TableauModeles;
import tablesDB.ModelesDB;
import tablesJava.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletModeles extends Onglet {
    private ModelesDB modelesDB = new ModelesDB();
    private TableauModeles tableau = new TableauModeles();
    private JButton bAjouter;

    public OngletModeles() {
        super("Modeles", "src/gui/images/icone_modeles.png");

        JScrollPane tableau_defilant = new JScrollPane(new JTable(tableau));
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // marges pour centrer visuellement

        add(tableau_defilant, BorderLayout.CENTER);
        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterModele();
            }
        });

        add(bAjouter, BorderLayout.SOUTH);
    }

    public void ajouterModele() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterModele fenetreAjouterModele = new FenetreAjouterModele((JFrame) parent);
        Modele modele = fenetreAjouterModele.afficherEtRecuperer();
        tableau.addDonnee(modele);
    }
}
