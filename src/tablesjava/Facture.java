package tablesjava;


// Importation des bibliothèques internes
import tablesdb.ClientsDB;

// Importation des bibliothèques externes
import java.sql.Date;


/**
 * Classe permettant de modéliser en Java la table "Facture" de la bdd
 */
public class Facture {

    private int id;
    private int idClient;
    private Date date;


    public Facture(int id, int idClient, Date date) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
    }


    public void setIdClient(int idClient) { this.idClient = idClient; }

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
        Client client = ClientsDB.getById(idClient);
        return client.getPrenom() + " " + client.getNom();
    }

    public Date getDate() {
        return date;
    }
}
