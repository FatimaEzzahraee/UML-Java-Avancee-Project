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
    private boolean estAdmin; // true si admin, false si adhérent

    // ===== CONSTRUCTEUR POUR ADMIN =====
    public EmpruntController(EmpruntForm form, EmpruntList list) {
        this.form = form;
        this.list = list;
        this.estAdmin = true;

        if (form != null) {
            form.getBtnEmprunter().addActionListener(e -> emprunter());
            form.getBtnRetourner().addActionListener(e -> retourner());
        }

        charger(); // charge la table
    }

    // ===== CONSTRUCTEUR POUR ADHÉRENT =====
    public EmpruntController(EmpruntList list) {
        this.form = null;
        this.list = list;
        this.estAdmin = false;

        charger();
    }

    // ===== EMPRUNTER UN LIVRE =====
    private void emprunter() {
        try {
            // Récupérer l'ID de l'adhérent connecté
            int idAdherent = form.getAdherentId(); // si tu passes l'adhérent connecté, remplace par adherentConnecte.getId()
            int idLivre = form.getLivreId();

            service.emprunter(idAdherent, idLivre);

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

        try {
            // Récupération de l'ID de l'emprunt
            int idEmprunt = (int) list.getModel().getValueAt(row, estAdmin ? 0 : 0); // pour adhérent, on peut stocker l'id dans un champ invisible si nécessaire

            // ID du livre correspondant
            Emprunt e = service.lister().stream()
                    .filter(emp -> emp.getId() == idEmprunt)
                    .findFirst()
                    .orElse(null);

            if (e != null) {
                service.retourner(e.getId(), e.getLivre().getId());
            
            }

            charger();
            JOptionPane.showMessageDialog(null, "Livre retourné et stock mis à jour !");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== CHARGER LA LISTE DES EMPRUNTS =====
    private void charger() {
        if (list == null) return;

        initialiserModel();

        DefaultTableModel model = list.getModel();

        for (Emprunt e : service.lister()) {
            String statutAffiche = e.getStatut();
            if ("EN_COURS".equals(statutAffiche) &&
                    e.getDateEmprunt().plusDays(14).isBefore(java.time.LocalDate.now())) {
                statutAffiche = "RETARD";
            }

            if (estAdmin) {
                model.addRow(new Object[]{
                        e.getId(),
                        e.getAdherent().getNom(),
                        e.getLivre().getTitre(),
                        e.getDateEmprunt(),
                        statutAffiche
                });
            } else {
                model.addRow(new Object[]{
                        e.getLivre().getTitre(),
                        e.getDateEmprunt(),
                        statutAffiche
                });
            }
        }
    }

    // ===== AFFICHER LES EMPRUNTS D’UN ADHÉRENT CONNECTÉ =====
    public void afficherMesEmprunts(int idAdherent) {
        if (list == null) return;

        initialiserModel();

        DefaultTableModel model = list.getModel();

        for (Emprunt e : service.mesEmprunts(idAdherent)) {
            String statutAffiche = e.getStatut();
            if ("EN_COURS".equals(statutAffiche) &&
                    e.getDateEmprunt().plusDays(14).isBefore(java.time.LocalDate.now())) {
                statutAffiche = "RETARD";
            }

            model.addRow(new Object[]{
                    e.getLivre().getTitre(),
                    e.getDateEmprunt(),
                    statutAffiche
            });
        }
    }

    // ===== INITIALISER LE MODELE DE LA TABLE SELON UTILISATEUR =====
    private void initialiserModel() {
        String[] colonnes;
        if (estAdmin) {
            colonnes = new String[]{"ID", "Adhérent", "Livre", "Date", "Statut"};
        } else {
            colonnes = new String[]{"Livre", "Date", "Statut"};
        }
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        list.setModel(model);
    }
}
