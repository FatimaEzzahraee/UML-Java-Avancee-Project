package lib.ui;

import javax.swing.*;
import java.awt.*;
import lib.model.Categorie;
import java.util.List;

public class LivreForm extends JPanel {

    private JTextField txtIsbn, txtTitre, txtAuteur, txtStock;
    private JComboBox<Categorie> comboCategorie;
    private JButton btnSave;

    public LivreForm(List<Categorie> categories) {
        setLayout(new GridLayout(6, 2));

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

        add(new JLabel("Catégorie :"));
        comboCategorie = new JComboBox<>();
        for (Categorie c : categories) {
            comboCategorie.addItem(c);
        }
        add(comboCategorie);

        btnSave = new JButton("Enregistrer");
        add(new JLabel());
        add(btnSave);
    }

    public String getIsbn() { return txtIsbn.getText(); }
    public String getTitre() { return txtTitre.getText(); }
    public String getAuteur() { return txtAuteur.getText(); }
    public int getStock() { return Integer.parseInt(txtStock.getText()); }
    public Categorie getCategorie() { return (Categorie) comboCategorie.getSelectedItem(); }
    public JButton getBtnSave() { return btnSave; }
}
