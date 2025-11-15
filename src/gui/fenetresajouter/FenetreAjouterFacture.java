package gui.fenetresajouter;

import gui.tableaux.TableauFactures;
import tablesdb.ClientsDB;
import tablesdb.FacturesDB;
import tablesdb.InstrumentsDB;
import tablesdb.LignesFacturesDB;
import tablesjava.Facture;
import tablesjava.LigneFacture;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class FenetreAjouterFacture extends JDialog {

    private final Map<String, Integer> allIDsClients = ClientsDB.getAllIDsClients();
    private final JComboBox champClient = new JComboBox(allIDsClients.keySet().toArray());

    private final JSpinner champJour = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    private final JComboBox<String> champMois = new JComboBox<>(new String[]{
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    });
    private final JSpinner champAnnee = new JSpinner(new SpinnerNumberModel(2025, 1950, 2050, 1));
    private final Map<String, Integer> dictIdsInstruments = InstrumentsDB.getAllIDsInstruments();
    private final JComboBox champLigneFacture = new JComboBox();
    private TableauFactures tableauFactures;

    public FenetreAjouterFacture(JFrame parent, TableauFactures tableauFactures) {
        super(parent, "Ajouter une facture", true);
        this.tableauFactures = tableauFactures;
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        panelForm.add(new JLabel("Client :"));
        panelForm.add(champClient);

        panelForm.add(new JLabel("Date de la facture :"));
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDate.add(champJour);
        panelDate.add(champMois);
        panelDate.add(champAnnee);
        panelForm.add(panelDate);

        JPanel panelInstrument = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInstrument.add(new JLabel("Instrument(s) :"));
        JButton bAjouterLigneFacture = new JButton("+");
        bAjouterLigneFacture.setBackground(new Color(46, 204, 113)); // vert
        bAjouterLigneFacture.setForeground(Color.WHITE);
        JButton bSupprimerLigneFacture = new JButton("−");
        bSupprimerLigneFacture.setBackground(new Color(231, 76, 60)); // rouge
        bSupprimerLigneFacture.setForeground(Color.WHITE);
        bAjouterLigneFacture.setFont(new Font("Arial", Font.BOLD, 14));
        bSupprimerLigneFacture.setFont(new Font("Arial", Font.BOLD, 14));
        panelInstrument.add(bAjouterLigneFacture);
        panelInstrument.add(bSupprimerLigneFacture);
        panelForm.add(panelInstrument);
        panelForm.add(champLigneFacture);

        bAjouterLigneFacture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterLigneFacture();
            }
        });

        bSupprimerLigneFacture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerLigneFacture();
            }
        });

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        JButton boutonValider = new JButton("Valider");
        JButton boutonAnnuler = new JButton("Annuler");

        panelBoutons.add(boutonValider);
        panelBoutons.add(boutonAnnuler);
        add(panelBoutons, BorderLayout.SOUTH);

        boutonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerFacture();
            }
        });

        boutonAnnuler.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    public void ajouterLigneFacture() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterLigneFacture fenetreAjouterInstrumentFacture = new FenetreAjouterLigneFacture((JFrame) parent, champLigneFacture);
        fenetreAjouterInstrumentFacture.setVisible(true);
        repaint();
    }

    public void supprimerLigneFacture() {
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
        int idFacture = FacturesDB.createNewId();
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
            FacturesDB.add(factureCreee);
            tableauFactures.addDonnee(factureCreee);

//        Ajouter les lignes de la facture à la base de données
            int idInstrument;
            LigneFacture ligneFactureCreee;
            for (int i = 0; i < champLigneFacture.getItemCount(); i++) {
                idInstrument = dictIdsInstruments.get((String) champLigneFacture.getItemAt(i));
                ligneFactureCreee = new LigneFacture(idFacture, idInstrument);
                LignesFacturesDB.add(ligneFactureCreee);
            }

            dispose();
        }
    }
}