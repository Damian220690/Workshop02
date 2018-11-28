package pl.coderslab.records;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Exercises {
    private Integer id;
    private String title;
    private String description;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void save(Connection connection) throws SQLException {
        if (id == null) {
            String addingQuery = "INSERT INTO exercises(title, description) VALUES (?,?)";
            String[] generatedColumns = {"id"};
            try (PreparedStatement preparedStatement = connection.prepareStatement(addingQuery, generatedColumns)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } else {
            String updatingQuery = "UPDATE exercises SET title = ?,description = ? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updatingQuery)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, id);
                preparedStatement.execute();
            }
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (id != null) {
            String sql = "DELETE FROM exercises WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }
            id = null;
        }
    }
        public static Exercises findById(Connection connection, int id) throws SQLException {
            String sql = "SELECT * FROM exercises WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                   Exercises exercises = new Exercises();
                    exercises.id = id;
                    exercises.title = rs.getString("title");
                    exercises.description = rs.getString("description");
                    return exercises;
                }
            }
            return null;
        }

    public static List<Exercises> findAll(Connection connection) throws SQLException {

        List<Exercises> exercisesList = new ArrayList<>();

        String sql = "SELECT * FROM exercises";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Exercises exercises = new Exercises();
                exercises.id = rs.getInt("id");
                exercises.title = rs.getString("title");
                exercises.description = rs.getString("description");
                exercisesList.add(exercises);
            }
        }

        return exercisesList;
    }

    public static List<Exercises> loadAllExerciseWithoutSolutions (Connection connection) throws SQLException {
        List<Exercises> exercisesWithoutSolutions = new ArrayList<>();
        String sql = "SELECT exercises.id, exercises.title, exercises.description FROM solutions inner join users on users.id = user_id right outer join exercises on exercises.id = exercise_id where solutions.id is null;";
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Exercises exercises = new Exercises();
                exercises.id = rs.getInt("id");
                exercises.title = rs.getString("title");
                exercises.description = rs.getString("description");
                exercisesWithoutSolutions.add(exercises);
            }
            return exercisesWithoutSolutions;
        }
    }

    @Override
    public String toString() {
        return "Exercises{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
