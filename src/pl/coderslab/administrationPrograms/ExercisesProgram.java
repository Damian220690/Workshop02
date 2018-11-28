package pl.coderslab.administrationPrograms;

import com.mysql.jdbc.Connection;
import pl.coderslab.records.Exercises;
import pl.coderslab.records.User;
import pl.coderslab.records.User_groups;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ExercisesProgram implements ProgramHelper {
    @Override
    public void showAllRecords(Connection connection) {
        Exercises exercises = new Exercises();
        try {
            List<Exercises> exercisesList = exercises.findAll(connection);
            System.out.println("List of Exercises:");
            for (Exercises e : exercisesList) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToDatabase(Scanner scanner, Connection connection) throws SQLException {
            Exercises exercises = new Exercises();
            System.out.println("Please enter the title of Exercise:");
            String title = scanner.next();
            System.out.println("Please enter the description of this Exercise:");
            String description = scanner.next();
            exercises.setTitle(title);
            exercises.setDescription(description);
            exercises.save(connection);
            System.out.println("Operation success!!!");
    }

    @Override
    public void editRecord(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("Please enter the id of the Exercise with should be modified:");
        while(!scanner.hasNextInt()) {
            System.out.println("Please enter numeric value");
            scanner.next();
        }
        int id = scanner.nextInt();
        Exercises choosenExercise = Exercises.findById(connection, id);
        System.out.println("Please enter the title of Exercise:");
        String title = scanner.next();
        System.out.println("Please enter the description of this Exercise:");
        String description = scanner.nextLine();
        choosenExercise.setTitle(title);
        choosenExercise.setDescription(description);
        choosenExercise.save(connection);
        System.out.println("Exercise was modified!!!");
    }

    @Override
    public void deleteFromDatabase(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("Please enter the id of the Exercise which should be deleted:");
        int id = scanner.nextInt();
        Exercises choosenExercise = Exercises.findById(connection, id);
        choosenExercise.delete(connection);
        System.out.println("Exercise was deleted!!!");
    }

}
