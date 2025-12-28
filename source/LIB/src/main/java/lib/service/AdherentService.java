package lib.service;

import lib.dao.AdherentDao;
import lib.model.Adherent;

import java.util.List;

public class AdherentService {

    private AdherentDao dao = new AdherentDao();

    public void ajouter(String nom, String email) {
        Adherent a = new Adherent();
        a.setNom(nom);
        a.setEmail(email);
        dao.ajouter(a);
    }

    public List<Adherent> getAdherents() {
        return dao.lister();
    }
}
