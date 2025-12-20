package service;

import model.*;
import java.time.LocalDate;

public class EmpruntService {

    private AdherentService adherentService;
    private LivreService livreService;

    public EmpruntService(AdherentService adherentService,
                          LivreService livreService) {
        this.adherentService = adherentService;
        this.livreService = livreService;
    }

    public Emprunt emprunter(Adherent adherent, Livre livre) {

        if (!adherentService.peutEmprunter(adherent)) {
            return null;
        }

        if (!livreService.estDisponible(livre)) {
            return null;
        }

        livreService.decrementerExemplaire(livre);

        return new Emprunt(
                0,
                livre,
                adherent,
                LocalDate.now(),
                null,
                "EN_COURS"
        );
    }

    public void retourner(Emprunt emprunt) {
        emprunt.setDateRetour(LocalDate.now());
        emprunt.setStatut("RETOURNE");
        livreService.incrementerExemplaire(emprunt.getLivre());
    }
}
