package lib.ui;

import javax.swing.*;
import java.awt.*;

public class LivreForm extends JPanel {

    private JTextField txtIsbn, txtTitre, txtAuteur, txtStock;
    private JButton btnSave;

    public LivreForm() {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("ISBN :"));
        txtIsbn = new JTextField();
        add(txtIsbn);

        add(new JLabel("Titre :"));
        txtTitre = new JTextField();
        add(txtTitre);

        add(new JLabel("Auteur :"));
        txtAuteur = new JTextField();
        add(txtAuteur);

        add(new JLabel("Stock :"));
        txtStock = new JTextField();
        add(txtStock);

        btnSave = new JButton("Enregistrer");
        add(new JLabel());
        add(btnSave);
    }

    public String getIsbn() { return txtIsbn.getText(); }
    public String getTitre() { return txtTitre.getText(); }
    public String getAuteur() { return txtAuteur.getText(); }
    public int getStock() { return Integer.parseInt(txtStock.getText()); }
    public JButton getBtnSave() { return btnSave; }
}
