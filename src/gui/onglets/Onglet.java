package gui.onglets;

import javax.swing.*;
import java.awt.*;

public class Onglet extends JPanel {

    private String titre;

    public Onglet(String titre_onglet) {
        super(new BorderLayout());

        titre = titre_onglet;

        JLabel titreOnglet = new JLabel(titre, SwingConstants.CENTER);
        titreOnglet.setFont(new Font("Arial", Font.BOLD, 24));
        titreOnglet.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // marge autour du titre

        add(titreOnglet, BorderLayout.NORTH);
    }

    public String getTitre() {
        return titre;
    }
}
