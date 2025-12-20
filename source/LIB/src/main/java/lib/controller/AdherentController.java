package lib.controller;

import lib.service.AdherentService;
import lib.ui.AdherentForm;

public class AdherentController {

    private AdherentForm form;
    private AdherentService service;

    public AdherentController(AdherentForm form) {
        this.form = form;
        this.service = new AdherentService();
        init();
    }

    private void init() {
        form.getBtnSave().addActionListener(e -> save());
    }

    private void save() {
        try {
            service.addAdherent(form.getNom(), form.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
