package dao;

import database.DBConnection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

public class UserDAO {

    Connection conn = DBConnection.connect();

    // GET ONE USER
    public User getUser(String username) {
        String sqlQuery =
            "SELECT u.id_user, u.username, u.password, r.nama_role AS role FROM users u INNER JOIN roles r ON u.id_role = r.id_role WHERE u.username = ? LIMIT 1";

        try {
            PreparedStatement st = conn.prepareStatement(sqlQuery);

            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
