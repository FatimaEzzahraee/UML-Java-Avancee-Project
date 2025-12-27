package lib.model;

import java.util.List;

public class Adherent {
    private int id;
    private String nom;
    private boolean bloque;
    private Utilisateur utilisateur;
    private List<Emprunt> emprunts;

    public Adherent() {}

    public Adherent(int id, String nom, boolean bloque, Utilisateur utilisateur) {
        this.id = id;
        this.nom = nom;
        this.bloque = bloque;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isBloque() {
        return bloque;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }

    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }
}