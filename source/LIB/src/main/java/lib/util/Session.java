package lib.util;

import lib.model.Utilisateur;

public class Session {

    private static Utilisateur currentUser;

    private Session() {}

    public static void setCurrentUser(Utilisateur user) {
        currentUser = user;
    }

    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdmin() {
        return currentUser != null &&
               "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }
}
