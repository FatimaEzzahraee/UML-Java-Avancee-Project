package lib.ui;

import javax.swing.*;
import java.awt.*;

public class EmpruntForm extends JPanel {

    private JTextField txtAdherentId, txtIsbn;
    private JButton btnEmprunter, btnRetourner;

    public EmpruntForm() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("ID Adhérent :"));
        txtAdherentId = new JTextField();
        add(txtAdherentId);

        add(new JLabel("ISBN Livre :"));
        txtIsbn = new JTextField();
        add(txtIsbn);

        btnEmprunter = new JButton("Emprunter");
        btnRetourner = new JButton("Retourner");

        add(btnEmprunter);
        add(btnRetourner);
    }

    public int getAdherentId() { return Integer.parseInt(txtAdherentId.getText()); }
    public String getIsbn() { return txtIsbn.getText(); }
    public JButton getBtnEmprunter() { return btnEmprunter; }
    public JButton getBtnRetourner() { return btnRetourner; }
}
