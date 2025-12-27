package lib.dao;

import java.sql.Connection;
import java.sql.SQLException;
import util.Database;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    // Constructeur privé pour le singleton
    private DBConnection() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
        }
    }

    // Méthode pour récupérer l’instance unique
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Méthode pour récupérer la connexion
    public Connection getConnection() {
        return connection;
    }
}
