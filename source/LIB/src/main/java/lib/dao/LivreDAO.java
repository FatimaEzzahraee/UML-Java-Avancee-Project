package lib.dao;

import lib.model.Livre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    private Connection conn = DBConnection.getInstance().getConnection();

    public void ajouter(Livre l) {
        String sql = "INSERT INTO livre (titre, auteur, nb_exemplaires) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getTitre());
            ps.setString(2, l.getAuteur());
            ps.setInt(3, l.getNbExemplaires());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Livre> lister() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setNbExemplaires(rs.getInt("nb_exemplaires"));
                livres.add(l);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livres;
    }

    public Livre chercherParId(int id) {
        String sql = "SELECT * FROM livre WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setNbExemplaires(rs.getInt("nb_exemplaires"));
                return l;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void decrementerStock(int idLivre) {
        String sql = "UPDATE livre SET nb_exemplaires = nb_exemplaires - 1 WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLivre);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementerStock(int idLivre) {
        String sql = "UPDATE livre SET nb_exemplaires = nb_exemplaires + 1 WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLivre);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
