package tablesJava;
import java.sql.Date;

public class Facture {

    private int id;
    private int idClient;
    private Date date;


    public Facture(int id, int id_marque, Date date) {
        this.id = id;
        this.idClient = id_marque;
        this.date = date;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int id_marque) { this.idClient = id_marque; }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public Date getDate() {
        return date;
    }
}
