package lib.model;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int nbExemplaires;
    private Categorie categorie;

    public Livre() {}

    public Livre(int id, String titre, String auteur, int nbExemplaires, Categorie categorie) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.nbExemplaires = nbExemplaires;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public int getNbExemplaires() { return nbExemplaires; }
    public void setNbExemplaires(int nbExemplaires) { this.nbExemplaires = nbExemplaires; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}
