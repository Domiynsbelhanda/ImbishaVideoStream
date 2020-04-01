package cd.belhanda.imbishavideostream.Modele;

public class Chanson {
    private String img_url;
    private String nom_art;
    private String titre;
    private String url;

    public Chanson() {
    }

    public Chanson(String img_url, String nom_art, String titre, String url) {
        this.img_url = img_url;
        this.nom_art = nom_art;
        this.titre = titre;
        this.url = url;
    }

    public Chanson(String img_url, String titre) {
        this.img_url = img_url;
        this.titre = titre;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNom_art() {
        return nom_art;
    }

    public void setNom_art(String nom_art) {
        this.nom_art = nom_art;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
