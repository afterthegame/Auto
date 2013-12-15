/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import gui.*;
import java.io.File;
import java.io.FileNotFoundException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author gorz
 */
public class Auto {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GuiController guiController;
        UserController userController;
        InputDataController inputDataController = null;
    
        Connection connection;
        Statement statement = null;
        
        String dbName = null;
        String login = null;
        String password = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            dbName = scanner.nextLine();
            login = scanner.nextLine();
            password = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/"+dbName, 
                    login, 
                    password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();  
            System.exit(-1);
        }
        
        userController = new UserController(statement);
        guiController = new GuiController(userController);
        try {
            inputDataController = new InputDataController(statement);
        } catch(SQLException e) {
            System.exit(-1);
        }
        guiController.addFrame("auth", new Auth(guiController, userController));
        guiController.addFrame("menu", new Menu(guiController));
        guiController.addFrame("driverInfo", new DriverInfo(guiController, inputDataController));
        guiController.addFrame("carInfo", new CarInfo(guiController, inputDataController));
        guiController.addFrame("usageConditions", new UsageConditions(guiController, inputDataController));       
        guiController.addFrame("components", new Components(guiController, inputDataController));       
        guiController.addFrame("repair", new Repair(guiController, inputDataController));       
        guiController.addFrame("users", new Users(guiController, userController));
        guiController.addFrame("registration", new Registration(guiController, userController));
        guiController.addFrame("preview", new Preview(guiController, inputDataController));
        guiController.changeFrame("auth");
    }
}
