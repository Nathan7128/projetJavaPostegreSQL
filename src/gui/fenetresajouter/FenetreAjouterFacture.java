package gui.fenetresajouter;

import gui.tableaux.TableauFactures;
import tablesDB.ClientsDB;
import tablesDB.FacturesDB;
import tablesJava.Facture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class FenetreAjouterFacture extends JDialog {

    private Facture factureCreee = null;

    private final Map<String, Integer> allIDsClients = ClientsDB.getAllIDsClients();
    private final JComboBox champClient = new JComboBox(allIDsClients.keySet().toArray());

    private final JSpinner champJour = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    private final JComboBox<String> champMois = new JComboBox<>(new String[]{
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    });
    private final JSpinner champAnnee = new JSpinner(new SpinnerNumberModel(2025, 1950, 2050, 1));
    private TableauFactures tableauFactures;

    public FenetreAjouterFacture(JFrame parent, TableauFactures tableauFactures) {
        super(parent, "Ajouter une facture", true);
        this.tableauFactures = tableauFactures;
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 20, 20));

        panelForm.add(new JLabel("Client :"));
        panelForm.add(champClient);

        panelForm.add(new JLabel("Date de la facture :"));
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDate.add(champJour);
        panelDate.add(champMois);
        panelDate.add(champAnnee);
        panelForm.add(panelDate);

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

    private void creerFacture() {
        int id_facture = FacturesDB.createNewId();
        int id_client = allIDsClients.get((String) champClient.getSelectedItem());

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

        factureCreee = new Facture(id_facture, id_client, date);
        FacturesDB.add(factureCreee);
        tableauFactures.addDonnee(factureCreee);
        dispose();
    }
}