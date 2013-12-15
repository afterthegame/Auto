/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

/**
 *
 * @author gorz
 */
public class CarModel extends CarBrand{
    
    private CarBrand brand;
    private int category;
    
    public CarModel(int id, String name, CarBrand brand, int category) {
        super(id, name);
        this.brand = brand;
        this.category = category;
    }
    
    public int getCategory() {
        return category;
    }
}
