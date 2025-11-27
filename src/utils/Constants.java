package utils;

// Importation des bibliothèques externes
import java.awt.*;
import java.io.File;


/**
 * Classe permettant de stocker les principales variables qui sont utilisées/partagées par tous les programmes du logiciel
 */
public final class Constants {

//    Séparateur de chemin de fichier utilisé par le système d'exploitation
    public static final String sep = File.separator;
    public static final String cheminRacineProjet = System.getProperty("user.dir");
    public static final String cheminPhotosInstruments = cheminRacineProjet + sep + "imagesInstruments";

    public static final Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int largeurEcran = (int) tailleEcran.getWidth();
    public static final int hauteurEcran = (int) tailleEcran.getHeight();
}
