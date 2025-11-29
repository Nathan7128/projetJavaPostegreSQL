package gui.fenetresajouter;


// Importation des bibliothèques internes
import gui.tableaux.TableauInstruments;
import tablesdb.InstrumentsDB;
import tablesdb.ModelesDB;
import tablesjava.*;

// Importation des bibliothèques externes
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;


/**
 * Cette classe représente une fenêtre de dialogue permettant d'ajouter un instrument.
 * Elle est instanciée lorsque l'utilisateur clique sur "Ajouter" un instrument, et permet de
 * saisir les informations du instrument via sa boite de dialogue.
 */
public class FenetreAjouterInstrument extends JDialog {

    protected JTextField champNumSerie;
    protected Map<String, Integer> allIDsModeles;
    protected JComboBox champModele;
    protected JTextField champCouleur;
    protected JSpinner champPrix;
    protected String champPhoto;
    protected JFileChooser selectPhoto;
    protected TableauInstruments tableauInstruments;
    protected JButton bValider;
    protected JButton bAnnuler;

    
    public FenetreAjouterInstrument(JFrame parent, TableauInstruments tableauInstruments) {
        super(parent, "Ajouter un instrument", true);
        this.tableauInstruments = tableauInstruments;
        setLayout(new BorderLayout(10, 10));

        creerSelecteurPhoto();

        creerChamps();
        creerBoutons();

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerSelecteurPhoto() {
        selectPhoto = new JFileChooser();
        File dossierFixe = new File(System.getProperty("user.dir") + "/imagesInstruments");
        selectPhoto.setCurrentDirectory(dossierFixe);
        selectPhoto.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter typeFichier = new FileNameExtensionFilter("Images (png, jpg ou jpeg)", "png", "jpg", "jpeg");
        selectPhoto.addChoosableFileFilter(typeFichier);
    }

    private void creerChamps() {
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 20, 20));

        champNumSerie = new JTextField(15);
        panelForm.add(new JLabel("Numéro de série :"));
        panelForm.add(champNumSerie);

        allIDsModeles = ModelesDB.getIdsModele();
        champModele = new JComboBox(allIDsModeles.keySet().toArray());
        panelForm.add(new JLabel("Modèle :"));
        panelForm.add(champModele);

        champCouleur = new JTextField(15);
        panelForm.add(new JLabel("Couleur :"));
        panelForm.add(champCouleur);

        champPrix = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        panelForm.add(new JLabel("Prix (€) :"));
        panelForm.add(champPrix);

        champPhoto = null;
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
    }

    private void creerBoutons() {
        JPanel panelBoutons = new JPanel();

        bValider = new JButton("Valider");
        panelBoutons.add(bValider);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerInstrument();
            }
        });

        bAnnuler = new JButton("Annuler");
        panelBoutons.add(bAnnuler);
        bAnnuler.addActionListener(e -> {
            dispose();
        });

        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void choisirPhoto() {
        int r = selectPhoto.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            champPhoto = selectPhoto.getSelectedFile().getName();
        }
    }

    private void creerInstrument() {
        int idInstrument = InstrumentsDB.creerNouvelId();
        String numSerie = champNumSerie.getText().trim();
        String modeleSelect = (String) champModele.getSelectedItem();
        String couleur = champCouleur.getText().trim();
        int prix = (int) champPrix.getValue();

        // Vérifie si un champ obligatoire est vide
        if (numSerie.isEmpty() | modeleSelect == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Numéro de série et Modèle).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            int idModele = allIDsModeles.get(modeleSelect);
            Instrument instrumentCree = new Instrument(idInstrument, numSerie, idModele, couleur, prix, champPhoto);
            InstrumentsDB.ajouter(instrumentCree);
            tableauInstruments.ajouterDonnee(instrumentCree);
            dispose();
        }
    }
}