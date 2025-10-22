package utils;

import java.awt.*;
import java.io.File;

public final class Constants {
    public static final String sep = File.separator;
    public static final String cheminRacineProjet = System.getProperty("user.dir");
    public static final String cheminPhotosInstruments = cheminRacineProjet + sep + "imagesInstruments";

    public static final String cheminIconesOnglets = cheminRacineProjet + sep + "gui" + sep + "iconesOnglets";
    public static final String cheminIconeOngletClients = cheminIconesOnglets + sep + "icone_clients";
    public static final String cheminIconeOngletFactures = cheminIconesOnglets + sep + "icone_factures";
    public static final String cheminIconeOngletInstruments = cheminIconesOnglets + sep + "icone_instruments";
    public static final String cheminIconeOngletMarques = cheminIconesOnglets + sep + "icone_marques";
    public static final String cheminIconeOngletModeles = cheminIconesOnglets + sep + "icone_modeles";
}
