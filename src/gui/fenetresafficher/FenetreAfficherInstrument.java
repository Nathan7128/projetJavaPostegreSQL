package gui.fenetresafficher;

import tablesdb.InstrumentsDB;
import tablesjava.Instrument;
import utils.Constants;

import javax.swing.*;
import java.io.File;

public class FenetreAfficherInstrument extends JDialog {
    public FenetreAfficherInstrument(JFrame parent, int idInstrument) {
        super(parent, "Instrument " + idInstrument);
        Instrument instrument = InstrumentsDB.findById(idInstrument);
        String photo = instrument.getPhoto();
        String cheminPhoto = Constants.cheminPhotosInstruments + Constants.sep + photo;
        File fichierPhoto = new File(cheminPhoto);
        if (photo == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez choisir une photo pour cet instrument.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else if (!(fichierPhoto.exists())) {
            JOptionPane.showMessageDialog(this,
                    "L'image " + photo + " est introuvable dans le dossier " + Constants.cheminPhotosInstruments + " .",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            ImageIcon imageInstrument = new ImageIcon(cheminPhoto);
            add(new JLabel(imageInstrument));
            setSize(imageInstrument.getIconWidth() + 50, imageInstrument.getIconHeight() + 50);
            setLocationRelativeTo(parent);
            setVisible(true);
        }
    }
}
