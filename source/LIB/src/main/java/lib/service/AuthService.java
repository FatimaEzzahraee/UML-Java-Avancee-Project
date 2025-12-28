package lib.service;

import lib.dao.UtilisateurDAO;
import lib.model.Utilisateur;

public class AuthService {

    private UtilisateurDAO dao = new UtilisateurDAO();

    public Utilisateur login(String username, String password) throws Exception {
        Utilisateur u = dao.chercherParUsername(username);

        if (u == null) {
            throw new Exception("Login ou mot de passe incorrect");
        }

        if (!u.getPassword().equals(password)) {
            throw new Exception("Login ou mot de passe incorrect");
        }

        return u;
    }
}
