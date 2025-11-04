package tablesjava;

public class Modele {

    private int id;
    private int idMarque;
    private String nom;


    public Modele(int id, int id_marque, String nom_modele) {
        this.id = id;
        this.idMarque = id_marque;
        this.nom = nom_modele;
    }


//    public void setId(int id) {
//        this.id = id;
//    }

    public void setIdMarque(int id_marque) { this.idMarque = id_marque; }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public int getIdMarque() {
        return idMarque;
    }

    public String getNom() {
        return nom;
    }
}
