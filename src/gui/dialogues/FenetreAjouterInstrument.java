package gui.dialogues;

import tablesDB.InstrumentsDB;
import tablesDB.ModelesDB;
import tablesJava.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FenetreAjouterInstrument extends JDialog {

    private Instrument instrumentCree = null;

    private final JSpinner champIdInstrument = new JSpinner((new SpinnerNumberModel(0, 0, 100, 1)));
    private final JTextField champNumSerie = new JTextField(15);
    private final Map<String, Integer> allIDsModeles = ModelesDB.getAllIDsModeles();
    private final JComboBox champModele = new JComboBox(allIDsModeles.keySet().toArray());
    private final JTextField champCouleur = new JTextField(15);
    private final JSpinner champPrix = new JSpinner((new SpinnerNumberModel(0, 0, 10000, 1)));
    private final JTextField champPhoto = new JTextField(15);

    public FenetreAjouterInstrument(JFrame parent) {
        super(parent, "Ajouter un instrument", true);
        setLayout(new BorderLayout(10, 10));


        JPanel panelForm = new JPanel(new GridLayout(6, 2, 20, 20));

        panelForm.add(new JLabel("Identifiant de l'instrument :"));
        panelForm.add(champIdInstrument);

        panelForm.add(new JLabel("Numéro de série :"));
        panelForm.add(champNumSerie);

        panelForm.add(new JLabel("Modèle :"));
        panelForm.add(champModele);

        panelForm.add(new JLabel("Couleur :"));
        panelForm.add(champCouleur);

        panelForm.add(new JLabel("Prix (€) :"));
        panelForm.add(champPrix);

        panelForm.add(new JLabel("Photo (URL / chemin) :"));
        panelForm.add(champPhoto);

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

    private void creerInstrument() {
        int id_instrument = (int) champIdInstrument.getValue();
        String num_serie = champNumSerie.getText().trim();
        int id_modele = allIDsModeles.get((String) champModele.getSelectedItem());
        String couleur = champCouleur.getText().trim();
        int prix = (int) champPrix.getValue();
        String photo = champPhoto.getText().trim();

        // Vérifie si l'identifiant existe déjà
        if (InstrumentsDB.getAllIDsInstruments().containsValue(id_instrument)) {
            JOptionPane.showMessageDialog(this,
                    "L'identifiant " + id_instrument + " existe déjà.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Vérifie si un champ obligatoire est vide
        else if (num_serie.isEmpty() || couleur.isEmpty() || photo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            instrumentCree = new Instrument(id_instrument, num_serie, id_modele, couleur, prix, photo);
            InstrumentsDB.add(instrumentCree);
            dispose();
        }
    }

    public Instrument afficherEtRecuperer() {
        setVisible(true);
        return instrumentCree;
    }
}