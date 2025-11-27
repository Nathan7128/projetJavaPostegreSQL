package gui;


// Importation des bibliothèques internes
import gui.onglets.*;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;


/**
 * Classe modélisant la fenêtre principale de l'application, qui regroupe tous les onglets qui servent de "sous-fenêtre" à
 * l'application pour la séparer en plusieurs parties
 */
public class FenetrePrincipale extends JFrame {

    private JPanel panneauContenu;
    JTabbedPane onglets;


    public FenetrePrincipale() {
        super("Magasin de Musique");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

//        Création du panneau principal
        panneauContenu = new JPanel(new BorderLayout());
        setContentPane(panneauContenu);

//        Création de la liste des onglets de la fenêtre
        onglets = creerOnglets();
        panneauContenu.add(onglets, BorderLayout.CENTER);
    }


    /**
     * Créer la liste des onglets de l'application
     */
    private JTabbedPane creerOnglets() {
        JTabbedPane onglets = new JTabbedPane();

        OngletInstruments ongletInstruments = new OngletInstruments();
        onglets.addTab(ongletInstruments.getTitre(), ongletInstruments);

        OngletModeles ongletModeles = new OngletModeles();
        onglets.addTab(ongletModeles.getTitre(), ongletModeles);

        OngletMarques ongletMarques = new OngletMarques();
        onglets.addTab(ongletMarques.getTitre(), ongletMarques);

        OngletClients ongletClients = new OngletClients();
        onglets.addTab(ongletClients.getTitre(), ongletClients);

        OngletFactures ongletFactures = new OngletFactures();
        onglets.addTab(ongletFactures.getTitre(), ongletFactures);

        return onglets;
    }
}
