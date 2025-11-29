package gui.fenetresajouter;


// Importation des bibliothèques internes
import tablesdb.InstrumentsDB;
import tablesdb.MarquesDB;
import tablesdb.ModelesDB;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;


/**
 * Cette classe représente une fenêtre de dialogue permettant d'ajouter une ligne de facture.
 * Elle est instanciée lorsque l'utilisateur clique sur "Ajouter" une ligne de facture, et permet de
 * saisir les informations de la ligne de facture via sa boite de dialogue.
 */
public class FenetreAjouterLigneFacture extends JDialog {

    private Map<String, Integer> allIDsMarques;
    private JComboBox champMarque;
    private Map<String, Integer> allIDsModeles;
    private String[] listeNomsModele;
    private JComboBox champModele;
    private Map<String, Integer> allIDsInstruments;
    private String[] listeNomsInstrument;
    private JComboBox champInstrument;
    private JComboBox champLigneFacture;
    private JButton bValider;
    private JButton bAnnuler;

    public FenetreAjouterLigneFacture(JFrame parent, JComboBox champLigneFacture) {
        super(parent, "Ajouter un instrument à la facture", true);

        this.champLigneFacture = champLigneFacture;

        setLayout(new BorderLayout(10, 10));

        creerChamps();
        creerBoutons();

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerChamps() {
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        allIDsMarques = MarquesDB.getIdsMarque();
        champMarque = new JComboBox(allIDsMarques.keySet().toArray());
        panelForm.add(new JLabel("Filtrer marque :"));
        panelForm.add(champMarque);
        champMarque.setSelectedItem(null);
        champMarque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrerModeles();
            }
        });

        allIDsModeles = ModelesDB.getIdsModele();
        listeNomsModele = allIDsModeles.keySet().toArray(new String[0]);
        champModele = new JComboBox(Arrays.stream(listeNomsModele).sorted().toArray(String[]::new));
        panelForm.add(new JLabel("Filtrer modèle :"));
        panelForm.add(champModele);
        champModele.setSelectedItem(null);
        champModele.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrerInstruments();
            }
        });

        allIDsInstruments = InstrumentsDB.getIdsInstrument();
        listeNomsInstrument = allIDsInstruments.keySet().toArray(new String[0]);
        champInstrument = new JComboBox(allIDsInstruments.keySet().toArray());
        panelForm.add(new JLabel("Choisir instrument :"));
        panelForm.add(champInstrument);
        champInstrument.setSelectedItem(null);

        add(panelForm, BorderLayout.CENTER);
    }

    private void creerBoutons() {
        JPanel panelBoutons = new JPanel();
        bValider = new JButton("Valider");
        panelBoutons.add(bValider);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterLigneFacture();
            }
        });

        bAnnuler = new JButton("Annuler");
        panelBoutons.add(bAnnuler);
        bAnnuler.addActionListener(e -> dispose());

        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void filtrerModeles() {
        String modeleActuel = (String) champModele.getSelectedItem();
        int idMarque = allIDsMarques.get((String) champMarque.getSelectedItem());
        allIDsModeles = ModelesDB.getIdsModele(idMarque);
        listeNomsModele = allIDsModeles.keySet().toArray(new String[0]);
        champModele.removeAllItems();
        for (String nomModele : listeNomsModele) {
            champModele.addItem(nomModele);
        }
        if (Arrays.asList(listeNomsModele).contains(modeleActuel)) {
            champModele.setSelectedItem(modeleActuel);
        }
    }

    private void filtrerInstruments() {
        String instrumentActuel = (String) champInstrument.getSelectedItem();
        String modeleActuel = (String) champModele.getSelectedItem();
        champInstrument.removeAllItems();
        if (modeleActuel != null) {
            int idModele = allIDsModeles.get(modeleActuel);
            allIDsInstruments = InstrumentsDB.getIdsInstrument(idModele);
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

    private void ajouterLigneFacture() {
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
