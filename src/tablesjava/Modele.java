package tablesjava;

import tablesdb.MarquesDB;

public class Modele {

    private int id;
    private int idMarque;
    private String nomMarque;
    private String nom;


    public Modele(int id, int idMarque, String nomModele) {
        this.id = id;
        this.idMarque = idMarque;
        this.nomMarque = MarquesDB.findById(idMarque).getNom();
        this.nom = nomModele;
    }


//    public void setId(int id) {
//        this.id = id;
//    }

    public void setIdMarque(int idMarque) { this.idMarque = idMarque; }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public int getIdMarque() {
        return idMarque;
    }

    public String getNomMarque() {
        return nomMarque;
    }

    public String getNom() {
        return nom;
    }
}
