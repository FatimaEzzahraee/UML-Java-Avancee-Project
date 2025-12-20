package lib.controller;

import lib.service.EmpruntService;
import lib.ui.EmpruntForm;

public class EmpruntController {

    private EmpruntForm form;
    private EmpruntService service;

    public EmpruntController(EmpruntForm form) {
        this.form = form;
        this.service = new EmpruntService();
        init();
    }

    private void init() {
        form.getBtnEmprunter().addActionListener(e -> emprunter());
        form.getBtnRetourner().addActionListener(e -> retourner());
    }

    private void emprunter() {
        try {
            service.emprunter(form.getAdherentId(), form.getIsbn());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retourner() {
        try {
            service.retourner(form.getAdherentId(), form.getIsbn());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
