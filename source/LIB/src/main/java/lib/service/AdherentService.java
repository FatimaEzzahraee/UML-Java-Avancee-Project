package service;

import model.Adherent;

public class AdherentService {

    public boolean peutEmprunter(Adherent adherent) {
        return !adherent.isBloque()
               && adherent.getEmprunts().size() < 3;
    }

    public void bloquer(Adherent adherent) {
        adherent.setBloque(true);
    }
}
