package lib.controller;

import lib.service.UtilisateurService;
import lib.ui.UtilisateurForm;

import javax.swing.*;

public class UtilisateurController {
	/**
	 * Controller responsible for managing user-related actions.
	 * 
	 * This class is part of the Controller package*/

    private UtilisateurForm view;
    private UtilisateurService service;

    public UtilisateurController(UtilisateurForm view) {
        this.view = view;
        this.service = new UtilisateurService();
        initActions();
    }

    private void initActions() {

        view.getBtnAjouter().addActionListener(e -> ajouterUtilisateur());

        view.getBtnDesactiver().addActionListener(e -> desactiverUtilisateur());

        view.getBtnActiver().addActionListener(e -> activerUtilisateur());
    }

    private void ajouterUtilisateur() {
        try {
            service.creerUtilisateur(
                    view.getLogin(),
                    view.getPassword(),
                    view.getRole()
            );
            JOptionPane.showMessageDialog(view,
                    "Utilisateur ajouté avec succès");
            view.clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desactiverUtilisateur() {
        try {
            int userId = view.getSelectedUtilisateurId();
            service.desactiverUtilisateur(userId);

            JOptionPane.showMessageDialog(view,
                    "Utilisateur désactivé");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void activerUtilisateur() {
        try {
            int userId = view.getSelectedUtilisateurId();
            service.activerUtilisateur(userId);

            JOptionPane.showMessageDialog(view,
                    "Utilisateur activé");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
