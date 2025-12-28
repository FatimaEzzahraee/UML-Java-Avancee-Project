package lib.ui;

import javax.swing.*;

import lib.controller.AdherentController;
import lib.controller.EmpruntController;
import lib.controller.LivreController;
import lib.util.Session;

import java.awt.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        setTitle("Bibliothèque - Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnLivres = new JButton("Livres");
        JButton btnEmprunts = new JButton("Emprunts");
        JButton btnAdherents = new JButton("Adhérents");

        // =========================
        // 🔐 GESTION DES RÔLES ICI
        // =========================
        if (!Session.isAdmin()) {
            btnLivres.setEnabled(false);
            btnAdherents.setEnabled(false);
        }

        btnLivres.addActionListener(e -> ouvrirLivres());
        btnAdherents.addActionListener(e -> ouvrirAdherents());
        btnEmprunts.addActionListener(e -> ouvrirEmprunts());

        JPanel panel = new JPanel();
        panel.add(btnLivres);
        panel.add(btnEmprunts);
        panel.add(btnAdherents);

        add(panel);
    }

    private void ouvrirLivres() {
        JFrame f = new JFrame("Gestion des livres");
        f.setSize(700, 400);
        f.setLayout(new BorderLayout());

        LivreForm form = new LivreForm();
        LivreList list = new LivreList();

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
}
