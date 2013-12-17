/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

/**
 *
 * @author gorz
 */
public class User {
    
    private int id;
    private String fio, login;
    private boolean admin;

    public int getId() {
        return id;
    }
    
    public String getFio() {
        return fio;
    }

    public String getLogin() {
        return login;
    }
    
    public User(int id, String fio, String login, boolean admin) {
        this.id = id;
        this.fio = fio;
        this.login = login;
        this.admin = admin;
    }
    
    public boolean isAdmin() {
        return admin;
    }
    
}
