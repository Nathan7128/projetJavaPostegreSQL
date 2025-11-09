package gui.fenetresmodifier;

import gui.fenetresajouter.FenetreAjouterLigneFacture;
import gui.tableaux.TableauFactures;
import tablesdb.*;
import tablesjava.Client;
import tablesjava.Facture;
import tablesjava.Instrument;
import tablesjava.LigneFacture;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Map;
import java.sql.Date;

public class FenetreModifierFacture extends JDialog {

    private final Map<String, Integer> allIDsClients = ClientsDB.getAllIDsClients();
    private final JComboBox champClient = new JComboBox(allIDsClients.keySet().toArray());

    private final JSpinner champJour = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    private final JComboBox<String> champMois = new JComboBox<>(new String[]{
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    });
    private final JSpinner champAnnee = new JSpinner(new SpinnerNumberModel(2025, 1950, 2050, 1));
    private final JComboBox champLigneFacture = new JComboBox();
    private TableauFactures tableauFactures;
    int indexTableau;
    private Facture facture;

    public FenetreModifierFacture(JFrame parent, TableauFactures tableauFactures, int indexTableau) {
        super(parent, "Modifier le modèle", true);
        this.tableauFactures = tableauFactures;
        this.indexTableau = indexTableau;
        int idFacture = (int) tableauFactures.getValueAt(indexTableau, 0);
        this.facture = FacturesDB.findById(idFacture);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 20, 20));

        panelForm.add(new JLabel("Client :"));
        panelForm.add(champClient);
        Client client = ClientsDB.findById(facture.getIdClient());
        champClient.setSelectedItem(client.getId() + " - " + client.getPrenom() + " " + client.getNom());

        panelForm.add(new JLabel("Date de la facture :"));
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDate.add(champJour);
        panelDate.add(champMois);
        panelDate.add(champAnnee);
        panelForm.add(panelDate);
        LocalDate localDate = facture.getDate().toLocalDate();
        champJour.setValue(localDate.getDayOfMonth());
        champMois.setSelectedIndex(localDate.getMonthValue() - 1); // index de 0 à 11
        champAnnee.setValue(localDate.getYear());

        panelForm.add(new JLabel("Instrument(s) :"));
        JPanel panelInstrument = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInstrument.add(champLigneFacture);
        List<LigneFacture> lignesFacture = LignesFacturesDB.trouverLignesFacture(idFacture);
        Instrument instrument;
        for (LigneFacture ligneFacture : lignesFacture) {
            instrument = InstrumentsDB.findById(ligneFacture.getIdInstrument());
            champLigneFacture.addItem(instrument.getId() + " - " + instrument.getNumSerie());
        }
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
                modifierFacture();
            }
        });

        boutonAnnuler.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public void ajouterLigneFacture() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FenetreAjouterLigneFacture fenetreAjouterInstrumentFacture = new FenetreAjouterLigneFacture((JFrame) parent, champLigneFacture);
        fenetreAjouterInstrumentFacture.setVisible(true);
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

    private void modifierFacture() {
        int idClient = allIDsClients.get((String) champClient.getSelectedItem());

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

        this.facture.setIdClient(idClient);
        this.facture.setDate(date);
        FacturesDB.update(this.facture.getId(), idClient, date);
        tableauFactures.modifierLigne(this.indexTableau, this.facture);

        List<Integer> idsInstrument = new ArrayList<>();
        Map<String, Integer> allIdsInstrument = InstrumentsDB.getAllIDsInstruments();
        for (int i = 0; i < champLigneFacture.getItemCount(); i++) {
            idsInstrument.add(allIdsInstrument.get((String) champLigneFacture.getItemAt(i)));
        }
        LignesFacturesDB.modifierFacture(this.facture.getId(), idsInstrument);

        dispose();
    }
}
