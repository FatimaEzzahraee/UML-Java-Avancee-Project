package lib.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Adherent {

    private int id;
    private String nom;
    private String email;
    private boolean bloque;

    // Nouveau : compte actif et rôle
    private boolean actif = true;       // Par défaut, compte actif
    private String role = "USER";       // Par défaut, rôle USER

    // Liste des emprunts actifs
    private List<Emprunt> empruntsActifs = new ArrayList<>();

    public Adherent() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isBloque() { return bloque; }
    public void setBloque(boolean bloque) { this.bloque = bloque; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // ---------------- Contrôle des emprunts ----------------
    public List<Emprunt> getEmpruntsActifs() { return empruntsActifs; }
    public void setEmpruntsActifs(List<Emprunt> empruntsActifs) { this.empruntsActifs = empruntsActifs; }

    public int getNombreEmpruntsActifs() {
        int count = 0;
        for (Emprunt e : empruntsActifs) {
            if (!e.isRetourne()) {
                count++;
            }
        }
        return count;
    }

    public boolean peutEmprunter() {
        // L'adhérent peut emprunter s'il a moins de 3 prêts actifs, n'est pas bloqué et compte actif
        return getNombreEmpruntsActifs() < 3 && !bloque && actif && "USER".equals(role);
    }

    // ===== Gestion du retard > 10 jours =====
    public boolean aEmpruntEnRetard() {
        LocalDate aujourdHui = LocalDate.now();
        for (Emprunt e : empruntsActifs) {
            if (!e.isRetourne() && e.getDateEmprunt().plusDays(14).isBefore(aujourdHui)) {
                return true; // Au moins un emprunt est en retard
            }
        }
        return false;
    }

    public void mettreAJourBlocage() {
        this.bloque = aEmpruntEnRetard();
    }
}


