package dao;

import database.DBConnection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import model.Role;

public class UserDAO {

    Connection conn = DBConnection.connect();

    // =========================
    // GET ALL
    // =========================

    public ObservableList<User> getAllUser() {

        ObservableList<User> list = FXCollections.observableArrayList();

        try {

            String sql = """
                    SELECT
                        u.*,
                        r.id_role,
                        r.nama_role
                    FROM users u
                    JOIN roles r
                    ON u.id_role = r.id_role
                    """;

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Role role = new Role();

                role.setIdRole(
                        rs.getInt("id_role"));

                role.setNamaRole(
                        rs.getString("nama_role"));

                User user = new User();

                user.setIdUser(
                        rs.getInt("id_user"));

                user.setNama(
                        rs.getString("nama"));

                user.setUsername(
                        rs.getString("username"));

                user.setPassword(
                        rs.getString("password"));

                user.setRole(role);

                list.add(user);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

    // =========================
    // INSERT
    // =========================

    public void insertUser(User user) {

        try {

            String sql = """
                    INSERT INTO users
                    (nama,username,password,id_role,id_dokter)
                    VALUES(?,?,?,?,?)
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getNama());

            ps.setString(2, user.getUsername());

            ps.setString(3, user.getPassword());

            ps.setInt(4,
                    user.getRole().getIdRole());

            if (user.getDokter() == null) {

                ps.setNull(5, Types.INTEGER);

            } else {

                ps.setInt(
                        5,
                        user.getDokter().getIdDokter());

            }

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // =========================
    // UPDATE
    // =========================

    public void updateUser(User user) {

        try {

            String sql = """
                    UPDATE users
                    SET
                        nama=?,
                        username=?,
                        password=?,
                        id_role=?,
                        id_dokter=?
                    WHERE id_user=?
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getNama());

            ps.setString(2, user.getUsername());

            ps.setString(3, user.getPassword());

            ps.setInt(
                    4,
                    user.getRole().getIdRole());

            if (user.getDokter() == null) {

                ps.setNull(5, Types.INTEGER);

            } else {

                ps.setInt(
                        5,
                        user.getDokter().getIdDokter());

            }

            ps.setInt(
                    6,
                    user.getIdUser());

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // =========================
    // DELETE
    // =========================

    public void deleteUser(int id) {

        try {

            String sql = "DELETE FROM users WHERE id_user=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // =========================
    // SEARCH
    // =========================

    public ObservableList<User> searchUser(String keyword) {

        ObservableList<User> list = FXCollections.observableArrayList();

        try {

            String sql = """
                    SELECT
                        u.*,
                        r.id_role,
                        r.nama_role
                    FROM users u
                    JOIN roles r
                    ON u.id_role=r.id_role
                    WHERE
                        u.nama LIKE ?
                        OR u.username LIKE ?
                        OR r.nama_role LIKE ?
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Role role = new Role(
                        rs.getInt("id_role"),
                        rs.getString("nama_role"));

                User user = new User();

                user.setIdUser(rs.getInt("id_user"));
                user.setNama(rs.getString("nama"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(role);

                list.add(user);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;
    }

    // =========================
    // GET USER LOGIN
    // =========================
    // NOTE MERGE
    // Method getUser() versi sebelumnya dihapus karena:
    // 1. Menggunakan kolom user_id (seharusnya id_user).
    // 2. Konstruktor User sudah berubah dan sekarang menggunakan objek Role.
    // 3. Digabung dengan CRUD User agar tidak terjadi duplicate method.

public User getUser(String username) {

    String sql = """
            SELECT
                u.id_user,
                u.nama,
                u.username,
                u.password,
                r.id_role,
                r.nama_role
            FROM users u
            JOIN roles r
            ON u.id_role = r.id_role
            WHERE u.username = ?
            LIMIT 1
            """;

    try {

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            Role role = new Role();

            role.setIdRole(
                    rs.getInt("id_role"));

            role.setNamaRole(
                    rs.getString("nama_role"));

            User user = new User();

            user.setIdUser(
                    rs.getInt("id_user"));

            user.setNama(
                    rs.getString("nama"));

            user.setUsername(
                    rs.getString("username"));

            user.setPassword(
                    rs.getString("password"));

            user.setRole(role);

            return user;
        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return null;
}
