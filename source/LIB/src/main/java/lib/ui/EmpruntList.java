package lib.ui;

import lib.model.Emprunt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmpruntList extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private List<Emprunt> emprunts = new ArrayList<>(); // liste utilisée pour remplir le JTable

    public EmpruntList() {
        model = new DefaultTableModel(new Object[]{"ID", "Adhérent", "Livre", "Date", "Statut"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table));
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }

    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
        model.setRowCount(0);
        for (Emprunt e : emprunts) {
            model.addRow(new Object[]{
                    e.getId(),
                    e.getAdherent().getNom(),
                    e.getLivre().getTitre(),
                    e.getDateEmprunt(),
                    e.getStatut()
            });
        }
    }

    // ===== Méthode à ajouter pour récupérer un emprunt selon la ligne =====
    public Emprunt getEmpruntAt(int row) {
        if (row >= 0 && row < emprunts.size()) {
            return emprunts.get(row);
        }
        return null;
    }
}
