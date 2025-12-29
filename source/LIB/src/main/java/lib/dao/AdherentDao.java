package lib.dao;

import lib.model.Adherent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdherentDao {

    private Connection conn = DBConnection.getInstance().getConnection();

    // ===============================
    // AJOUTER UN ADHÉRENT
    // ===============================
    public void ajouter(Adherent a) {
        String sql = "INSERT INTO adherent (nom, email, bloque) VALUES (?, ?, false)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNom());
            ps.setString(2, a.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur ajout adhérent", e);
        }
    }

    // ===============================
    // LISTER TOUS LES ADHÉRENTS
    // ===============================
    public List<Adherent> lister() {
        List<Adherent> adherents = new ArrayList<>();
        String sql = "SELECT * FROM adherent";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Adherent a = new Adherent();
                a.setId(rs.getInt("id"));
                a.setNom(rs.getString("nom"));
                a.setEmail(rs.getString("email"));
                a.setBloque(rs.getBoolean("bloque"));
                adherents.add(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur liste adhérents", e);
        }

        return adherents;
    }

    // ===============================
    // CHERCHER UN ADHÉRENT PAR ID
    // (UTILISÉ PAR EMPRUNT)
    // ===============================
    public Adherent chercherParId(int id) {
        String sql = "SELECT * FROM adherent WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	
                Adherent a = new Adherent();
                a.setId(rs.getInt("id"));
                a.setNom(rs.getString("nom"));
                a.setEmail(rs.getString("email"));
                a.setBloque(rs.getBoolean("bloque"));
                return a;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche adhérent", e);
        }

        return null;
    }

    // ===============================
    // BLOQUER UN ADHÉRENT
    // ===============================
    public void bloquer(int id) {
        String sql = "UPDATE adherent SET bloque=true WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur blocage adhérent", e);
        }
    }

    // ===============================
    // DÉBLOQUER UN ADHÉRENT
    // ===============================
    public void debloquer(int id) {
        String sql = "UPDATE adherent SET bloque=false WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur déblocage adhérent", e);
        }
    }
}
