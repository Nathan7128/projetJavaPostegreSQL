package gui;

import gui.onglets.*;

import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {

    private JPanel contentPane;
    JTabbedPane onglets;
    private JToolBar barreOutils;

    public FenetrePrincipale() {
        super("Magasin de Musique");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

//        Création du conteneur principal
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

//        Création de la liste des onglets de la fenêtre
        onglets = creerOnglets();
        contentPane.add(onglets, BorderLayout.CENTER);
    }

    private JTabbedPane creerOnglets() {
        JTabbedPane onglets = new JTabbedPane();

        OngletInstruments ongletInstruments = new OngletInstruments();
        onglets.addTab(ongletInstruments.getTitre(), ongletInstruments.getIcone(), ongletInstruments);

        OngletModeles ongletModeles = new OngletModeles();
        onglets.addTab(ongletModeles.getTitre(), ongletModeles.getIcone(), ongletModeles);

        OngletMarques ongletMarques = new OngletMarques();
        onglets.addTab(ongletMarques.getTitre(), ongletMarques.getIcone(), ongletMarques);

        OngletClients ongletClients = new OngletClients();
        onglets.addTab(ongletClients.getTitre(), ongletClients.getIcone(), ongletClients);

        OngletFactures ongletFactures = new OngletFactures();
        onglets.addTab(ongletFactures.getTitre(), ongletFactures.getIcone(), ongletFactures);

        // 🔥 ChangeListener commun à tous les onglets
        onglets.addChangeListener(e -> {
            Component ongletSelectionne = onglets.getSelectedComponent();
            if (ongletSelectionne instanceof Onglet) {
                ((Onglet) ongletSelectionne).rafraichir();
            }
        });

        return onglets;
    }
}
