package gui.onglets;

import gui.fenetresajouter.FenetreAjouterModele;
import gui.tableaux.TableauModeles;
import tablesdb.ModelesDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletModeles extends Onglet {
    private TableauModeles tableau = new TableauModeles();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer;

    public OngletModeles() {
        super("Modeles", "src/gui/images/icone_modeles.png");

        construireTableau();

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

    public void ajouterModele() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterModele fenetreAjouterModele = new FenetreAjouterModele((JFrame) parent, tableau);
        fenetreAjouterModele.setVisible(true);
    }

    public void supprimerModele() {
        int[] selection = jTableau.getSelectedRows();

        for(int i = selection.length - 1; i >= 0; i--){
            int index = selection[i];
            int id_modele = (int) tableau.getValueAt(index, 0);
            tableau.supprDonnee(index);
            ModelesDB.delete(id_modele);
        }
    }
}
