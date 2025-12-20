package lib.controller;

import lib.service.LivreService;
import lib.ui.LivreForm;

public class LivreController {

    private LivreForm form;
    private LivreService service;

    public LivreController(LivreForm form) {
        this.form = form;
        this.service = new LivreService();
        init();
    }

    private void init() {
        form.getBtnSave().addActionListener(e -> save());
    }

    private void save() {
        try {
            service.addLivre(
                    form.getIsbn(),
                    form.getTitre(),
                    form.getAuteur(),
                    form.getStock()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
