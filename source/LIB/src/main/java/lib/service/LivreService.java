package lib.service;

import lib.dao.LivreDAO;
import lib.dao.EmpruntDAO;
import lib.model.Categorie;
import lib.model.Livre;
import java.util.List;

public class LivreService {

    private LivreDAO dao = new LivreDAO();
    private EmpruntDAO empruntDao = new EmpruntDAO(); // DAO pour vérifier les emprunts actifs

    // ===== AJOUTER UN LIVRE =====
    public void ajouter(String titre, String auteur, int stock, Categorie categorie) {
        Livre l = new Livre();
        l.setTitre(titre);
        l.setAuteur(auteur);
        l.setNbTotalExemplaires(stock);
        l.setNbExemplairesDisponibles(stock); // au départ, tout le stock est dispo
        l.setCategorie(categorie);
        dao.ajouter(l);
    }

    // ===== LISTER LES LIVRES =====
    public List<Livre> lister() {
        return dao.lister();
    }

    // ===== CHERCHER UN LIVRE =====
    public Livre chercher(int id) {
        return dao.chercherParId(id);
    }

    // ===== DECREMENTER LE STOCK DISPONIBLE =====
    public void decrementerStock(int id) {
        Livre l = dao.chercherParId(id);
        if (l != null) {
            l.emprunterExemplaire();  // décrémente le stock disponible
            dao.mettreAJour(l);       // sauvegarde dans la base
        }
    }

    // ===== INCREMENTER LE STOCK DISPONIBLE =====
    public void incrementerStock(int id) {
        Livre l = dao.chercherParId(id);
        if (l != null) {
            l.retournerExemplaire();  // incrémente le stock disponible
            dao.mettreAJour(l);       // sauvegarde dans la base
        }
    }

    // ===== SUPPRIMER UN LIVRE AVEC VERIFICATION =====
    public void supprimer(int idLivre) {
        Livre l = dao.chercherParId(idLivre);
        if (l == null) {
            throw new RuntimeException("Livre introuvable !");
        }

        // Vérifier s'il y a des emprunts actifs
        if (!empruntDao.getEmpruntsActifs(l).isEmpty()) {
            throw new RuntimeException("Impossible de supprimer ce livre : il est actuellement emprunté !");
        }

        // Supprimer si aucun emprunt actif
        dao.supprimer(idLivre);
    }
}
