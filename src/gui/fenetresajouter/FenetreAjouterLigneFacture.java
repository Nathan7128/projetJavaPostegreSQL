package gui.fenetresajouter;

import tablesdb.InstrumentsDB;
import tablesdb.LignesFacturesDB;
import tablesdb.MarquesDB;
import tablesdb.ModelesDB;
import tablesjava.LigneFacture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

public class FenetreAjouterLigneFacture extends JDialog {

    private final Map<String, Integer> allIDsMarques = MarquesDB.getAllIDsMarques();
    private final JComboBox champMarque = new JComboBox(allIDsMarques.keySet().toArray());
    private Map<String, Integer> allIDsModeles = ModelesDB.getAllIDsModeles();
    private String[] listeNomsModele = allIDsModeles.keySet().toArray(new String[0]);
    private JComboBox champModele = new JComboBox(Arrays.stream(listeNomsModele).sorted().toArray(String[]::new));
    private Map<String, Integer> allIDsInstruments = InstrumentsDB.getAllIDsInstruments();
    private String[] listeNomsInstrument = allIDsInstruments.keySet().toArray(new String[0]);
    private JComboBox champInstrument = new JComboBox(allIDsInstruments.keySet().toArray());

    private final JComboBox champLigneFacture ;

    public FenetreAjouterLigneFacture(JFrame parent, JComboBox champLigneFacture) {
        super(parent, "Ajouter un instrument à la facture", true);
        this.champLigneFacture = champLigneFacture;
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

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        JButton boutonValider = new JButton("Valider");
        JButton boutonAnnuler = new JButton("Annuler");

        panelBoutons.add(boutonValider);
        panelBoutons.add(boutonAnnuler);
        add(panelBoutons, BorderLayout.SOUTH);

        boutonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterLigneFacture();
            }
        });

        boutonAnnuler.addActionListener(e -> dispose());

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

    public void ajouterLigneFacture() {
        String instrumentSel = (String) champInstrument.getSelectedItem();
//        On vérifie si l'instrument n'a pas déjà été ajouté
        boolean instrumentAjt = false;
        int i = 0;
        while (i < champLigneFacture.getItemCount() & !instrumentAjt) {
            if (((String) champLigneFacture.getItemAt(i)).equals(instrumentSel)) {
                instrumentAjt = true;
            }
            i++;
        }
        if (instrumentAjt) {
            JOptionPane.showMessageDialog(this,
                    "Erreur, cet instrument a déjà été ajouté à cette facture.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }

//        Sinon, on vérifie si un instrument a bien été sélectionné
        else if (instrumentSel == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir un instrument.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }

//         Si tout est correct
        else {
            champLigneFacture.addItem(instrumentSel);
            dispose();
        }
    }
}
