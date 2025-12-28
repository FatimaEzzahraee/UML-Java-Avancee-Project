package lib.controller;

import lib.service.EmpruntService;
import lib.ui.EmpruntForm;
import lib.ui.EmpruntList;
import lib.model.Emprunt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EmpruntController {

    private EmpruntForm form;
    private EmpruntList list;
    private EmpruntService service = new EmpruntService();

    public EmpruntController(EmpruntForm form, EmpruntList list) {
        this.form = form;
        this.list = list;

        form.getBtnEmprunter().addActionListener(e -> emprunter());
        form.getBtnRetourner().addActionListener(e -> retourner());

        charger();
    }

    private void emprunter() {
        try {
            service.emprunter(form.getAdherentId(), form.getLivreId());
            charger();
            JOptionPane.showMessageDialog(null, "Emprunt effectué");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void retourner() {
        int row = list.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez un emprunt");
            return;
        }

        // Récupérer l'ID de l'emprunt (colonne 0)
        int idEmprunt = (int) list.getModel().getValueAt(row, 0);

        // Récupérer le livre depuis la liste métier
        Emprunt emprunt = service.lister().get(row);
        int idLivre = emprunt.getLivre().getId();

        service.retourner(idEmprunt, idLivre);
        charger();

        JOptionPane.showMessageDialog(null, "Livre retourné");
    }

    private void charger() {
        DefaultTableModel model = list.getModel();
        model.setRowCount(0);

        for (Emprunt e : service.lister()) {
            model.addRow(new Object[]{
                    e.getId(),
                    e.getAdherent().getNom(),
                    e.getLivre().getTitre(),
                    e.getDateEmprunt(),
                    e.getStatut()
            });
        }
    }
}
