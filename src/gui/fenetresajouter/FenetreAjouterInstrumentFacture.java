package gui.fenetresajouter;

import tablesdb.InstrumentsDB;
import tablesdb.MarquesDB;
import tablesdb.ModelesDB;
import tablesjava.Instrument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class FenetreAjouterInstrumentFacture extends JDialog {

    private final Map<String, Integer> allIDsMarques = MarquesDB.getAllIDsMarques();
    private final JComboBox champMarque = new JComboBox(allIDsMarques.keySet().toArray());
    private Map<String, Integer> allIDsModeles = ModelesDB.getAllIDsModeles();
    private String[] listeNomsModele = allIDsModeles.keySet().toArray(new String[0]);
    private JComboBox champModele = new JComboBox(listeNomsModele);
    private Map<String, Integer> allIDsInstruments = InstrumentsDB.getAllIDsInstruments();
    private String[] listeNomsInstrument = allIDsInstruments.keySet().toArray(new String[0]);
    private JComboBox champInstrument = new JComboBox(allIDsInstruments.keySet().toArray());

    private final JComboBox champListeInstrument ;

    public FenetreAjouterInstrumentFacture(JFrame parent, JComboBox champListeInstrument) {
        super(parent, "Ajouter un instrument à la facture", true);
        this.champListeInstrument = champListeInstrument;
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        panelForm.add(new JLabel("Filtrer marque :"));
        panelForm.add(champMarque);
        champMarque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrerModeles();
            }
        });

        panelForm.add(new JLabel("Filtrer modèle :"));
        panelForm.add(champModele);
        champModele.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrerInstruments();
            }
        });

        panelForm.add(new JLabel("Choisir instrument :"));
        panelForm.add(champInstrument);

        add(panelForm, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
    }

    public void filtrerModeles() {
        String modeleActuel = (String) champModele.getSelectedItem();
        int idMarque = allIDsMarques.get((String) champMarque.getSelectedItem());
        allIDsModeles = ModelesDB.getAllIDsModeles(idMarque);
        listeNomsModele = allIDsModeles.keySet().toArray(new String[0]);
        champModele.removeAllItems();
        for (String nomModele : listeNomsModele) {
            champModele.addItem(nomModele);
        }
        if (Arrays.asList(listeNomsModele).contains(modeleActuel)) {
            champModele.setSelectedItem(modeleActuel);
        }
    }

    public void filtrerInstruments() {
        String instrumentActuel = (String) champInstrument.getSelectedItem();
        String modeleActuel = (String) champModele.getSelectedItem();
        champInstrument.removeAllItems();
        if (modeleActuel != null) {
            int idModele = allIDsModeles.get(modeleActuel);
            allIDsInstruments = InstrumentsDB.getAllIDsInstruments(idModele);
            listeNomsInstrument = allIDsInstruments.keySet().toArray(new String[0]);
            for (String nomInstrument : listeNomsInstrument) {
                champInstrument.addItem(nomInstrument);
            }
            if (Arrays.asList(listeNomsInstrument).contains(instrumentActuel)) {
                champInstrument.setSelectedItem(instrumentActuel);
            }
        }
        else {
            champInstrument.setSelectedItem(null);
        }
    }
}
