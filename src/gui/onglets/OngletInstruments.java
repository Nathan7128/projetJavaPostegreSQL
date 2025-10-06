package gui.onglets;

import gui.tableaux.TableauInstruments;
import tablesDB.InstrumentsDB;
import tablesJava.Instrument;
import gui.dialogues.FenetreAjouterInstrument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class OngletInstruments extends Onglet {
    private InstrumentsDB instrumentsDB = new InstrumentsDB();
    private TableauInstruments tableau = new TableauInstruments();
    private JButton bAjouter;

    public OngletInstruments() {
        super("Instruments", "src/gui/images/icone_instruments.png");

        JScrollPane tableau_defilant = new JScrollPane(new JTable(tableau));
        tableau_defilant.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // marges pour centrer visuellement

        add(tableau_defilant, BorderLayout.CENTER);
        bAjouter = new JButton("Ajouter");
        bAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterInstrument();
            }
        });

        add(bAjouter, BorderLayout.SOUTH);
    }

    public void ajouterInstrument() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterInstrument fenetreAjouterInstrument = new FenetreAjouterInstrument((JFrame) parent);
        Instrument instrument = fenetreAjouterInstrument.afficherEtRecuperer();
        tableau.addDonnee(instrument);
    }
}
