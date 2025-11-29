package gui.fenetresmodifier;


// Importation des bibliothèques internes
import gui.fenetresajouter.FenetreAjouterFacture;
import gui.tableaux.TableauFactures;
import tablesdb.*;
import tablesjava.Client;
import tablesjava.Facture;
import tablesjava.Instrument;
import tablesjava.LigneFacture;


// Importation des bibliothèques externes
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Map;
import java.sql.Date;


/**
 * Cette classe hérite de la classe mère "FenetreAjouterFacture" et est utilisée pour modifier une facture
 * déjà présente dans la BDD.
 * Elle hérite de cette classe car elle lui est très semblable : elle possède les mêmes champs, labels, etc.,
 * à la différence que certains d'entre eux sont remplis par défaut car la facture a déjà été saisie.
 */
public class FenetreModifierFacture extends FenetreAjouterFacture {

    int indexTableau;
    private Facture facture;


    public FenetreModifierFacture(JFrame parent, TableauFactures tableauFactures, int indexTableau) {
        super(parent, tableauFactures);

        this.indexTableau = indexTableau;
        int idFacture = (int) tableauFactures.getValueAt(indexTableau, 0);
        this.facture = FacturesDB.getById(idFacture);

        Client client = ClientsDB.getById(facture.getIdClient());
        champClient.setSelectedItem(client.getId() + " - " + client.getPrenom() + " " + client.getNom());

        LocalDate localDate = facture.getDate().toLocalDate();
        champJour.setValue(localDate.getDayOfMonth());
        champMois.setSelectedIndex(localDate.getMonthValue() - 1); // index de 0 à 11
        champAnnee.setValue(localDate.getYear());

        List<LigneFacture> lignesFacture = LignesFactureDB.getLignesFacture(idFacture);
        Instrument instrument;
        for (LigneFacture ligneFacture : lignesFacture) {
            instrument = InstrumentsDB.getById(ligneFacture.getIdInstrument());
            champLigneFacture.addItem(instrument.getId() + " - " + instrument.getNumSerie());
        }


        bValider.removeActionListener(bValider.getActionListeners()[0]);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierFacture();
            }
        });
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
        FacturesDB.modifier(this.facture.getId(), idClient, date);
        tableauFactures.modifierLigne(this.indexTableau, this.facture);

        List<Integer> idsInstrument = new ArrayList<>();
        Map<String, Integer> allIdsInstrument = InstrumentsDB.getIdsInstrument();
        for (int i = 0; i < champLigneFacture.getItemCount(); i++) {
            idsInstrument.add(allIdsInstrument.get((String) champLigneFacture.getItemAt(i)));
        }
        LignesFactureDB.modifierFacture(this.facture.getId(), idsInstrument);

        dispose();
    }
}
