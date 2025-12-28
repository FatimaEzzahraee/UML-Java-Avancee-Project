package lib.ui;

import javax.swing.*;
import java.awt.*;

public class EmpruntForm extends JPanel {

    private JTextField txtAdherentId;
    private JTextField txtLivreId;
    private JButton btnEmprunter;
    private JButton btnRetourner;

    public EmpruntForm() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("ID Adhérent :"));
        txtAdherentId = new JTextField();
        add(txtAdherentId);

        add(new JLabel("ID Livre :"));
        txtLivreId = new JTextField();
        add(txtLivreId);

        btnEmprunter = new JButton("Emprunter");
        btnRetourner = new JButton("Retourner");

        add(btnEmprunter);
        add(btnRetourner);
    }

    public int getAdherentId() {
        return Integer.parseInt(txtAdherentId.getText());
    }

    public int getLivreId() {
        return Integer.parseInt(txtLivreId.getText());
    }

    public JButton getBtnEmprunter() {
        return btnEmprunter;
    }

    public JButton getBtnRetourner() {
        return btnRetourner;
    }
}
