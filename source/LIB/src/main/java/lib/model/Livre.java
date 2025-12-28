package lib.model;


public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int nbTotalExemplaires;    // Stock total
    private int nbExemplairesDisponibles; // Stock disponible
    private Categorie categorie;

    public Livre() {}

    public Livre(int id, String titre, String auteur, int nbExemplaires, Categorie categorie) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.nbTotalExemplaires = nbExemplaires;
        this.nbExemplairesDisponibles = nbExemplaires; // Au départ, tout le stock est dispo
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public int getNbTotalExemplaires() { return nbTotalExemplaires; }
    public void setNbTotalExemplaires(int nbTotalExemplaires) { 
        this.nbTotalExemplaires = nbTotalExemplaires; 
    }

    public int getNbExemplairesDisponibles() { return nbExemplairesDisponibles; }
    public void setNbExemplairesDisponibles(int nbExemplairesDisponibles) { 
        this.nbExemplairesDisponibles = nbExemplairesDisponibles; 
    }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    // ===== Méthodes pour gérer le stock =====
    public boolean estDisponible() {
        return nbExemplairesDisponibles > 0;
    }

    public void emprunterExemplaire() {
        if (nbExemplairesDisponibles <= 0) {
            throw new RuntimeException("Aucun exemplaire disponible pour ce livre !");
        }
        nbExemplairesDisponibles--;
    }

    public void retournerExemplaire() {
        if (nbExemplairesDisponibles < nbTotalExemplaires) {
            nbExemplairesDisponibles++;
        }
    }
}
