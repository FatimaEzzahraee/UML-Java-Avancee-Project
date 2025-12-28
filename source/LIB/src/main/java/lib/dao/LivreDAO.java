package lib.dao;

import lib.model.Livre;
import lib.model.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    private Connection conn = DBConnection.getInstance().getConnection();

    // ===== AJOUTER UN LIVRE AVEC CATEGORIE =====
    public void ajouter(Livre l) {
        String sql = "INSERT INTO livre (titre, auteur, nb_total_exemplaires, nb_exemplaires_disponibles, categorie_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, l.getTitre());
            ps.setString(2, l.getAuteur());
            ps.setInt(3, l.getNbTotalExemplaires());
            ps.setInt(4, l.getNbExemplairesDisponibles());

            
            if (l.getCategorie() != null) {
                ps.setInt(5, l.getCategorie().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ===== LISTER LES LIVRES =====
    public List<Livre> lister() {
        List<Livre> livres = new ArrayList<>();
        String sql = """
            SELECT l.id, l.titre, l.auteur,
                   l.nb_total_exemplaires, l.nb_exemplaires_disponibles,
                   c.id AS categorie_id, c.nom AS categorie_nom
            FROM livre l
            LEFT JOIN categorie c ON l.categorie_id = c.id
        """;

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setNbTotalExemplaires(rs.getInt("nb_total_exemplaires"));
                l.setNbExemplairesDisponibles(rs.getInt("nb_exemplaires_disponibles"));

                int catId = rs.getInt("categorie_id");
                String catNom = rs.getString("categorie_nom");
                if (catNom != null) {
                    l.setCategorie(new Categorie(catId, catNom));
                }

                livres.add(l);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return livres;
    }

    // ===== CHERCHER PAR ID =====
    public Livre chercherParId(int id) {
        String sql = """
            SELECT l.id, l.titre, l.auteur,
                   l.nb_total_exemplaires, l.nb_exemplaires_disponibles,
                   c.id AS categorie_id, c.nom AS categorie_nom
            FROM livre l
            LEFT JOIN categorie c ON l.categorie_id = c.id
            WHERE l.id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setNbTotalExemplaires(rs.getInt("nb_total_exemplaires"));
                l.setNbExemplairesDisponibles(rs.getInt("nb_exemplaires_disponibles"));

                int catId = rs.getInt("categorie_id");
                String catNom = rs.getString("categorie_nom");
                if (catNom != null) {
                    l.setCategorie(new Categorie(catId, catNom));
                }

                return l;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // ===== METTRE À JOUR UN LIVRE =====
    public void mettreAJour(Livre l) {
        String sql = """
            UPDATE livre
            SET nb_total_exemplaires = ?,
                nb_exemplaires_disponibles = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, l.getNbTotalExemplaires());
            ps.setInt(2, l.getNbExemplairesDisponibles());
            ps.setInt(3, l.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ===== SUPPRIMER UN LIVRE UNIQUEMENT SI NON EMPRUNTÉ =====
    public void supprimer(int id) {

        // 1️⃣ Vérifier s’il existe un emprunt EN COURS
        String checkSql = "SELECT COUNT(*) FROM emprunt WHERE livre_id = ? AND statut = 'EN_COURS'";
        try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            psCheck.setInt(1, id);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new RuntimeException(
                    "Impossible de supprimer ce livre : un exemplaire est actuellement emprunté."
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 2️⃣ Supprimer les anciens emprunts (TERMINÉ, RENDU, etc.)
        String deleteEmpruntsSql = "DELETE FROM emprunt WHERE livre_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteEmpruntsSql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 3️⃣ Supprimer le livre
        String deleteLivreSql = "DELETE FROM livre WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteLivreSql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
