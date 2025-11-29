package tablesjava;

// Importation des bibliothèques internes
import tablesdb.MarquesDB;


/**
 * Classe permettant de modéliser en Java la table "Modèle" de la bdd
 */
public class Modele {

    private int id;
    private int idMarque;
    private String nom;


    public Modele(int id, int idMarque, String nomModele) {
        this.id = id;
        this.idMarque = idMarque;
        this.nom = nomModele;
    }


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
        return MarquesDB.getById(idMarque).getNom();
    }

    public String getNom() {
        return nom;
    }
}
