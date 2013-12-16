/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

/**
 *
 * @author afterthegame
 */
public class CarEdition {

    private int id;
    private CarModel model;
    private CarBrand brand;
    private int year;
    private float volume;
    private float price;

    public CarEdition(int id, CarModel model, CarBrand brand, int year, float volume, float price) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.volume = volume;
        this.price = price;
    }



    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public CarModel getModel() {
        return model;
    }

    public CarBrand getBrand() {
        return brand;
    }
    
    

}
