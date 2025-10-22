package gui.fenetresajouter;

import gui.tableaux.TableauInstruments;
import tablesDB.InstrumentsDB;
import tablesDB.ModelesDB;
import tablesJava.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class FenetreAjouterInstrument extends JDialog {

    private Instrument instrumentCree = null;

    private final JTextField champNumSerie = new JTextField(15);
    private final Map<String, Integer> allIDsModeles = ModelesDB.getAllIDsModeles();
    private final JComboBox champModele = new JComboBox(allIDsModeles.keySet().toArray());
    private final JTextField champCouleur = new JTextField(15);
    private final JSpinner champPrix = new JSpinner((new SpinnerNumberModel(0, 0, 10000, 1)));
    private String champPhoto = null;
    private final JFileChooser selectPhoto = new JFileChooser();
    private TableauInstruments tableauInstruments;

    public FenetreAjouterInstrument(JFrame parent, TableauInstruments tableauInstruments) {
        super(parent, "Ajouter un instrument", true);
        this.tableauInstruments = tableauInstruments;
        setLayout(new BorderLayout(10, 10));

        creerSelecteurPhoto();

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 20, 20));

        panelForm.add(new JLabel("Numéro de série :"));
        panelForm.add(champNumSerie);

        panelForm.add(new JLabel("Modèle :"));
        panelForm.add(champModele);

        panelForm.add(new JLabel("Couleur :"));
        panelForm.add(champCouleur);

        panelForm.add(new JLabel("Prix (€) :"));
        panelForm.add(champPrix);

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
                creerInstrument();
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
    }

    private void choisirPhoto() {
        int r = selectPhoto.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            champPhoto = selectPhoto.getSelectedFile().getName();
        }
    }

    private void creerInstrument() {
        int id_instrument = InstrumentsDB.createNewId();
        String num_serie = champNumSerie.getText().trim();
        int id_modele = allIDsModeles.get((String) champModele.getSelectedItem());
        String couleur = champCouleur.getText().trim();
        int prix = (int) champPrix.getValue();

        // Vérifie si un champ obligatoire est vide
        if (num_serie.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Numéro de série).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            instrumentCree = new Instrument(id_instrument, num_serie, id_modele, couleur, prix, champPhoto);
            InstrumentsDB.add(instrumentCree);
            tableauInstruments.addDonnee(instrumentCree);
            dispose();
        }
    }
}