package gui.onglets;

import gui.dialogues.FenetreAjouterMarque;
import gui.tableaux.TableauMarques;
import tablesDB.MarquesDB;
import tablesDB.ModelesDB;
import tablesJava.Marque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngletMarques extends Onglet {
    private MarquesDB marquesDB = new MarquesDB();
    private TableauMarques tableau = new TableauMarques();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer;

    public OngletMarques() {
        super("Marques", "src/gui/images/icone_marques.png");

        construireTableau();

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

    public void rafraichir() {
        tableau.rafraichir();
    }

    public void ajouterMarque() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterMarque fenetreAjouterMarque = new FenetreAjouterMarque((JFrame) parent);
        Marque marque = fenetreAjouterMarque.afficherEtRecuperer();
        tableau.addDonnee(marque);
    }

    public void supprimerMarque() {
        int[] selection = jTableau.getSelectedRows();

        if (selection.length > 0) {
            int option = JOptionPane.showConfirmDialog(this,
                    "La suppression de cette/ces marques supprimera les modèles associés, voulez vous continuer ?",
                    "Suppression de la marque",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(option == JOptionPane.YES_OPTION) {
                for(int i = selection.length - 1; i >= 0; i--){
                    int index = selection[i];
                    int id_marque = (int) tableau.getValueAt(index, 0);
                    tableau.supprDonnee(index);
                    MarquesDB.delete(id_marque);
                }
            }
        }
    }
}
