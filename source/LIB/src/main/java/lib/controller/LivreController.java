package lib.controller;

import lib.service.LivreService;
import lib.dao.EmpruntDAO;
import lib.ui.LivreForm;
import lib.ui.LivreList;
import lib.model.Livre;
import lib.model.Categorie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LivreController {

    private LivreForm form;
    private LivreList list;
    private LivreService service = new LivreService();
    private EmpruntDAO empruntDao = new EmpruntDAO(); // pour vérifier les emprunts actifs

    public LivreController(LivreForm form, LivreList list) {
        this.form = form;
        this.list = list;

        form.getBtnSave().addActionListener(e -> ajouter());
        list.getBtnDelete().addActionListener(e -> supprimer()); // suppression
        charger();
    }

    private void ajouter() {
        Categorie cat = form.getCategorie(); 
        service.ajouter(form.getTitre(), form.getAuteur(), form.getStock(), cat);
        charger();
    }

    private void supprimer() {
        int row = list.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez un livre à supprimer !");
            return;
        }

        int id = (int) list.getModel().getValueAt(row, 0);
        Livre l = service.chercher(id);

        if (!empruntDao.getEmpruntsActifs(l).isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Impossible de supprimer ce livre : il est actuellement emprunté !",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        service.supprimer(id);
        charger();
    }

    private void charger() {
        DefaultTableModel model = list.getModel();
        model.setRowCount(0);
        for (Livre l : service.lister()) {
            model.addRow(new Object[]{
                    l.getId(), 
                    l.getTitre(), 
                    l.getAuteur(), 
                    l.getNbExemplairesDisponibles(),
                    l.getCategorie() != null ? l.getCategorie().getNom() : ""
            });
        }
    }
}
