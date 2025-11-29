package gui.fenetresajouter;


// Importation des bibliothèques internes
import gui.tableaux.TableauMarques;
import tablesdb.MarquesDB;
import tablesjava.Marque;

// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Cette classe représente une fenêtre de dialogue permettant d'ajouter une marque.
 * Elle est instanciée lorsque l'utilisateur clique sur "Ajouter" une marque, et permet de
 * saisir les informations de la marque via sa boite de dialogue.
 */
public class FenetreAjouterMarque extends JDialog {

    protected JTextField champNom;
    protected JTextField champSiteWeb;
    protected TableauMarques tableauMarques;
    protected JButton bValider;
    protected JButton bAnnuler;


    public FenetreAjouterMarque(JFrame parent, TableauMarques tableauMarques) {
        super(parent, "Ajouter une marque", true);

        this.tableauMarques = tableauMarques;

        setLayout(new BorderLayout(10, 10));

        creerChamps();
        creerBoutons();

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerChamps() {
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 20, 20));

        champNom = new JTextField(15);
        panelForm.add(new JLabel("Nom :"));
        panelForm.add(champNom);

        champSiteWeb = new JTextField(15);
        panelForm.add(new JLabel("Site Web :"));
        panelForm.add(champSiteWeb);

        add(panelForm, BorderLayout.CENTER);
    }

    private void creerBoutons() {
        JPanel panelBoutons = new JPanel();

        bValider = new JButton("Valider");
        panelBoutons.add(bValider);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerMarque();
            }
        });

        bAnnuler = new JButton("Annuler");
        panelBoutons.add(bAnnuler);
        bAnnuler.addActionListener(e -> {
            dispose();
        });

        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void creerMarque() {
        int idMarque = MarquesDB.creerNouvelId();
        String nom = champNom.getText().trim();
        String siteWeb = champSiteWeb.getText().trim();

        // Vérifie si un champ obligatoire est vide
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Nom).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si tout est correct
        else {
            Marque marqueCreee = new Marque(idMarque, nom, siteWeb);
            MarquesDB.ajouter(marqueCreee);
            tableauMarques.ajouterDonnee(marqueCreee);
            dispose();
        }
    }
}