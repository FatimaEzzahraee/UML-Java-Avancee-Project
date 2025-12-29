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

    // ===== EMPRUNTER UN LIVRE =====
    public void emprunter(int idAdherent, int idLivre) {

        Adherent a = adherentDao.chercherParId(idAdherent);
        if (a == null) {
            throw new RuntimeException("Adhérent introuvable");
        }

        if (!a.isActif()) {
            throw new RuntimeException("Le compte de cet adhérent est inactif !");
        }
        if (!"USER".equals(a.getRole())) {
            throw new RuntimeException("Seul un adhérent avec le rôle USER peut emprunter !");
        }

        a.mettreAJourBlocage();
        if (a.isBloque()) {
            throw new RuntimeException("Cet adhérent est bloqué pour retard > 10 jours !");
        }

        
        
     // --- CONTRAINTE : max 3 prêts actifs ---
        // On compte les emprunts actifs depuis la base pour être sûr
        long nbActifs = empruntDAO.lister().stream()
                .filter(emp -> emp.getAdherent().getId() == a.getId())
                .filter(emp -> "EN_COURS".equalsIgnoreCase(emp.getStatut()))
                .count();

        if (nbActifs >= 3) {
            throw new RuntimeException("Limite de 3 prêts actifs atteinte pour cet adhérent !");
        }


        Livre l = livreService.chercher(idLivre);
        if (l == null) {
            throw new RuntimeException("Livre introuvable");
        }
        if (l.getNbExemplairesDisponibles() <= 0) {
            throw new RuntimeException("Stock insuffisant");
        }

        Emprunt e = new Emprunt();
        e.setAdherent(a);
        e.setLivre(l);
        e.setDateEmprunt(LocalDate.now());
        e.setStatut("EN_COURS");

        a.getEmpruntsActifs().add(e);
        empruntDAO.ajouter(e);
    }

    // ===== RETOURNER UN LIVRE =====
    public void retourner(int idEmprunt, int idLivre) {
        empruntDAO.retourner(idEmprunt);
        livreService.incrementerStock(idLivre);

        Emprunt e = empruntDAO.chercherParId(idEmprunt);
        if (e != null) {
            e.getAdherent().getEmpruntsActifs()
                .removeIf(emp -> emp.getId() == idEmprunt);
            e.getAdherent().mettreAJourBlocage();
        }
    }

    // =====  MES EMPRUNTS =====
    public List<Emprunt> mesEmprunts(int idAdherent) {
        Adherent a = adherentDao.chercherParId(idAdherent);
        if (a == null) {
            throw new RuntimeException("Adhérent introuvable");
        }

        // Retourne uniquement les emprunts de cet adhérent
        return empruntDAO.listerParAdherent(a);
    }

    // ===== LISTER TOUS LES EMPRUNTS (ADMIN) =====
    public List<Emprunt> lister() {
        return empruntDAO.lister();
    }
}
