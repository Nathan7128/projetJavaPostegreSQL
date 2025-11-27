package tablesjava;


/**
 * Classe permettant de modéliser en Java la table "Marque" de la bdd
 */
public class Marque {

    private int id;
    private String nom;
    private String siteWeb;

    public Marque(int id, String nomMarque, String siteWeb) {
        this.id = id;
        this.nom = nomMarque;
        this.siteWeb = siteWeb;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getSiteWeb() {
        return siteWeb;
    }
}
