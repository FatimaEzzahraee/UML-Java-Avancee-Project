package lib.dao;

import java.sql.*;
import java.time.LocalDateTime;

public class LogDAO {

    private Connection conn = DBConnection.getInstance().getConnection();

    public void ajouterLog(String action, String details) {
        String sql = "INSERT INTO log (action, details, date_action) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, action);
            ps.setString(2, details);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
