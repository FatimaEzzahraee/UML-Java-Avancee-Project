package service;

import model.Utilisateur;

public class UtilisateurService {

    public boolean estAdmin(Utilisateur u) {
        return u.getRole().equalsIgnoreCase("ADMIN");
    }
}
