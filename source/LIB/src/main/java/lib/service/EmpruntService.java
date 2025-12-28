package lib.service;

import lib.dao.EmpruntDAO;
import lib.dao.AdherentDao;
import lib.model.Emprunt;
import lib.model.Adherent;
import lib.model.Livre;

import java.time.LocalDate;
import java.util.List;

public class EmpruntService {

    private EmpruntDAO empruntDAO = new EmpruntDAO();
    private LivreService livreService = new LivreService();
    private AdherentDao adherentDao = new AdherentDao();

    public void emprunter(int idAdherent, int idLivre) {

        Adherent a = adherentDao.chercherParId(idAdherent);
        if (a == null) {
            throw new RuntimeException("Adhérent introuvable");
        }

        Livre l = livreService.chercher(idLivre);
        if (l == null) {
            throw new RuntimeException("Livre introuvable");
        }

        if (l.getNbExemplaires() <= 0) {
            throw new RuntimeException("Stock insuffisant");
        }

        Emprunt e = new Emprunt();
        e.setAdherent(a);
        e.setLivre(l);
        e.setDateEmprunt(LocalDate.now());
        e.setStatut("EN_COURS");

        empruntDAO.ajouter(e);
        livreService.decrementerStock(idLivre);
    }

    public void retourner(int idEmprunt, int idLivre) {
        empruntDAO.retourner(idEmprunt);
        livreService.incrementerStock(idLivre);
    }

    public List<Emprunt> lister() {
        return empruntDAO.lister();
    }
}
