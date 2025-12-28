package lib.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import lib.controller.AdherentController;
import lib.controller.CategorieController;
import lib.controller.EmpruntController;
import lib.controller.LivreController;
import lib.model.Categorie;
import lib.model.Livre;
import lib.service.CategorieService;
import lib.service.EmpruntService;
import lib.service.LivreService;
import lib.util.Session;

public class MainDashboard extends JFrame {
	 private int idAdherent = 1;

    public MainDashboard() {
        setTitle("Bibliothèque - Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnLivres = new JButton("Livres");
        JButton btnCategories = new JButton("Catégories");
        JButton btnEmprunts = new JButton("Emprunts");
        JButton btnAdherents = new JButton("Adhérents");
        JButton btnCatalogue = new JButton("Catalogue");
        JButton btnMesEmprunts = new JButton("Mes emprunts");

        // Gestion des rôles : désactiver certains boutons pour non-admin
        if (!Session.isAdmin()) {
            btnAdherents.setEnabled(false);
            btnCategories.setEnabled(false);
            btnLivres.setEnabled(false);
            btnEmprunts.setEnabled(false);
        }else {
        	btnMesEmprunts.setEnabled(false);
        }
        

        btnLivres.addActionListener(e -> ouvrirLivres());
        btnCategories.addActionListener(e -> ouvrirCategories());
        btnAdherents.addActionListener(e -> ouvrirAdherents());
        btnEmprunts.addActionListener(e -> ouvrirEmprunts());
        btnCatalogue.addActionListener(e -> ouvrirCatalogue());
        btnMesEmprunts.addActionListener(e -> ouvrirMesEmprunts( idAdherent = 1));

        JPanel panel = new JPanel();
        
        panel.add(btnMesEmprunts);
        panel.add(btnCatalogue);
        panel.add(btnLivres);
        panel.add(btnCategories);
        panel.add(btnEmprunts);
        panel.add(btnAdherents);

        add(panel);
    }

    private void ouvrirLivres() {
    	
        JFrame f = new JFrame("Gestion des livres");
        f.setSize(800, 400);
        f.setLayout(new BorderLayout());

        // Récupérer la liste des catégories depuis la base
        CategorieService categorieService = new CategorieService();
        List<Categorie> categories = categorieService.lister();

        // Formulaire et liste
        LivreForm form = new LivreForm(categories);
        // Passer le paramètre isAdmin pour activer les boutons
        LivreList list = new LivreList(Session.isAdmin());

        // Contrôleur
        new LivreController(form, list);

        f.add(form, BorderLayout.NORTH);
        f.add(list, BorderLayout.CENTER);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void ouvrirAdherents() {
        JFrame f = new JFrame("Gestion des adhérents");
        f.setSize(700, 400);
        f.setLayout(new BorderLayout());

        AdherentForm form = new AdherentForm();
        AdherentList list = new AdherentList();

        new AdherentController(form, list);

        f.add(form, BorderLayout.NORTH);
        f.add(list, BorderLayout.CENTER);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void ouvrirEmprunts() {
        JFrame f = new JFrame("Gestion des emprunts");
        f.setSize(700, 400);
        f.setLayout(new BorderLayout());

        EmpruntForm form = new EmpruntForm();
        EmpruntList list = new EmpruntList();

        new EmpruntController(form, list);

        f.add(form, BorderLayout.NORTH);
        f.add(list, BorderLayout.CENTER);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void ouvrirCategories() {
        JFrame f = new JFrame("Gestion des catégories");
        f.setSize(500, 300);
        f.setLayout(new BorderLayout());

        CategorieForm form = new CategorieForm();
        CategorieList list = new CategorieList();

        new CategorieController(form, list);

        f.add(form, BorderLayout.NORTH);
        f.add(list, BorderLayout.CENTER);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    private void ouvrirCatalogue() {
        JFrame f = new JFrame("Catalogue des livres");
        f.setSize(800, 400);
        f.setLayout(new BorderLayout());

        LivreService service = new LivreService();
        List<Livre> livres = service.lister();

        LivreList catalogue = new LivreList(!Session.isAdmin()); // désactiver boutons si non-admin
        catalogue.setLivres(livres);

        // Ajouter le bouton Emprunter pour les adhérents (non-admin)
        if (!Session.isAdmin()) {
            JButton btnEmprunter = new JButton("Emprunter");
            btnEmprunter.addActionListener(e -> {
                int row = catalogue.getTable().getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Sélectionnez un livre à emprunter !");
                    return;
                }
                int idLivre = (int) catalogue.getModel().getValueAt(row, 0);
                EmpruntService empruntService = new EmpruntService();
                try {
                    empruntService.emprunter(idAdherent, idLivre);
                    JOptionPane.showMessageDialog(null, "Livre emprunté !");
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            JPanel topPanel = new JPanel();
            topPanel.add(btnEmprunter);
            f.add(topPanel, BorderLayout.NORTH);
        }

        f.add(catalogue, BorderLayout.CENTER);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    private void ouvrirMesEmprunts(int idAdherent) {
        JFrame f = new JFrame("Mes emprunts");
        f.setSize(700, 400);
        f.setLayout(new BorderLayout());

        EmpruntList list = new EmpruntList(); // juste la table
        EmpruntController controller = new EmpruntController(list);

        // Afficher uniquement les emprunts de cet adhérent
        controller.afficherMesEmprunts(idAdherent);

        f.add(list, BorderLayout.CENTER);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainDashboard().setVisible(true);
        });
    }
}
