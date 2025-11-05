package tablesjava;

import tablesdb.ModelesDB;

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
        this.nomModele = ModelesDB.findById(idModele).getNom();
        this.couleur = couleur;
        this.prix = prix;
        this.photo = photo;
    }


//    public void setId(int id) {
//        this.id = id;
//    }
//
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
