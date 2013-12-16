/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parcer;
import auto.CarEdition;
import auto.*;

/**
 *
 * @author afterthegame
 */
public class BrandModelEditionBody {
    private CarBrand brand;
    private CarModel model;
    private CarEdition edition;
    private CarBody body;

    public CarBody getBody() {
        return body;
    }

    public void setBody(CarBody body) {
        this.body = body;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public CarEdition getEdition() {
        return edition;
    }

    public void setEdition(CarEdition edition) {
        this.edition = edition;
    }
    
    
    
}
