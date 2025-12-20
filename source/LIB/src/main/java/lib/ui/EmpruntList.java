package lib.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmpruntList extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public EmpruntList() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(
                new Object[]{"Adhérent", "Livre", "Date", "Statut"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public DefaultTableModel getModel() { return model; }
}
