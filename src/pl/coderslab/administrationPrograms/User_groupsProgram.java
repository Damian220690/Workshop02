package pl.coderslab.administrationPrograms;

import com.mysql.jdbc.Connection;
import pl.coderslab.records.Exercises;
import pl.coderslab.records.User_groups;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class User_groupsProgram implements ProgramHelper {
    @Override
    public void showAllRecords(Connection connection) {
        User_groups user_groups = new User_groups();
        try {
            List<User_groups> user_groupList = user_groups.findAll(connection);
            System.out.println("List of user groups :");
            for (User_groups ug : user_groupList) {
                System.out.println(ug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToDatabase(Scanner scanner, Connection connection) throws SQLException {
      User_groups user_groups = new User_groups();
        System.out.println("Please enter the name of user group:");
        String name = scanner.next();
        user_groups.setName(name);
        user_groups.save(connection);
        System.out.println("Operation success!!!");
    }

    @Override
    public void editRecord(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("Please enter the id of the user group with should be modified:");
        while(!scanner.hasNextInt()) {
            System.out.println("Please enter numeric value");
            scanner.next();
        }
        int id = scanner.nextInt();
        User_groups choosenUserGroups = User_groups.findById(connection, id);
        System.out.println("Please enter the name of user group:");
        String name = scanner.next();
        choosenUserGroups.setName(name);
        choosenUserGroups.save(connection);
        System.out.println("User group was modified!!!");
    }

    @Override
    public void deleteFromDatabase(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("Please enter the id of the User group which should be deleted:");
        int id = scanner.nextInt();
        User_groups choosenUserGroups = User_groups.findById(connection, id);
        choosenUserGroups.delete(connection);
        System.out.println("User group was deleted!!!");
    }

}
