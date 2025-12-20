package lib.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdherentList extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public AdherentList() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(
                new Object[]{"ID", "Nom", "Email"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public DefaultTableModel getModel() { return model; }
}
