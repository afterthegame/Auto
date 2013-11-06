/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

/**
 *
 * @author gorz
 */
public class CarModel {
    
    private int id;
    private String name;
    private CarBrand brand;
    private int category;
    
    public CarModel(int id, String name, CarBrand brand, int category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
