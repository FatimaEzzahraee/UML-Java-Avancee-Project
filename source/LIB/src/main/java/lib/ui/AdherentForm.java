package lib.ui;

import javax.swing.*;
import java.awt.*;

public class AdherentForm extends JPanel {

    private JTextField txtNom, txtEmail;
    private JButton btnSave;

    public AdherentForm() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Nom :"));
        txtNom = new JTextField();
        add(txtNom);

        add(new JLabel("Email :"));
        txtEmail = new JTextField();
        add(txtEmail);

        btnSave = new JButton("Enregistrer");
        add(new JLabel());
        add(btnSave);
    }

    public String getNom() { return txtNom.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public JButton getBtnSave() { return btnSave; }
}
