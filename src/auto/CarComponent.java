/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;


public class CarComponent extends CarBrand {
    private float price;
    private CarModel model;
    
    public CarComponent(int id, String name, float price, CarModel model) {
        super(id, name);
        this.price = price;
        this.model = model;
    }
    
    public float getPrice() {
        return price;
    }
}
