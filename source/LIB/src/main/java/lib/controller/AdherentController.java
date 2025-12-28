package lib.controller;

import lib.service.AdherentService;
import lib.ui.AdherentForm;
import lib.ui.AdherentList;
import lib.model.Adherent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdherentController {

    private AdherentForm form;
    private AdherentList list;
    private AdherentService service;

    public AdherentController(AdherentForm form, AdherentList list) {
        this.form = form;
        this.list = list;
        this.service = new AdherentService();

        form.getBtnSave().addActionListener(e -> ajouter());
        charger();
    }

    private void ajouter() {
        try {
            service.ajouter(form.getNom(), form.getEmail());
            charger();
            JOptionPane.showMessageDialog(null, "Adhérent ajouté");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void charger() {
        DefaultTableModel model = list.getModel();
        model.setRowCount(0);

        for (Adherent a : service.getAdherents()) {
            model.addRow(new Object[]{
                    a.getId(),
                    a.getNom(),
                    a.getEmail()
            });
        }
    }
}
