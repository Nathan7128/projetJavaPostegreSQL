package gui.onglets;

import javax.swing.*;
import java.awt.*;

public class Onglet extends JPanel {

    private String titre;
    private Icon icone;

    public Onglet(String titre_onglet, String fichier_icone) {
        super(new BorderLayout());

        titre = titre_onglet;

        JLabel titreOnglet = new JLabel(titre, SwingConstants.CENTER);
        titreOnglet.setFont(new Font("Arial", Font.BOLD, 24));
        titreOnglet.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // marge autour du titre

        // Chargement et redimensionnement de l'icône
        Image image_icone = new ImageIcon(fichier_icone).getImage();
        image_icone = image_icone.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        icone = new ImageIcon(image_icone);

        add(titreOnglet, BorderLayout.NORTH);
    }

    public void rafraichir() {
    }

    public Icon getIcone() {
        return icone;
    }

    public String getTitre() {
        return titre;
    }
}
