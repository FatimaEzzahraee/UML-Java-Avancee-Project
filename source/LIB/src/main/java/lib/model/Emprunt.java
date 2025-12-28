package lib.model;

import java.time.LocalDate;

public class Emprunt {

    private int id;
    private Adherent adherent;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private String statut; // "RETURNE" ou "EN_COURS"
    private boolean retard; // true si dateRetour dépassée et non retourné

    public Emprunt() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }

    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }

    public LocalDate getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public boolean isRetard() { return retard; }
    public void setRetard(boolean retard) { this.retard = retard; }

    // ---------------- Méthode pour vérifier si l'emprunt est retourné ----------------
    public boolean isRetourne() {
        return "RETURNE".equalsIgnoreCase(statut);
    }

    // ---------------- Méthode pour mettre à jour le statut de retard ----------------
    public void mettreAJourRetard() {
        if (!isRetourne() && dateRetour != null) {
            this.retard = dateRetour.isBefore(LocalDate.now());
        } else {
            this.retard = false;
        }
    }

}
