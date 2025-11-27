package gui.tableaux;


// Importation des bibliothèques internes
import tablesdb.ClientsDB;
import tablesjava.Client;


/**
 * Classe dérivée de la classe mère Tableau, et qui modélise le tableau
 * contenant les données de la table Client de la bdd
 */
public class TableauClients extends Tableau<Client> {

    public TableauClients() {
        super(
                ClientsDB.getClients(),
                new String[]{"ID Client", "Nom", "Prenom", "Adresse", "Email"}
        );
    }


    @Override
    public Object getValueAt(int indexLigne, int indexColonne) {
        Client client = donnees.get(indexLigne);

        return switch (indexColonne) {
            case 0 -> client.getId();
            case 1 -> client.getNom();
            case 2 -> client.getPrenom();
            case 3 -> client.getAdresse();
            case 4 -> client.getEmail();
            default -> null;
        };
    }
}