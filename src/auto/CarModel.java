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
    private float repairCost;
    private float wearFactor;
    private float regionFactor1;
    private float regionFactor2;
    private float regionFactor3;

    public CarModel(int id, String name, CarBrand brand, int category, float repairCost, float wearFactor, float regionFactor1, float regionFactor2, float regionFactor3) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.repairCost = repairCost;
        this.wearFactor = wearFactor;
        this.regionFactor1 = regionFactor1;
        this.regionFactor2 = regionFactor2;
        this.regionFactor3 = regionFactor3;
    }

    public float getWearFactor() {
        return wearFactor;
    }

    public float getRegionFactor1() {
        return regionFactor1;
    }

    public float getRegionFactor2() {
        return regionFactor2;
    }

    public float getRegionFactor3() {
        return regionFactor3;
    }

    
    public CarModel(int id, String name, CarBrand brand, int category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public float getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(float repairCost) {
        this.repairCost = repairCost;
    }
 
    
}