package pl.coderslab;

import pl.coderslab.administrationPrograms.ExercisesProgram;
import pl.coderslab.administrationPrograms.SolutionOfTheExercisesProgram;
import pl.coderslab.administrationPrograms.User_groupsProgram;
import pl.coderslab.administrationPrograms.UsersProgram;
import pl.coderslab.records.Exercises;
import pl.coderslab.records.Solutions;
import pl.coderslab.records.User;
import pl.coderslab.records.User_groups;


import com.mysql.jdbc.Connection;
import pl.coderslab.userPanel.UserPanel;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = ConnectionProvider.getConnection()) {
            Scanner scanner = new Scanner(System.in);
//            ExercisesProgram exercisesProgram = new ExercisesProgram();
//            exercisesProgram.chooseOption(scanner,connection);

//            User_groupsProgram user_groupsProgram = new User_groupsProgram();
//            user_groupsProgram.chooseOption(scanner,connection);

//            UsersProgram usersProgram = new UsersProgram();
//            usersProgram.chooseOption(scanner,connection);

//            SolutionOfTheExercisesProgram solutionOfTheExercisesProgram = new SolutionOfTheExercisesProgram();
//            solutionOfTheExercisesProgram.chooseOptionFromMenu(scanner,connection);

            UserPanel userPanel = new UserPanel();
            userPanel.chooseOptionFromMenu(scanner,connection,args);

        }
    }
}
