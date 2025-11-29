package gui.fenetresajouter;


// Importation des bibliothèques internes
import gui.tableaux.TableauFactures;
import tablesdb.ClientsDB;
import tablesdb.FacturesDB;
import tablesdb.InstrumentsDB;
import tablesdb.LignesFactureDB;
import tablesjava.Facture;
import tablesjava.LigneFacture;


// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;


/**
 * Cette classe représente une fenêtre de dialogue permettant d'ajouter une facture.
 * Elle est instanciée lorsque l'utilisateur clique sur "Ajouter" une facture, et permet de
 * saisir les informations de la facture via sa boite de dialogue.
 */
public class FenetreAjouterFacture extends JDialog {

    protected Map<String, Integer> allIDsClients;
    protected JComboBox champClient;
    protected JSpinner champJour;
    protected JComboBox<String> champMois;
    protected JSpinner champAnnee;
    protected Map<String, Integer> dictIdsInstruments;
    protected JComboBox champLigneFacture;
    protected TableauFactures tableauFactures;
    protected JButton bValider;
    protected JButton bAnnuler;


    public FenetreAjouterFacture(JFrame parent, TableauFactures tableauFactures) {
        super(parent, "Ajouter une facture", true);
        this.tableauFactures = tableauFactures;
        setLayout(new BorderLayout(10, 10));

        creerChamps();
        creerBoutons();

        pack();
        setLocationRelativeTo(parent);
    }

    private void creerChamps() {
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));


        allIDsClients = ClientsDB.getIdsClient();
        champClient = new JComboBox(allIDsClients.keySet().toArray());
        panelForm.add(new JLabel("Client :"));
        panelForm.add(champClient);


        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        champJour = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        panelDate.add(champJour);
        champMois = new JComboBox<>(new String[]{
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        });
        panelDate.add(champMois);
        champAnnee = new JSpinner(new SpinnerNumberModel(2025, 1950, 2050, 1));
        panelDate.add(champAnnee);
        panelForm.add(new JLabel("Date de la facture :"));
        panelForm.add(panelDate);


        JPanel panelInstrument = new JPanel(new FlowLayout(FlowLayout.LEFT));

        dictIdsInstruments = InstrumentsDB.getIdsInstrument();
        panelInstrument.add(new JLabel("Instrument(s) :"));

        JButton bAjouterLigneFacture = new JButton("+");
        bAjouterLigneFacture.setBackground(new Color(46, 204, 113)); // vert
        bAjouterLigneFacture.setForeground(Color.WHITE);
        bAjouterLigneFacture.setFont(new Font("Arial", Font.BOLD, 14));
        bAjouterLigneFacture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterLigneFacture();
            }
        });
        panelInstrument.add(bAjouterLigneFacture);

        JButton bSupprimerLigneFacture = new JButton("−");
        bSupprimerLigneFacture.setBackground(new Color(231, 76, 60)); // rouge
        bSupprimerLigneFacture.setForeground(Color.WHITE);
        bSupprimerLigneFacture.setFont(new Font("Arial", Font.BOLD, 14));
        bSupprimerLigneFacture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerLigneFacture();
            }
        });
        panelInstrument.add(bSupprimerLigneFacture);

        panelForm.add(panelInstrument);

        champLigneFacture = new JComboBox();
        panelForm.add(champLigneFacture);

        add(panelForm, BorderLayout.CENTER);
    }

    private void creerBoutons() {
        JPanel panelBoutons = new JPanel();

        bValider = new JButton("Valider");
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerFacture();
            }
        });
        panelBoutons.add(bValider);

        bAnnuler = new JButton("Annuler");
        bAnnuler.addActionListener(e -> dispose());
        panelBoutons.add(bAnnuler);

        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void ajouterLigneFacture() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterLigneFacture fenetreAjouterInstrumentFacture = new FenetreAjouterLigneFacture((JFrame) parent, champLigneFacture);
        fenetreAjouterInstrumentFacture.setVisible(true);
        repaint();
    }

    private void supprimerLigneFacture() {
        String instrumentSel = (String) champLigneFacture.getSelectedItem();
        if (instrumentSel == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un instrument à supprimer.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
        else {
            champLigneFacture.removeItem(instrumentSel);
            champLigneFacture.setSelectedItem(null);
        }
    }

    private void creerFacture() {
        int idFacture = FacturesDB.creerNouvelId();
        String clientSelect = (String) champClient.getSelectedItem();

        // Récupération des valeurs de date
        int jour = (int) champJour.getValue();
        int mois = champMois.getSelectedIndex() + 1; // Janvier = 1
        int annee = (int) champAnnee.getValue();

        LocalDate localDate;
        try {
            localDate = LocalDate.of(annee, mois, jour);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Date invalide (par exemple 31 février n’existe pas).",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date date = Date.valueOf(localDate);

        // Vérifie si un champ obligatoire est vide
        if (clientSelect == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs obligatoires (Client).",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }

        else {
            //        Ajouter la facture à la base de données
            int idClient = allIDsClients.get(clientSelect);
            Facture factureCreee = new Facture(idFacture, idClient, date);
            FacturesDB.ajouter(factureCreee);
            tableauFactures.ajouterDonnee(factureCreee);

            creerLignesFacture(idFacture);

            dispose();
        }
    }

    private void creerLignesFacture(int idFacture) {
        int idInstrument;
        LigneFacture ligneFactureCreee;
        for (int i = 0; i < champLigneFacture.getItemCount(); i++) {
            idInstrument = dictIdsInstruments.get((String) champLigneFacture.getItemAt(i));
            ligneFactureCreee = new LigneFacture(idFacture, idInstrument);
            LignesFactureDB.ajouter(ligneFactureCreee);
        }
    }
}