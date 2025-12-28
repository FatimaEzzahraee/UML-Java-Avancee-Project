package lib.service;

import lib.dao.UtilisateurDAO;
import lib.model.Utilisateur;

public class UtilisateurService {

    private UtilisateurDAO dao = new UtilisateurDAO();

    public void creerUtilisateur(String username, String password, String role) {
        Utilisateur u = new Utilisateur();
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);
        dao.ajouter(u);
    }

    public void activerUtilisateur(int id) {
        dao.activer(id);
    }

    public void desactiverUtilisateur(int id) {
        dao.desactiver(id);
    }
}
