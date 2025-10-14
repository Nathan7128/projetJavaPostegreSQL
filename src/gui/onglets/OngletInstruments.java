package gui.onglets;

import gui.tableaux.TableauInstruments;
import tablesDB.InstrumentsDB;
import tablesDB.ModelesDB;
import tablesJava.Instrument;
import gui.dialogues.FenetreAjouterInstrument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class OngletInstruments extends Onglet {
    private InstrumentsDB instrumentsDB = new InstrumentsDB();
    private TableauInstruments tableau = new TableauInstruments();
    private JTable jTableau;
    private JScrollPane tableau_defilant;
    private JButton bAjouter, bSupprimer;

    public OngletInstruments() {
        super("Instruments", "src/gui/images/icone_instruments.png");

        construireTableau();

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

    public void ajouterInstrument() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterInstrument fenetreAjouterInstrument = new FenetreAjouterInstrument((JFrame) parent);
        Instrument instrument = fenetreAjouterInstrument.afficherEtRecuperer();
        tableau.addDonnee(instrument);
    }

    public void supprimerInstrument() {
        int[] selection = jTableau.getSelectedRows();

        if (selection.length > 0) {
            int option = JOptionPane.showConfirmDialog(this,
                    "La suppression de ce/ces instruments supprimera les factures associées, voulez vous continuer ?",
                    "Suppression de l'instrument",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(option == JOptionPane.OK_OPTION) {
                for(int i = selection.length - 1; i >= 0; i--){
                    int index = selection[i];
                    int id_instrument = (int) tableau.getValueAt(index, 0);
                    tableau.supprDonnee(index);
                    InstrumentsDB.delete(id_instrument);
                }
            }
        }
    }
}
