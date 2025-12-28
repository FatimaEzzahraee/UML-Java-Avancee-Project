package lib.controller;

import lib.service.LivreService;
import lib.ui.LivreForm;
import lib.ui.LivreList;
import lib.model.Livre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LivreController {

    private LivreForm form;
    private LivreList list;
    private LivreService service = new LivreService();

    public LivreController(LivreForm form, LivreList list) {
        this.form = form;
        this.list = list;

        form.getBtnSave().addActionListener(e -> ajouter());
        charger();
    }

    private void ajouter() {
        service.ajouter(form.getTitre(), form.getAuteur(), form.getStock());
        charger();
    }

    private void charger() {
        DefaultTableModel model = list.getModel();
        model.setRowCount(0);
        for (Livre l : service.lister()) {
            model.addRow(new Object[]{
                    l.getId(), l.getTitre(), l.getAuteur(), l.getNbExemplaires()
            });
        }
    }
}
