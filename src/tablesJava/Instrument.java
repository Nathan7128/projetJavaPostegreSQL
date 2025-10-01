package tablesJava;

public class Instrument {

    private String id;
    private String numSerie;
    private String idModele;
    private String couleur;
    private int prix;
    private String photo;


    public Instrument(String id, String numSerie, String idModele, String couleur, int prix, String photo) {
        this.id = id;
        this.numSerie = numSerie;
        this.idModele = idModele;
        this.couleur = couleur;
        this.prix = prix;
        this.photo = photo;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public void setIdModele(String idModele) {
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

    public String getId() {
        return id;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public String getIdModele() {
        return idModele;
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
