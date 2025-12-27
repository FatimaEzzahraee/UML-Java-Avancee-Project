package lib.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lib.model.Adherent;
import lib.model.Utilisateur;

public class AdherentDao {

    private Connection conn = DBConnection.getInstance().getConnection();

    // Ajouter un adherent
    public void ajouter(Adherent a) {
        String sql = "INSERT INTO adherent (nom, bloque, utilisateur_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNom());
            ps.setBoolean(2, a.isBloque());
            ps.setInt(3, a.getUtilisateur().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifier un adherent
    public void modifier(Adherent a) {
        String sql = "UPDATE adherent SET nom=?, bloque=?, utilisateur_id=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNom());
            ps.setBoolean(2, a.isBloque());
            ps.setInt(3, a.getUtilisateur().getId());
            ps.setInt(4, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un adherent
    public void supprimer(int id) {
        String sql = "DELETE FROM adherent WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Chercher par id
    public Adherent chercherParId(int id) {
        String sql = "SELECT a.id, a.nom, a.bloque, u.id AS uid, u.username " +
                     "FROM adherent a JOIN utilisateur u ON a.utilisateur_id = u.id " +
                     "WHERE a.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("uid"));
                u.setUsername(rs.getString("username"));

                return new Adherent(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getBoolean("bloque"),
                    u
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lister tous les adherents
    public List<Adherent> lister() {
        List<Adherent> list = new ArrayList<>();
        String sql = "SELECT a.id, a.nom, a.bloque, u.id AS uid, u.username " +
                     "FROM adherent a JOIN utilisateur u ON a.utilisateur_id = u.id";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("uid"));
                u.setUsername(rs.getString("username"));

                list.add(new Adherent(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getBoolean("bloque"),
                    u
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
