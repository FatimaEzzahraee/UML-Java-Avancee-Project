package lib.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LivreList extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnDelete;

    public LivreList() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new Object[]{"ISBN", "Titre", "Auteur", "Stock"}, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel p = new JPanel();
        btnAdd = new JButton("Ajouter");
        btnDelete = new JButton("Supprimer");
        p.add(btnAdd);
        p.add(btnDelete);

        add(p, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModel() { return model; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTable getTable() {
        return table;
    }

}
