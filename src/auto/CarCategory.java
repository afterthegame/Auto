/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

/**
 *
 * @author gorz
 */
public class CarCategory {
    private int id;
    private String name;
    private boolean flag;

    public CarCategory(int id, String name, boolean flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFlag() {
        return flag;
    }
    
    
}
