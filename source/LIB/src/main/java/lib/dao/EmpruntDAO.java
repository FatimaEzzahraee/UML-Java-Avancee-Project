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

    // ================= AJOUTER UN EMPRUNT =================
    public void ajouter(Emprunt e) {

        //  Contrainte : livre disponible
        if (!e.getLivre().estDisponible()) {
            throw new RuntimeException(
                "Impossible d'emprunter ce livre : aucun exemplaire disponible"
            );
        }

        //  Décrémenter le stock
        e.getLivre().emprunterExemplaire();
        new LivreDAO().mettreAJour(e.getLivre());

        // Dates et statut
        e.setDateRetour(e.getDateEmprunt().plusDays(14));
        e.setStatut("EN_COURS");
        e.mettreAJourRetard();

        String sql = """
            INSERT INTO emprunt (adherent_id, livre_id, date_emprunt, date_retour, statut)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, e.getAdherent().getId());
            ps.setInt(2, e.getLivre().getId());
            ps.setDate(3, Date.valueOf(e.getDateEmprunt()));
            ps.setDate(4, Date.valueOf(e.getDateRetour()));
            ps.setString(5, e.getStatut());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getInt(1));
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // ================= RETOURNER UN EMPRUNT =================
    public void retourner(int idEmprunt) {
        String sql = """
            UPDATE emprunt
            SET date_retour = ?, statut = 'RETOURNE'
            WHERE id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, idEmprunt);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // ================= LISTER TOUS LES EMPRUNTS =================
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

                Date dr = rs.getDate("date_retour");
                e.setDateRetour(dr != null ? dr.toLocalDate() : null);

                e.setStatut(rs.getString("statut"));
                e.mettreAJourRetard(); // ✅ sans NPE

                list.add(e);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }
    
 // ================= EMPRUNTS D'UN ADHERENT =================
    public List<Emprunt> listerParAdherent(Adherent adherent) {

        List<Emprunt> list = new ArrayList<>();

        String sql = """
            SELECT e.id, e.date_emprunt, e.date_retour, e.statut,
                   l.id lid, l.titre
            FROM emprunt e
            JOIN livre l ON e.livre_id = l.id
            WHERE e.adherent_id = ?
            ORDER BY e.date_emprunt DESC
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adherent.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Livre l = new Livre();
                l.setId(rs.getInt("lid"));
                l.setTitre(rs.getString("titre"));

                Emprunt e = new Emprunt();
                e.setId(rs.getInt("id"));
                e.setAdherent(adherent); // 👈 important
                e.setLivre(l);
                e.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());

                Date dr = rs.getDate("date_retour");
                e.setDateRetour(dr != null ? dr.toLocalDate() : null);

                e.setStatut(rs.getString("statut"));
                e.mettreAJourRetard();

                list.add(e);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }

   


    // ================= CHERCHER PAR ID =================
    public Emprunt chercherParId(int id) {
        for (Emprunt e : lister()) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    // ================= EMPRUNTS ACTIFS D’UN LIVRE =================
    public List<Emprunt> getEmpruntsActifs(Livre livre) {

        List<Emprunt> actifs = new ArrayList<>();

        for (Emprunt e : lister()) {
            if (e.getLivre().getId() == livre.getId()
                && !e.isRetourne()) {
                actifs.add(e);
            }
        }
        return actifs;
    }
}
