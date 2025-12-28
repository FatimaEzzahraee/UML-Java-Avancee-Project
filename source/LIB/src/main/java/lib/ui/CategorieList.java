package lib.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategorieList extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnDelete;

    public CategorieList() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Nom"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel p = new JPanel();
        btnAdd = new JButton("Ajouter");
        btnDelete = new JButton("Supprimer");
        p.add(btnAdd);
        p.add(btnDelete);

        add(p, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }
}
