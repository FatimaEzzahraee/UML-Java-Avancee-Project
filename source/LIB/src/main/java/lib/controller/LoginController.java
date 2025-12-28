package lib.controller;

import lib.model.Utilisateur;
import lib.service.AuthService;
import lib.ui.LoginView;
import lib.util.LoggerUtil;
import lib.util.Session;

public class LoginController {

    private LoginView view;
    private AuthService service = new AuthService();

    public LoginController(LoginView view) {
        this.view = view;
        view.getBtnLogin().addActionListener(e -> {
			try {
				login();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    }

    private void login() throws Exception {
        Utilisateur u = service.login(view.getLogin(), view.getPassword());

        if (u == null) {
            view.showError("Login ou mot de passe incorrect");
            return;
        }

        Session.setCurrentUser(u);
        LoggerUtil.log(u.getUsername(), "Connexion");

        view.openDashboard();
    }

}
