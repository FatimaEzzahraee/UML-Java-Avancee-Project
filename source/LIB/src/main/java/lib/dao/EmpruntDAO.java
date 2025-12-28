package lib.dao;

import lib.model.Emprunt;
import lib.model.Adherent;
import lib.model.Livre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {

    private Connection conn = DBConnection.getInstance().getConnection();

    // ===== AJOUTER UN EMPRUNT =====
    public void ajouter(Emprunt e) {
        String sql = "INSERT INTO emprunt (adherent_id, livre_id, date_emprunt, statut) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getAdherent().getId());
            ps.setInt(2, e.getLivre().getId());
            ps.setDate(3, Date.valueOf(e.getDateEmprunt()));
            ps.setString(4, e.getStatut());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // ===== RETOURNER UN EMPRUNT =====
    public void retourner(int idEmprunt) {
        String sql = "UPDATE emprunt SET date_retour=?, statut='RETOURNE' WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, idEmprunt);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // ===== LISTER LES EMPRUNTS =====
    public List<Emprunt> lister() {
        List<Emprunt> list = new ArrayList<>();

        String sql = """
            SELECT e.id, e.date_emprunt, e.date_retour, e.statut,
                   a.id aid, a.nom,
                   l.id lid, l.titre
            FROM emprunt e
            JOIN adherent a ON e.adherent_id = a.id
            JOIN livre l ON e.livre_id = l.id
        """;

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Adherent a = new Adherent();
                a.setId(rs.getInt("aid"));
                a.setNom(rs.getString("nom"));

                Livre l = new Livre();
                l.setId(rs.getInt("lid"));
                l.setTitre(rs.getString("titre"));

                Emprunt e = new Emprunt();
                e.setId(rs.getInt("id"));
                e.setAdherent(a);
                e.setLivre(l);
                e.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                e.setDateRetour(rs.getDate("date_retour") != null
                        ? rs.getDate("date_retour").toLocalDate()
                        : null);
                e.setStatut(rs.getString("statut"));

                list.add(e);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }
}
