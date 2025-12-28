package lib.service;

import lib.dao.CategorieDAO;
import lib.model.Categorie;
import java.util.List;

public class CategorieService {

    private CategorieDAO dao = new CategorieDAO();

    // ===== AJOUTER UNE CATEGORIE =====
    public void ajouter(String nom) {
        Categorie c = new Categorie();
        c.setNom(nom);
        dao.ajouter(c);
    }

    // ===== MODIFIER UNE CATEGORIE =====
    public void modifier(int id, String nom) {
        Categorie c = dao.chercherParId(id);
        if (c != null) {
            c.setNom(nom);
            dao.modifier(c);
        } else {
            throw new RuntimeException("Catégorie introuvable !");
        }
    }

    // ===== SUPPRIMER UNE CATEGORIE =====
    public void supprimer(int id) {
        dao.supprimer(id);
    }

    // ===== LISTER LES CATEGORIES =====
    public List<Categorie> lister() {
        return dao.lister();
    }

    // ===== CHERCHER PAR ID =====
    public Categorie chercher(int id) {
        return dao.chercherParId(id);
    }
}
