package lib.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lib.model.Livre;
import lib.model.Categorie;

public class LivreDAO {

    private Connection conn = DBConnection.getInstance().getConnection();

    // Ajouter un livre
    public void ajouter(Livre livre) {
        String sql = "INSERT INTO livre (titre, auteur, categorie_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setInt(3, livre.getCategorie().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifier un livre
    public void modifier(Livre livre) {
        String sql = "UPDATE livre SET titre=?, auteur=?, categorie_id=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setInt(3, livre.getCategorie().getId());
            ps.setInt(4, livre.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un livre
    public void supprimer(int id) {
        String sql = "DELETE FROM livre WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Chercher un livre par ID
    public Livre chercherParId(int id) {
        String sql = "SELECT l.id, l.titre, l.auteur, c.id AS cid, c.nom AS cname " +
                     "FROM livre l JOIN categorie c ON l.categorie_id = c.id WHERE l.id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categorie c = new Categorie(rs.getInt("cid"), rs.getString("cname"));
                    return new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"), c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lister tous les livres
    public List<Livre> lister() {
        List<Livre> list = new ArrayList<>();
        String sql = "SELECT l.id, l.titre, l.auteur, c.id AS cid, c.nom AS cname " +
                     "FROM livre l JOIN categorie c ON l.categorie_id = c.id";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Categorie c = new Categorie(rs.getInt("cid"), rs.getString("cname"));
                list.add(new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"), c));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
