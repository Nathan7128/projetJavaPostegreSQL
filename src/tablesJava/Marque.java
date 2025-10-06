package tablesJava;

public class Marque {

    private int id;
    private String nom;
    private String siteWeb;

    public Marque(int id, String nom_modele, String site_web) {
        this.id = id;
        this.nom = nom_modele;
        this.siteWeb = site_web;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSiteWeb(String site_web) {
        this.siteWeb = site_web;
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
