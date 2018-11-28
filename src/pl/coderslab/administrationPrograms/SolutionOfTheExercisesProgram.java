package pl.coderslab.administrationPrograms;

import com.mysql.jdbc.Connection;
import pl.coderslab.records.Solutions;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SolutionOfTheExercisesProgram {

    public void chooseOptionFromMenu(Scanner scanner, Connection connection) throws SQLException {
        String input;
        do {
            System.out.println("Please choose the appropriate option:");
            System.out.println("add - if you want assign exercises to the user");
            System.out.println("view - if you want see the solutions of a given user");
            System.out.println("quit - if you want exit program");
            input = scanner.next();
            switch(input){
                case "add":
                    addSolution(scanner,connection);
                    break;
                case "view":
                    viewUSerSolution(scanner,connection);
                    break;
                case "quit":
                    System.exit(0);
            }
        }
        while(!input.equals("quit"));
    }

    private void addSolution(Scanner scanner, Connection connection) throws SQLException {
        UsersProgram up = new UsersProgram();
        up.showAllRecords(connection);
        System.out.println(" ");
        System.out.println("Please enter user id: ");
        while(!scanner.hasNextInt()){
            System.out.println("Please enter numeric value");
            scanner.next();
        }
        int userId = scanner.nextInt();

        ExercisesProgram ep = new ExercisesProgram();
        ep.showAllRecords(connection);
        System.out.println("");
        System.out.println("Please enter exercise id: ");
        while(!scanner.hasNextInt()){
            System.out.println("Please enter numeric value");
            scanner.next();
        }
        int exerciseId = scanner.nextInt();

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String created = String.valueOf(sqlDate);

        Solutions solutions = new Solutions();
        solutions.setExercise_id(exerciseId);
        solutions.setUser_id(userId);
        solutions.setCreated(created);
            solutions.save(connection);
        System.out.println("Operation success!!!");
    }

    private void viewUSerSolution(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("Please enter user id: ");
        while(!scanner.hasNextInt()){
            System.out.println("Please enter numeric value");
            scanner.next();
        }
        int userId = scanner.nextInt();
        List<Solutions> listOfSolutions = Solutions.loadAllByUserId(connection,userId);
        for(Solutions solutions : listOfSolutions){
            System.out.println(solutions);
        }
    }

}
