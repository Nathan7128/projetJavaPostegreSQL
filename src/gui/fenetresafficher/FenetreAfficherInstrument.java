package gui.fenetresafficher;


// Importation des bibliothèques internes
import tablesdb.InstrumentsDB;
import tablesjava.Instrument;
import utils.Constants;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import com.idrsolutions.image.JDeli;


/**
 * Classe qui est appelée pour afficher l'image d'un instrument.
 * L'image est stockée dans le dossier "imagesInstruments" à la racine du projet
 * Elle est affichée dans une fenêtre de dialogue à l'écran
 */
public class FenetreAfficherInstrument extends JDialog {

    public FenetreAfficherInstrument(JFrame parent, int idInstrument) throws Exception {
        super(parent, "Instrument " + idInstrument);
        Instrument instrument = InstrumentsDB.getById(idInstrument);
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
            BufferedImage photoInstrument = JDeli.read(fichierPhoto);
            ImageIcon imageInstrument = new ImageIcon(photoInstrument);
            add(new JLabel(imageInstrument));
            setSize(imageInstrument.getIconWidth() + 50, imageInstrument.getIconHeight() + 50);
            setLocationRelativeTo(parent);
            setVisible(true);
        }
    }
}
