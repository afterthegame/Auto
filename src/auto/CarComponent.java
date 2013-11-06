/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;


public class CarComponent {
    private int id;
    private String name;
    private float price;
    private CarModel model;
    
    public CarComponent(int id, String name, float price, CarModel model) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.model = model;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public float getPrice() {
        return price;
    }
}
