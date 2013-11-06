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
    private int type;
    
    public User(int id, String fio, String login, int type) {
        this.id = id;
        this.fio = fio;
        this.login = login;
        this.type = type;
        System.out.println(fio);
    }
    
}
