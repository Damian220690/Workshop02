package pl.coderslab.userPanel;

import com.mysql.jdbc.Connection;
import pl.coderslab.administrationPrograms.ExercisesProgram;
import pl.coderslab.administrationPrograms.UsersProgram;
import pl.coderslab.records.Exercises;
import pl.coderslab.records.Solutions;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserPanel {
    public void chooseOptionFromMenu(Scanner scanner, Connection connection, String[] args) throws SQLException {
        String input;
        do {
            System.out.println("Please choose the appropriate option:");
            System.out.println("add - if you add your solution");
            System.out.println("view - if you want see your  solutions");
            System.out.println("quit - if you want exit program");
            input = scanner.next();
            switch(input){
                case "add":
                    addYourSolution(scanner,connection, args);
                    break;
                case "view":
                    viewYourSolution(connection, args);
                    break;
                case "quit":
                    System.exit(0);
            }
        }
        while(!input.equals("quit"));
    }


    private void addYourSolution(Scanner scanner, Connection connection, String[] args) throws SQLException {
        List<Exercises> list = Exercises.loadAllExerciseWithoutSolutions(connection);
        for(Exercises e : list){
            System.out.println(e);
        }
        System.out.println(" ");

        System.out.println("Please enter exercise id: ");
        while(!scanner.hasNextInt()){
            System.out.println("Please enter numeric value");
            scanner.next();
        }
        int exerciseId = scanner.nextInt();
        System.out.println("Please enter description to the solutions: ");
        String description = scanner.next();

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String created = String.valueOf(sqlDate);

        List<Solutions> listOfSolutions = Solutions.loadAllByUserId(connection,Integer.parseInt(args[0]));

        Solutions solutions = new Solutions();
        solutions.setExercise_id(exerciseId);
        solutions.setUser_id(Integer.parseInt(args[0]));
        solutions.setDescription(description);
        solutions.setUpdated(created);
        if (listOfSolutions.contains(solutions)) {
            System.out.println("This.solution already exists!!! Please enter another");
        }
        else {
            solutions.save(connection);
            System.out.println("Operation success!!!");

        }
    }

    private void viewYourSolution(Connection connection, String[] args) throws SQLException {
        int userId = Integer.parseInt(args[0]);
        List<Solutions> listOfSolutions = Solutions.loadAllByUserId(connection,userId);
        for(Solutions solutions : listOfSolutions){
            System.out.println(solutions);
        }
    }


}
