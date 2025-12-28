package lib.ui;

import lib.model.Emprunt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmpruntList extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private List<Emprunt> emprunts = new ArrayList<>();

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

    public Emprunt getEmpruntAt(int row) {
        if (row >= 0 && row < emprunts.size()) {
            return emprunts.get(row);
        }
        return null;
    }
}
