/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gorz
 */
public class UserController {
    
    private Statement statement;
    private User currentUser;
    
    public UserController(Statement statement) {
        this.statement = statement;
        currentUser = null;
    }
    
    public int auth(String login, String password) {   
        try (ResultSet result = statement.executeQuery(
                    "SELECT * FROM users WHERE login=\""+login+"\";")) {
            if(!result.next() || !result.getString("password").equals(password)) {
                return 2;
            }
            currentUser = new User(
                    result.getInt("id"), 
                    result.getString("fio"),
                    login, 
                    result.getInt("type")
                    );
        } catch(SQLException e) {
            return 1;
        }
        return 0;
    }
    
}
