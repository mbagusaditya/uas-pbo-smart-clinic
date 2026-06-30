package service;

import dao.UserDAO;
import javafx.collections.ObservableList;
import model.User;

public class UserService {

    // =========================
    // DAO
    // =========================

    private UserDAO dao = new UserDAO();

    // =========================
    // GET ALL
    // =========================

    public ObservableList<User> getAll() {
        return dao.getAllUser();
    }

    // =========================
    // SEARCH
    // =========================

    public ObservableList<User> search(String keyword) {
        return dao.searchUser(keyword);
    }

    // =========================
    // DELETE
    // =========================

    public void delete(int id) throws Exception {

        if (id <= 0) {
            throw new Exception("ID User tidak valid");
        }

        dao.deleteUser(id);
    }

    // =========================
    // SIMPAN
    // =========================

    public void simpan(User user, boolean editMode)
            throws Exception {

        // =====================
        // VALIDASI
        // =====================

        if (user.getUsername() == null
                || user.getUsername().trim().isEmpty()) {

            throw new Exception("Username wajib diisi");
        }

        if (user.getPassword() == null
                || user.getPassword().trim().isEmpty()) {

            throw new Exception("Password wajib diisi");
        }

        if (user.getRole() == null) {
            throw new Exception("Role wajib dipilih");
        }

        // =====================
        // INSERT / UPDATE
        // =====================

        if (editMode) {

            dao.updateUser(user);

        } else {

            dao.insertUser(user);

        }

    }

}