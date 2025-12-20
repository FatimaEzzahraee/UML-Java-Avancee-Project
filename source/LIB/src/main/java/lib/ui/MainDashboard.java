package lib.ui;

import javax.swing.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        setTitle("Bibliothèque - Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        JButton btnLivres = new JButton("Livres");
        JButton btnEmprunts = new JButton("Emprunts");
        JButton btnAdherents = new JButton("Adhérents");

        panel.add(btnLivres);
        panel.add(btnEmprunts);
        panel.add(btnAdherents);

        add(panel);
    }
}
