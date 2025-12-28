package lib.controller;

import lib.dao.CategorieDAO;
import lib.model.Categorie;
import lib.ui.CategorieForm;
import lib.ui.CategorieList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CategorieController {

    private CategorieForm form;
    private CategorieList list;
    private CategorieDAO dao = new CategorieDAO();

    public CategorieController(CategorieForm form, CategorieList list) {
        this.form = form;
        this.list = list;

        form.getBtnSave().addActionListener(e -> ajouter());
        list.getBtnAdd().addActionListener(e -> form.setVisible(true));
        list.getBtnDelete().addActionListener(e -> supprimer());

        charger();
    }

    private void ajouter() {
        String nom = form.getNom();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le nom de la catégorie est obligatoire !");
            return;
        }

        Categorie c = new Categorie();
        c.setNom(nom);
        dao.ajouter(c);
        charger();
        form.setNom("");
    }

    private void supprimer() {
        int row = list.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une catégorie à supprimer");
            return;
        }

        int id = (int) list.getModel().getValueAt(row, 0);
        try {
            dao.supprimer(id);
            charger();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void charger() {
        List<Categorie> categories = dao.lister();
        DefaultTableModel model = list.getModel();
        model.setRowCount(0);
        for (Categorie c : categories) {
            model.addRow(new Object[]{c.getId(), c.getNom()});
        }
    }
}
