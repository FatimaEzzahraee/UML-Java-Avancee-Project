package lib.ui;

import javax.swing.*;
import java.awt.*;

public class CategorieForm extends JPanel {

    private JTextField txtNom;
    private JButton btnSave;

    public CategorieForm() {
        setLayout(new GridLayout(2, 2));

        add(new JLabel("Nom de la catégorie :"));
        txtNom = new JTextField();
        add(txtNom);

        btnSave = new JButton("Enregistrer");
        add(new JLabel());
        add(btnSave);
    }

    public String getNom() {
        return txtNom.getText();
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public void setNom(String nom) {
        txtNom.setText(nom);
    }
}
