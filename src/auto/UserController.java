/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
                    result.getBoolean("type")
                    );
        } catch(SQLException e) {
            return 1;
        }
        return 0;
    }
    
    public int registrate(String login, String password, String fio) {
        try (ResultSet result = statement.executeQuery(
                    "SELECT * FROM users WHERE login=\""+login+"\";")) {
            if(!result.next()) {
                return 1;
            } else if(result.getBoolean("reg")) {
                return 2;
            }
            statement.executeUpdate("UPDATE users SET fio=\""+fio+"\", "
                                    + "password=\""+password+"\", reg=TRUE "
                                    + "WHERE login=\""+login+"\";");
        
        } catch(SQLException e) {
            e.printStackTrace();
            return 3;
        }
        return 0;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try (ResultSet result = statement.executeQuery(
                    "SELECT * FROM users;")) {
            while(result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("fio"),
                        result.getString("login"),
                        result.getBoolean("type")
                ));
            }
        } catch(SQLException e) {
            return null;
        }
        return users;
    }
    
    public int addUser(String login) {
        try (ResultSet result = statement.executeQuery(
                    "SELECT * FROM users WHERE login = \""+login+"\";")) {
            if(result.next()) {
                return 1;
            }
        } catch(SQLException e) {
            return 2;
        }
        
        try {
           statement.executeUpdate("INSERT INTO users(login) VALUES(\""+login+"\");");
        } catch(SQLException e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }
    
    public int deleteUser(User user) {
        if(user.isAdmin()) {
            return 1;
        }
        try {
            statement.executeUpdate("DELETE FROM users WHERE id = "+user.getId()+";");
        } catch(SQLException e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }
    
    public boolean isCurrentUserAdmin() {
        return currentUser.isAdmin();
    }
    
}
