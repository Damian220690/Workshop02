package pl.coderslab.records;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {

    private Integer id;
    private String email;
    private String username;
    private String password;
    private int userGroupId;

    public static User findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.id = id;
                user.email = rs.getString("email");
                user.username = rs.getString("username");
                user.password = rs.getString("password");
                user.userGroupId = rs.getInt("user_group_id");
                return user;
            }
        }
        return null;
    }

    public static List<User> findAll(Connection connection) throws SQLException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");;
                user.email = rs.getString("email");
                user.username = rs.getString("username");
                user.password = rs.getString("password");
                user.userGroupId = rs.getInt("user_group_id");
                users.add(user);
            }
        }

        return users;
    }

    public static List<User> loadAllByGroupId(Connection connection, int userGroupId) throws SQLException {
        List<User> usersInSpecificGroup = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE user_group_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userGroupId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");;
                user.email = rs.getString("email");
                user.username = rs.getString("username");
                user.password = rs.getString("password");
                user.userGroupId = rs.getInt("user_group_id");
                usersInSpecificGroup.add(user);
            }
        }
        return usersInSpecificGroup;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", userGroupId=" + userGroupId +
                '}';
    }

    public void save(Connection connection) throws SQLException {
        if (id == null) {

            String sql = "INSERT INTO users(email, username, password, user_group_id) VALUES(?, ?, ?, ?)";
            String[] generatedColumns = {"id"};

            try (PreparedStatement statement = connection.prepareStatement(sql, generatedColumns)) {
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setInt(4, userGroupId);
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } else {
            String sql = "UPDATE users SET email=?, username=?, password=?, user_group_id=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setInt(4, userGroupId);
                statement.setInt(5, id);
                statement.execute();
            }
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (id != null) {

            String sql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }

            id = null;
        }
    }
}
