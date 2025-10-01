package gui;

import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {

    private JPanel contentPane;

    private JToolBar barreOutils;

    public FenetrePrincipale() {
        super("Magasin de Musique");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Initialisation de la barre de menu
        initBarreMenu();

        JTable tableauInstruments = new JTable(new TableauInstruments());
        contentPane.add(new JScrollPane(tableauInstruments), BorderLayout.CENTER);
    }

    private void initBarreMenu() {
        barreOutils = new JToolBar();
        barreOutils.setFloatable(false);
        barreOutils.setPreferredSize(new Dimension(0, 50));
        barreOutils.setLayout(new BoxLayout(barreOutils, BoxLayout.X_AXIS));
        barreOutils.add(Box.createHorizontalGlue());

        Image icone_factures = new ImageIcon("src/gui/images/icone_factures.png").getImage();
        icone_factures = icone_factures.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btnFacture = new JButton(new ImageIcon(icone_factures));
        btnFacture.setToolTipText("Factures");
        barreOutils.add(btnFacture);

        barreOutils.addSeparator();

        Image icone_clients = new ImageIcon("src/gui/images/icone_clients.png").getImage();
        icone_clients = icone_clients.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btnClients = new JButton(new ImageIcon(icone_clients));
        btnClients.setToolTipText("Clients");
        barreOutils.add(btnClients);

        barreOutils.addSeparator();

        Image icone_instruments = new ImageIcon("src/gui/images/icone_instruments.png").getImage();
        icone_instruments = icone_instruments.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btnInstruments = new JButton(new ImageIcon(icone_instruments));
        btnInstruments.setToolTipText("Instruments");
        barreOutils.add(btnInstruments);

        barreOutils.addSeparator();

        Image icone_modeles = new ImageIcon("src/gui/images/icone_modeles.png").getImage();
        icone_modeles = icone_modeles.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btnModeles = new JButton(new ImageIcon(icone_modeles));
        btnModeles.setToolTipText("Modèles");
        barreOutils.add(btnModeles);

        barreOutils.addSeparator();

        Image icone_marques = new ImageIcon("src/gui/images/icone_marques.png").getImage();
        icone_marques = icone_marques.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btnMarques = new JButton(new ImageIcon(icone_marques));
        btnMarques.setToolTipText("Marques");
        barreOutils.add(btnMarques);

        barreOutils.addSeparator();

        barreOutils.add(Box.createHorizontalGlue());

        contentPane.add(barreOutils, BorderLayout.NORTH);
    }
}
