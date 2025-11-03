package gui.fenetresmodifier;

import gui.tableaux.TableauInstruments;
import tablesdb.InstrumentsDB;
import tablesdb.ModelesDB;
import tablesjava.Instrument;
import tablesjava.Modele;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class FenetreModifierInstrument extends JDialog {

    private Instrument instrumentCree = null;

    private final JTextField champNumSerie = new JTextField(15);
    private final Map<String, Integer> allIDsModeles = ModelesDB.getAllIDsModeles();
    private final JComboBox champModele = new JComboBox(allIDsModeles.keySet().toArray());
    private final JTextField champCouleur = new JTextField(15);
    private final JSpinner champPrix = new JSpinner((new SpinnerNumberModel(0, 0, 10000, 1)));
    private String champPhoto = null;
    private final JFileChooser selectPhoto = new JFileChooser();
    private TableauInstruments tableauInstruments;
    int indexTableau;
    private Instrument instrument;

    public FenetreModifierInstrument(JFrame parent, TableauInstruments tableauInstruments, int indexTableau) {
        super(parent, "Modifier l'instrument", true);
        this.tableauInstruments = tableauInstruments;
        this.indexTableau = indexTableau;
        int idInstrument = (int) tableauInstruments.getValueAt(indexTableau, 0);
        this.instrument = InstrumentsDB.findById(idInstrument);
        setLayout(new BorderLayout(10, 10));

        creerSelecteurPhoto();

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 20, 20));

        panelForm.add(new JLabel("Numéro de série :"));
        panelForm.add(champNumSerie);
        champNumSerie.setText(instrument.getNumSerie());

        panelForm.add(new JLabel("Modèle :"));
        panelForm.add(champModele);
        Modele modele = ModelesDB.findById(instrument.getIdModele());
        champModele.setSelectedItem(modele.getId() + " - " + modele.getNom());

        panelForm.add(new JLabel("Couleur :"));
        panelForm.add(champCouleur);
        champCouleur.setText(instrument.getCouleur());

        panelForm.add(new JLabel("Prix (€) :"));
        panelForm.add(champPrix);
        champPrix.setValue(instrument.getPrix());

        panelForm.add(new JLabel("Photo :"));
        JButton bPhoto = new JButton("Choisir un fichier");
        bPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choisirPhoto();
            }
        });
        panelForm.add(bPhoto);

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
                modifierInstrument();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerSelecteurPhoto() {
        File dossierFixe = new File(System.getProperty("user.dir") + "/imagesInstruments");
        selectPhoto.setCurrentDirectory(dossierFixe);
        selectPhoto.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter typeFichier = new FileNameExtensionFilter("Images (png, jpg ou jpeg)", "png", "jpg", "jpeg");
        selectPhoto.addChoosableFileFilter(typeFichier);
        if (instrument.getPhoto() != null) {
            selectPhoto.setSelectedFile(new File(instrument.getPhoto()));
        }
    }

    private void choisirPhoto() {
        int r = selectPhoto.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            champPhoto = selectPhoto.getSelectedFile().getName();
        }
    }

    private void modifierInstrument() {
        String numSerie = champNumSerie.getText().trim();
        int idModele = allIDsModeles.get((String) champModele.getSelectedItem());
        String couleur = champCouleur.getText().trim();
        int prix = (int) champPrix.getValue();

        // Vérifie si un champ obligatoire est vide
        if (numSerie.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Numéro de série).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            this.instrument.setNumSerie(numSerie);
            this.instrument.setIdModele(idModele);
            this.instrument.setCouleur(couleur);
            this.instrument.setPrix(prix);
            this.instrument.setPhoto(champPhoto);
            InstrumentsDB.update(this.instrument.getId(), numSerie, idModele, couleur, prix, champPhoto);
            tableauInstruments.modifierLigne(this.indexTableau, this.instrument);
            dispose();
        }
    }
}
