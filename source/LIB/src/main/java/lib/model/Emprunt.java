package lib.model;


import java.time.LocalDate;

public class Emprunt {
    private int id;
    private Livre livre;
    private Adherent adherent;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private String statut; // EN_COURS, RETOURNE, RETARD

    public Emprunt() {}

    public Emprunt(int id, Livre livre, Adherent adherent,
                   LocalDate dateEmprunt, LocalDate dateRetour, String statut) {
        this.id = id;
        this.livre = livre;
        this.adherent = adherent;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
