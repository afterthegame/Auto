/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import gui.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author gorz
 */
public class Auto {

    private static GuiController guiController;
    private static UserController userController;
    private static InputDataController inputDataController = null;
    
    private static Connection connection;
    private static Statement statement;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/auto", "root", "root");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.exit(-1);
        }
        
        guiController = new GuiController();
        userController = new UserController(statement);
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
        guiController.changeFrame("menu");
    }
}
