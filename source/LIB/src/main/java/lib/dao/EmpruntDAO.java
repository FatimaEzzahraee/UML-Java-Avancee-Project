package lib.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lib.model.Emprunt;
import lib.model.Adherent;
import lib.model.Livre;

public class EmpruntDAO {

    private Connection conn = DBConnection.getInstance().getConnection();

    // Ajouter un emprunt
    public void ajouter(Emprunt e) {
        String sql = "INSERT INTO emprunt (adherent_id, livre_id, date_emprunt, date_retour, statut) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getAdherent().getId());
            ps.setInt(2, e.getLivre().getId());
            ps.setDate(3, Date.valueOf(e.getDateEmprunt()));
            ps.setDate(4, Date.valueOf(e.getDateRetour()));
            ps.setString(5, e.getStatut());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Modifier un emprunt
    public void modifier(Emprunt e) {
        String sql = "UPDATE emprunt SET date_retour=?, statut=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, e.getDateRetour() != null ? Date.valueOf(e.getDateRetour()) : null);
            ps.setString(2, e.getStatut());
            ps.setInt(3, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Supprimer un emprunt
    public void supprimer(int id) {
        String sql = "DELETE FROM emprunt WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Lister tous les emprunts
    public List<Emprunt> lister() {
        List<Emprunt> list = new ArrayList<>();
        String sql = "SELECT e.id, e.date_emprunt, e.date_retour, e.statut, " +
                     "a.id AS adherent_id, a.nom AS adherent_nom, " +
                     "l.id AS livre_id, l.titre AS livre_titre, l.auteur AS livre_auteur " +
                     "FROM emprunt e " +
                     "JOIN adherent a ON e.adherent_id = a.id " +
                     "JOIN livre l ON e.livre_id = l.id";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Adherent a = new Adherent();
                a.setId(rs.getInt("adherent_id"));
                a.setNom(rs.getString("adherent_nom"));

                Livre l = new Livre();
                l.setId(rs.getInt("livre_id"));
                l.setTitre(rs.getString("livre_titre"));
                l.setAuteur(rs.getString("livre_auteur"));

                Emprunt e = new Emprunt(
                    rs.getInt("id"),
                    l,
                    a,
                    rs.getDate("date_emprunt").toLocalDate(),
                    rs.getDate("date_retour") != null ? rs.getDate("date_retour").toLocalDate() : null,
                    rs.getString("statut")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
