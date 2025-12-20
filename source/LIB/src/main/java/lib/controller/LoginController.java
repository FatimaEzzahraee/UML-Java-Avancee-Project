package lib.controller;

import lib.service.AuthService;
import lib.ui.LoginView;

public class LoginController {

    private LoginView view;
    private AuthService service;

    public LoginController(LoginView view) {
        this.view = view;
        this.service = new AuthService();
        init();
    }

    private void init() {
        view.getBtnLogin().addActionListener(e -> login());
    }

    private void login() {
        try {
            service.login(view.getLogin(), view.getPassword());
            view.openDashboard();
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }
    }
}
