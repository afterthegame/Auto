/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author gorz
 */
public class InputDataController {
    
    private InputData inputData;
    private UserController user;
    
    private Statement statement;
    private ArrayList<CarBrand> brands;
    private ArrayList<CarBody> bodies;
    private String regionName[] = {"low", "high", "another"};
    private HashMap<CarBrand, ArrayList<CarModel>> models;
    private HashMap<CarModel, ArrayList<CarComponent>> components;
    private HashMap<CarModel, ArrayList<CarComponent>> equipments;
    private ArrayList<CarComponent> materials;
    private ArrayList<UsageConditionFactor> usageConditions;
    private ArrayList<UsageConditionFactor> damages;
    
    //public int year = 0;
    //public float mass = 0;
    //public CarModel modelId = null;
    //public void setModel(int b, int m) {
    //    modelId = models.get(brands.get(b)).get(m);
    //}
    
    public InputDataController(Statement statement, UserController user) throws SQLException {
        this.user = user;
        this.statement = statement;
        brands = new ArrayList<CarBrand>();
        bodies = new ArrayList<CarBody>();
        models = new HashMap<CarBrand, ArrayList<CarModel>>();
        materials = new ArrayList<CarComponent>();
        components = new HashMap<CarModel, ArrayList<CarComponent>>();
        equipments = new HashMap<CarModel, ArrayList<CarComponent>>();
        damages = new ArrayList<UsageConditionFactor>();
        usageConditions = new ArrayList<UsageConditionFactor>();
        try(ResultSet result = statement.executeQuery(
                "SELECT * FROM brands")) {
            while(result.next()) {
                brands.add(new CarBrand(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            throw e;
        }
        try(ResultSet result = statement.executeQuery(
                "SELECT * FROM bodies")) {
            while(result.next()) {
                bodies.add(new CarBody(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            throw e;
        }
        try(ResultSet result = statement.executeQuery(
                "SELECT * FROM usage_conditions")) {
            while(result.next()) {
                usageConditions.add(
                        new UsageConditionFactor(
                            result.getString("name"), 
                            result.getFloat("val")
                        )
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        try(ResultSet result = statement.executeQuery(
                "SELECT * FROM damages")) {
            while(result.next()) {
                damages.add(
                        new UsageConditionFactor(
                            result.getString("name"), 
                            result.getFloat("val")
                        )
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        try(ResultSet result = statement.executeQuery(
                "SELECT * FROM materials;")) {
            while(result.next()) {
                materials.add(
                        new CarComponent(
                            result.getInt("id"),
                            result.getString("name"), 
                            result.getFloat("price"),
                            null
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public ArrayList<String> getBrands() {
        ArrayList<String> names = new ArrayList<String>();
        for(CarBrand b : brands) {
            names.add(b.getName());
        }
        return names;
    }
    
    public ArrayList<String> getMaterials() {
        ArrayList<String> names = new ArrayList<>();
        for(CarComponent m : materials) {
            names.add(m.getName());
        }
        return names;
    }
    
    public ArrayList<String> getBodies() {
        ArrayList<String> names = new ArrayList<>();
        for(CarBody b : bodies) {
            names.add(b.getName());
        }
        return names;
    }
    
    public ArrayList<String> getModels(int index) {
        ArrayList<String> names = new ArrayList<>();
        CarBrand brand = brands.get(index);
        ArrayList<CarModel> m = models.get(brand);
        if(m == null) {
            try(ResultSet result = statement.executeQuery(
                    "SELECT DISTINCT id, name, category_id "
                    + "FROM models WHERE brand_id=\""+brand.getId()+"\";")) {
                m = new ArrayList<CarModel>();
                while(result.next()) {
                    m.add(
                            new CarModel(result.getInt("id"), 
                            result.getString("name"), 
                            brand,
                            result.getInt("category_id")));
                }
                models.put(brand, m);
            } catch (SQLException e) {
                return null;
            }
        }
        for(CarModel model : m) {
            names.add(model.getName());
        }
        return names;
    }
    
    public ArrayList<String> getComponents() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<CarComponent> c = components.get(inputData.model);
        if(c == null) {
            String query = "SELECT DISTINCT id, name, price FROM components "
                    + "WHERE model_id=\""+inputData.model.getId()+"\";";
            try(ResultSet result = statement.executeQuery(query)) {
                c = new ArrayList<CarComponent>();
                while(result.next()) {
                    c.add(new CarComponent(
                            result.getInt("id"), 
                            result.getString("name"),
                            result.getFloat("price"),
                            inputData.model));
                }
                components.put(inputData.model, c);
            } catch (SQLException e) {
                return null;
            }
        }
        for(CarComponent component : c) {
            names.add(component.getName());
        }
        return names;
    }
    
    public ArrayList<String> getEquipments() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<CarComponent> c = equipments.get(inputData.model);
        if(c == null) {
            String query = "SELECT DISTINCT id, name, price FROM equipments "
                    + "WHERE model_id=\""+inputData.model.getId()+"\";";
            try(ResultSet result = statement.executeQuery(query)) {
                c = new ArrayList<CarComponent>();
                while(result.next()) {
                    c.add(new CarComponent(
                            result.getInt("id"), 
                            result.getString("name"),
                            result.getFloat("price"),
                            inputData.model));
                }
                equipments.put(inputData.model, c);
            } catch (SQLException e) {
                return null;
            }
        }
        for(CarComponent component : c) {
            names.add(component.getName());
        }
        return names;
    }    
    
    public ArrayList<String> getUsageConditions() {
        ArrayList<String> names = new ArrayList<String>();
        for(UsageConditionFactor u : usageConditions) {
            names.add(u.getName());
        }
        return names;
    }
    
    public ArrayList<String> getDamages() {
        ArrayList<String> names = new ArrayList<String>();
        for(UsageConditionFactor u : damages) {
            names.add(u.getName());
        }
        return names;
    }
    
    public void newData() {
        inputData = new InputData(statement);
        inputData.expert = user.getCurrentUser().getFio();
    }
    
    public InputData getData() {
        return inputData;
    }
    
    public void setDriverInfo(String fio, String dob, String idNumber) {
        inputData.FIO = fio;
        inputData.dateOfBirth = dob;
        inputData.idNumber = idNumber;
    }
    
    public void setCarInfo(int brand, int model, int body, int year,
                           float volume, int region, float tax, float mass, float mileage) {
        inputData.brand = brands.get(brand);
        inputData.model = models.get(brands.get(brand)).get(model);
        inputData.body = bodies.get(body);
        inputData.year = year;
        inputData.region = region;
        inputData.engineVolume = volume;
        inputData.tax = tax;
        inputData.mileage = mileage;
        inputData.mass = mass;
    }
    
    public void setUsageConditions(ArrayList<Boolean> usageConsitionSelections, 
            ArrayList<Boolean> damageSelections) {
        inputData.usageConditions = new ArrayList<UsageConditionFactor>();
        inputData.bodyDamages = new ArrayList<UsageConditionFactor>();
        for(int i=0; i<usageConsitionSelections.size(); i++) {
            if(usageConsitionSelections.get(i)) {
                inputData.usageConditions.add(usageConditions.get(i));
            }
        }
        for(int i=0; i<damageSelections.size(); i++) {
            if(damageSelections.get(i)) {
                inputData.bodyDamages.add(damages.get(i));
            }
        }
    }
    
    public void setComponentsAndEquipments(
            ArrayList<Pair<Integer, Integer>> selectedComponents,
            ArrayList<Pair<Integer, Integer>> selectedEquipments) {
        
        ArrayList<CarComponent> c = components.get(inputData.model);
        inputData.newComponents = new ArrayList<Pair<CarComponent, Integer>>();
        for(Pair<Integer, Integer> p : selectedComponents) {
            Pair<CarComponent, Integer> np = new Pair(c.get(p.first), p.second);
            inputData.newComponents.add(np);
        }
        ArrayList<CarComponent> e = equipments.get(inputData.model);
        inputData.newEquipments = new ArrayList<Pair<CarComponent, Integer>>();
        for(Pair<Integer, Integer> p : selectedEquipments) {
            Pair<CarComponent, Integer> np = new Pair(e.get(p.first), p.second);
            inputData.newEquipments.add(np);
        }
    }
    
    public void setRepairComponents(float repairComplexity,
            ArrayList<Float> materialsCounts, ArrayList<Integer> componentsCounts) {
        inputData.repairComplexity = repairComplexity;
        inputData.materials = new ArrayList<Pair<CarComponent, Float>>();
        for(int i = 0; i<materialsCounts.size(); i++) {
            if(materialsCounts.get(i)!=null) {
                Pair<CarComponent, Float> p = new Pair(
                        materials.get(i),
                        materialsCounts.get(i));
                inputData.materials.add(p);
            }
        }
        ArrayList<CarComponent> c = components.get(inputData.model);
        inputData.repairComponents = new ArrayList<Pair<CarComponent, Integer>>();
        for(int i = 0; i<componentsCounts.size(); i++) {
            if(componentsCounts.get(i)!=null) {
                Pair<CarComponent, Integer> p = new Pair(
                        c.get(i),
                        componentsCounts.get(i));
                inputData.repairComponents.add(p);
            }
        }
    }
    
    public void setLiquidityFactor(float liquidity, boolean renewable) {
        inputData.renewable = renewable;
        inputData.liquidity_factor = liquidity;
    }
    
    public float getCost() throws Exception {
        return inputData.getCost();
    }
    
    public float getLiquidityPrice() throws Exception {
        return inputData.getLiquidityPrice();
    }
    
    public float getDamage() {
        return inputData.getDamage();
    }
    
    
    
}
