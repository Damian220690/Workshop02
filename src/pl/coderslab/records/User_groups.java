package pl.coderslab.records;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User_groups {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User_groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void save(Connection connection) throws SQLException {
        if (id == null) {

            String sql = "INSERT INTO user_groups (name) VALUES(?)";
            String[] generatedColumns = {"id"};

            try (PreparedStatement statement = connection.prepareStatement(sql, generatedColumns)) {
                statement.setString(1, name);
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } else {
            String sql = "UPDATE user_groups SET name=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setInt(2, id);
                statement.execute();
            }
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (id != null) {

            String sql = "DELETE FROM user_groups WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }

            id = null;
        }
    }

    public static User_groups findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM user_groups WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User_groups user_groups= new User_groups();
                user_groups.id = id;
                user_groups.name = rs.getString("name");
                return user_groups;
            }
        }
        return null;
    }

    public static List<User_groups> findAll(Connection connection) throws SQLException {

        List<User_groups> user_groupsList = new ArrayList<>();

        String sql = "SELECT * FROM user_groups";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User_groups user_groups = new User_groups();
                user_groups.id = rs.getInt("id");;
                user_groups.name = rs.getString("name");
                user_groupsList.add(user_groups);
            }
        }

        return user_groupsList;
    }
}
