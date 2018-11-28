package pl.coderslab.records;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solutions {
    private Integer id;
    private String description;
    private int exercise_id;
    private long user_id;
    private String created;
    private String updated;

    public void save(Connection connection) throws SQLException {
        if (id == null) {

            String sql = "INSERT INTO solutions (created, updated, description, exercise_id, user_id) VALUES(?, ?, ?, ?,?)";
            String[] generatedColumns = {"id"};

            try (PreparedStatement statement = connection.prepareStatement(sql, generatedColumns)) {
                statement.setString(1, created);
                statement.setString(2, updated);
                statement.setString(3, description);
                statement.setInt(4, exercise_id);
                statement.setLong(5, user_id);
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } else {
            String sql = "UPDATE solutions SET created=?, updated=?, description=?, exercise_id = ?, user_id = ? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, created);
                statement.setString(2, updated);
                statement.setString(3, description);
                statement.setInt(4, exercise_id);
                statement.setLong(5, user_id);
                statement.execute();
            }
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (id != null) {
            String sql = "DELETE FROM solutions WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }
            id = null;
        }
    }

    public static Solutions getSolutionWithParameter(ResultSet rs, int id) throws SQLException {
        Solutions solutions = new Solutions();
        solutions.id = id;
        solutions.created= rs.getString("created");
        solutions.updated = rs.getString("updated");
        solutions.description = rs.getString("description");
        solutions.user_id = rs.getInt("user_id");
        solutions.exercise_id = rs.getInt("exercise_id");
        return solutions;
    }

    public static Solutions findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM solutions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Solutions solutions = getSolutionWithParameter(rs,id);
                return solutions;
            }
        }
        return null;
    }

    public static List<Solutions> findAll(Connection connection) throws SQLException {

        List<Solutions> solutionsList = new ArrayList<>();

        String sql = "SELECT * FROM solutions";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Solutions solutions = new Solutions();
                solutions.id = rs.getInt("id");
                solutions.created= rs.getString("created");
                solutions.updated = rs.getString("updated");
                solutions.description = rs.getString("description");
                solutions.user_id = rs.getInt("user_id");
                solutions.exercise_id = rs.getInt("exercise_id");
                solutionsList.add(solutions);
            }
        }

        return solutionsList;
    }

    public static List<Solutions> loadAllByUserId(Connection connection, int user_id) throws SQLException {
        List<Solutions> allUserSolutions = new ArrayList<>();
        String sql = "SELECT  * FROM solutions WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Solutions solutions = getSolutionWithParameter(rs, user_id);
                allUserSolutions.add(solutions);
            }
            return  allUserSolutions;
        }
    }

    public static List<Solutions> loadAllByExerciseId(Connection connection, int exercise_id) throws SQLException {
        List<Solutions> allUserSolutions = new ArrayList<>();
        String sql = "SELECT  * FROM solutions WHERE exercise_id = ? order by created asc;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, exercise_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Solutions solutions = getSolutionWithParameter(rs,exercise_id);
                allUserSolutions.add(solutions);
            }
            return  allUserSolutions;
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Solutions{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", exercise_id=" + exercise_id +
                ", user_id=" + user_id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solutions solutions = (Solutions) o;
        return Objects.equals(getDescription(), solutions.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }
}
