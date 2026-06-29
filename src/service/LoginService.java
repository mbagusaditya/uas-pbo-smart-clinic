package service;

import dao.UserDAO;
import model.User;

public class LoginService {

    private User currentUser;

    private UserDAO dao = new UserDAO();

    public boolean login(String username, String password) {
        this.currentUser = this.dao.getUser(username);

        return this.currentUser.login(username, password);
    }

    public boolean logout() {
        this.currentUser.logout();

        this.currentUser = null;

        return true;
    }

    public String getUsername() {
        return this.currentUser.getUsername();
    }
}
