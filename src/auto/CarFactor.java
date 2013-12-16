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
public class CarFactor {

    private int id;
    private int year;
    private float factor;

    public CarFactor(int id, int year, float factor) {
        this.id = id;
        this.year = year;
        this.factor = factor;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public float getFactor() {
        return factor;
    }

}
