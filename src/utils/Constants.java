package utils;

import java.awt.*;
import java.io.File;

public final class Constants {
    public static final String sep = File.separator;
    public static final String cheminRacineProjet = System.getProperty("user.dir");
    public static final String cheminPhotosInstruments = cheminRacineProjet + sep + "imagesInstruments";

    public static final String cheminIcones = cheminRacineProjet + sep + "gui" + sep + "icones.png";
}
