package gui.tableaux;

import tablesdb.ClientsDB;
import tablesjava.Client;


public class TableauClients extends Tableau<Client> {

    public TableauClients() {
        super(
                ClientsDB.findAll(),
                new String[]{"IdClient", "Nom", "Prenom", "Adresse", "Email"}
        );
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = donnees.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> client.getId();
            case 1 -> client.getNom();
            case 2 -> client.getPrenom();
            case 3 -> client.getAdresse();
            case 4 -> client.getEmail();
            default -> null;
        };
    }
}