package lib.ui;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        setTitle("Connexion");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Login :"));
        txtLogin = new JTextField();
        panel.add(txtLogin);

        panel.add(new JLabel("Mot de passe :"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Se connecter");
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);
    }

    public String getLogin() {
        return txtLogin.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void openDashboard() {
        new MainDashboard().setVisible(true);
        dispose();
    }
}
