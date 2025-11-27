package gui.onglets;


// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;


/**
 * Classe mère abstraite modélisant un onglet de l'application
 * Elle doit être dérivée pour implémenter des onglets plus spécifiques, comme celui pour afficher les clients, les factures, etc.
 */
abstract class Onglet extends JPanel {

    protected String titre;


    public Onglet(String titreOnglet) {
        super(new BorderLayout());

        titre = titreOnglet;

        JLabel lTitreOnglet = new JLabel(titre, SwingConstants.CENTER);
        lTitreOnglet.setFont(new Font("Arial", Font.BOLD, 24));
        lTitreOnglet.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // marge autour du titre

        add(lTitreOnglet, BorderLayout.NORTH);
    }


    public String getTitre() {
        return titre;
    }
}
