package pl.coderslab.administrationPrograms;

import com.mysql.jdbc.Connection;
import pl.coderslab.records.Exercises;

import java.sql.SQLException;
import java.util.Scanner;

public interface ProgramHelper {
    public void showAllRecords(Connection connection);
    default public void chooseOption(Scanner scanner, Connection connection) throws SQLException{
        String input;
        do {
            System.out.println("");
            showAllRecords(connection);
            System.out.println("");
            System.out.println("Please choose the appropriate option: ");
            System.out.println("add - if you want enter new Object into your table");
            System.out.println("edit - if you want edit specific Objet from table");
            System.out.println("delete - if you want delete specific Object from table");
            System.out.println("quit - if you want exit program");

            input = scanner.next();
            switch (input) {
                case "add":
                    addToDatabase(scanner, connection);
                    break;
                case "edit":
                    editRecord(scanner,connection);
                    break;
                case "delete":
                    deleteFromDatabase(scanner,connection);
                    break;
                case "quit":
                    quit();
            }
        }
        while(!input.equals("quit"));
    }
    public void addToDatabase(Scanner scanner,Connection connection) throws SQLException;
    public void editRecord(Scanner scanner, Connection connection) throws SQLException;
    public void deleteFromDatabase(Scanner scanner, Connection connection) throws SQLException;

    default public void quit() {
        System.exit(0);
    }
}
