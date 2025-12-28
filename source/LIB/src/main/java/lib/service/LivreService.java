package lib.service;

import lib.dao.LivreDAO;
import lib.model.Livre;
import java.util.List;

public class LivreService {

    private LivreDAO dao = new LivreDAO();

    public void ajouter(String titre, String auteur, int stock) {
        Livre l = new Livre();
        l.setTitre(titre);
        l.setAuteur(auteur);
        l.setNbExemplaires(stock);
        dao.ajouter(l);
    }

    public List<Livre> lister() {
        return dao.lister();
    }

    public Livre chercher(int id) {
        return dao.chercherParId(id);
    }

    public void decrementerStock(int id) {
        dao.decrementerStock(id);
    }

    public void incrementerStock(int id) {
        dao.incrementerStock(id);
    }
}
