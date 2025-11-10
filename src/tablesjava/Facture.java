package tablesjava;
import tablesdb.ClientsDB;

import java.sql.Date;

public class Facture {

    private int id;
    private int idClient;
    private Client client;
    private Date date;


    public Facture(int id, int id_client, Date date) {
        this.id = id;
        this.idClient = id_client;
        this.client = ClientsDB.findById(id_client);
        this.date = date;
    }


//    public void setId(int id) {
//        this.id = id;
//    }
//
    public void setIdClient(int id_client) { this.idClient = id_client; }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNomClientComplet() {
        return client.getPrenom() + " " + client.getNom();
    }

    public Date getDate() {
        return date;
    }
}
