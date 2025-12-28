package lib.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

import lib.model.Livre;

public class LivreList extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtRecherche;
    private TableRowSorter<DefaultTableModel> sorter;

    private JButton btnAdd;    // bouton Ajouter
    private JButton btnDelete; // bouton Supprimer

    public LivreList(boolean isAdmin) {
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(
                new Object[]{"ISBN", "Titre", "Auteur", "Stock dispo", "Catégorie"}, 0);
        table = new JTable(model);

        // Ajout du TableRowSorter pour filtrer
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Barre de recherche
        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recherchePanel.add(new JLabel("Rechercher : "));
        txtRecherche = new JTextField(20);
        recherchePanel.add(txtRecherche);
        add(recherchePanel, BorderLayout.NORTH);

        // Événement de filtrage
        txtRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filter() {
                String text = txtRecherche.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 2));
                }
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });

        // Boutons seulement pour admin
        if (isAdmin) {
            JPanel p = new JPanel();
            btnAdd = new JButton("Ajouter");
            btnDelete = new JButton("Supprimer");
            p.add(btnAdd);
            p.add(btnDelete);
            add(p, BorderLayout.SOUTH);
        }
    }

    // ---------------- Getters pour controller ----------------
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }

    public void setLivres(List<Livre> livres) {
        model.setRowCount(0);
        for (Livre l : livres) {
            model.addRow(new Object[]{
                l.getId(),
                l.getTitre(),
                l.getAuteur(),
                l.getNbExemplairesDisponibles(),
                l.getCategorie() != null ? l.getCategorie().getNom() : ""
            });
        }
    }
}
