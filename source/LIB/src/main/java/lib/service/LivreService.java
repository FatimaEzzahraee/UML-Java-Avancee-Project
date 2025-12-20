package service;

import model.Livre;

public class LivreService {

    public boolean estDisponible(Livre livre) {
        return livre.getNbExemplaires() > 0;
    }

    public void decrementerExemplaire(Livre livre) {
        livre.setNbExemplaires(livre.getNbExemplaires() - 1);
    }

    public void incrementerExemplaire(Livre livre) {
        livre.setNbExemplaires(livre.getNbExemplaires() + 1);
    }
}
