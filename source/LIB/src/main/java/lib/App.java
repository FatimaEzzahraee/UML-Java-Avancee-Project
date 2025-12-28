package lib;

import javax.swing.SwingUtilities;
import lib.controller.LoginController;
import lib.ui.LoginView;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}
