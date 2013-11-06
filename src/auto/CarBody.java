/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

/**
 *
 * @author gorz
 */
public class CarBody {
    
    private int id;
    private String name;
    
    public CarBody(int id, String name) {
        this.name = name;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
