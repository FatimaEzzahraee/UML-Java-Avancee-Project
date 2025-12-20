package service;

import dao.UtilisateurDAO;
import model.Utilisateur;

public class AuthService {

    private UtilisateurDAO utilisateurDAO;

    public AuthService(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public Utilisateur login(String username, String password) {
        Utilisateur u = utilisateurDAO.findByUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }
}
