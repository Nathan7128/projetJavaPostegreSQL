package tablesjava;

// Importation des bibliothèques internes
import tablesdb.ModelesDB;


/**
 * Classe permettant de modéliser en Java la table "Instrument" de la bdd
 */
public class Instrument {

    private int id;
    private String numSerie;
    private int idModele;
    private String nomModele;
    private String couleur;
    private int prix;
    private String photo;


    public Instrument(int id, String numSerie, int idModele, String couleur, int prix, String photo) {
        this.id = id;
        this.numSerie = numSerie;
        this.idModele = idModele;
        this.nomModele = ModelesDB.getById(idModele).getNom();
        this.couleur = couleur;
        this.prix = prix;
        this.photo = photo;
    }


    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public void setIdModele(int idModele) {
        this.idModele = idModele;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public int getIdModele() {
        return idModele;
    }

    public String getNomModele() {
        return nomModele;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getPrix() {
        return prix;
    }

    public String getPhoto() {
        return photo;
    }
}
