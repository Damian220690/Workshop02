package pl.coderslab.administrationPrograms;

import pl.coderslab.ConnectionProvider;
import pl.coderslab.records.Exercises;
import pl.coderslab.records.User;
import pl.coderslab.records.User_groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UsersProgram implements ProgramHelper{


    @Override
    public void showAllRecords(com.mysql.jdbc.Connection connection) {
        User user = new User();
        try {
            List<User> userList = user.findAll(connection);
            System.out.println("List of Users:");
            for (User u : userList) {
                System.out.println(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToDatabase(Scanner scanner, com.mysql.jdbc.Connection connection) throws SQLException {
        User user = new User();
        System.out.println("Please enter the name of user:");
        String userName = scanner.next();
        System.out.println("Please enter the user password:");
        String password = scanner.next();
        System.out.println("Please enter the user email:");
        String email = scanner.next();
        System.out.println("Please enter the user group id:");
        int groupId = scanner.nextInt();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserGroupId(groupId);
        user.save(connection);
        System.out.println("Operation success!!!");
    }

    @Override
    public void editRecord(Scanner scanner, com.mysql.jdbc.Connection connection) throws SQLException {
        System.out.println("Please enter the id of the User with should be modified:");
        while(!scanner.hasNextInt()) {
            System.out.println("Please enter numeric value");
            scanner.next();
        }
        int id = scanner.nextInt();
        User choosenUser = User.findById(connection, id);
        System.out.println("Please enter the name of user:");
        String userName = scanner.next();
        System.out.println("Please enter the user password:");
        String password = scanner.next();
        System.out.println("Please enter the user email:");
        String email = scanner.next();
        System.out.println("Please enter the user group id:");
        int groupId = scanner.nextInt();
        choosenUser.setUsername(userName);
        choosenUser.setPassword(password);
        choosenUser.setEmail(email);
        choosenUser.setUserGroupId(groupId);
        choosenUser.save(connection);
        System.out.println("User was modified!!!");
    }

    @Override
    public void deleteFromDatabase(Scanner scanner, com.mysql.jdbc.Connection connection) throws SQLException {
        System.out.println("Please enter the id of the User which should be deleted:");
        int id = scanner.nextInt();
        User choosenUser = User.findById(connection, id);
        choosenUser.delete(connection);
        System.out.println("User was deleted!!!");
    }
}
