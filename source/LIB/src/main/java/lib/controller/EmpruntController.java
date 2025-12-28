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

    // ===== CONSTRUCTEUR POUR ADMIN =====
    public EmpruntController(EmpruntForm form, EmpruntList list) {
        this.form = form;
        this.list = list;

        if (form != null) {
            // Actions des boutons (uniquement si form disponible)
            form.getBtnEmprunter().addActionListener(e -> emprunter());
            form.getBtnRetourner().addActionListener(e -> retourner());
        }

        // Charger la liste au démarrage
        charger();
    }

    // ===== CONSTRUCTEUR POUR MES EMPRUNTS (adhérent) =====
    public EmpruntController(EmpruntList list) {
        this.form = null; // pas de bouton Emprunter ici
        this.list = list;
        charger();
    }

    // ===== EMPRUNTER UN LIVRE =====
    private void emprunter() {
        try {
            int idAdherent = form.getAdherentId();
            int idLivre = form.getLivreId();

            service.emprunter(idAdherent, idLivre);

            // Rafraîchir la table
            charger();

            JOptionPane.showMessageDialog(null, "Emprunt effectué !");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== RETOURNER UN LIVRE =====
    private void retourner() {
        int row = list.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez un emprunt");
            return;
        }

        // Récupération de l'emprunt correspondant à la ligne
        Emprunt e = list.getEmpruntAt(row); // méthode à créer dans EmpruntList
        if (e == null) return;

        try {
            service.retourner(e.getId(), e.getLivre().getId());

            // Rafraîchir la table
            charger();

            JOptionPane.showMessageDialog(null, "Livre retourné et stock mis à jour !");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== AFFICHER LES EMPRUNTS D’UN ADHÉRENT =====
    public void afficherMesEmprunts(int idAdherent) {
        DefaultTableModel model = list.getModel();
        model.setRowCount(0); // vider le tableau

        boolean estAdherent = model.getColumnCount() == 3; // si 3 colonnes, c'est affichage adhérent

        for (Emprunt e : service.mesEmprunts(idAdherent)) {
            String statutAffiche = e.getStatut();

            if ("EN_COURS".equals(statutAffiche) && 
                e.getDateEmprunt().plusDays(14).isBefore(java.time.LocalDate.now())) {
                statutAffiche = "RETARD";
            }

            if (estAdherent) {
                model.addRow(new Object[]{
                    e.getLivre().getTitre(),
                    e.getDateEmprunt(),
                    statutAffiche
                });
            } else {
                model.addRow(new Object[]{
                    e.getId(),
                    e.getAdherent().getNom(),
                    e.getLivre().getTitre(),
                    e.getDateEmprunt(),
                    statutAffiche
                });
            }
        }
    }
   

    // ===== CHARGER LA LISTE DES EMPRUNTS =====
    private void charger() {
        if (list == null) return;

        DefaultTableModel model = list.getModel();
        model.setRowCount(0);

        for (Emprunt e : service.lister()) {
            String statutAffiche = e.getStatut();

            // Vérifier si l'emprunt est en retard
            if ("EN_COURS".equals(statutAffiche) && e.getDateEmprunt().plusDays(14).isBefore(java.time.LocalDate.now())) {
                statutAffiche = "RETARD";
            }

            model.addRow(new Object[]{
                    e.getId(),
                    e.getAdherent().getNom(),
                    e.getLivre().getTitre(),
                    e.getDateEmprunt(),
                    statutAffiche
            });
        }
    }
}
