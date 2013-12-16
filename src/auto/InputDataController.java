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
    
    public int year = 0;
    public float mass = 0;
    public CarModel modelId = null;
    public void setModel(int b, int m) {
        modelId = models.get(brands.get(b)).get(m);
    }
    
    public InputDataController(Statement statement) throws SQLException {
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
        ArrayList<CarComponent> c = equipments.get(modelId);
        if(c == null) {
            String query = "SELECT DISTINCT id, name, price FROM equipments "
                    + "WHERE model_id=\""+modelId.getId()+"\";";
            try(ResultSet result = statement.executeQuery(query)) {
                c = new ArrayList<CarComponent>();
                while(result.next()) {
                    c.add(new CarComponent(
                            result.getInt("id"), 
                            result.getString("name"),
                            result.getFloat("price"),
                            modelId));
                }
                equipments.put(modelId, c);
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
    }
    
    public void setDriverInfo(String fio, String dob, String idNumber) {
        inputData.FIO = fio;
        inputData.dateOfBirth = dob;
        inputData.idNumber = idNumber;
    }
    
    public void setCarInfo(int brand, int model, int body, int year,
                           float volume, int region, float tax) {
        inputData.brand = brands.get(brand);
        inputData.model = models.get(brands.get(brand)).get(model);
        inputData.body = bodies.get(body);
        inputData.year = year;
        inputData.region = region;
        inputData.engineVolume = volume;
        inputData.tax = tax;
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
    
    public float getMaterialsCost(ArrayList<Float> counts) {
        float S=0;
        for(int i=0; i<materials.size(); i++) {
            Float f = counts.get(i);
            if(f == null) {
                continue;
            }
            S += f*materials.get(i).getPrice();
        }
        return S;
    }
    
    public float getComponentsCost(ArrayList<Integer> counts) {
        float S=0;
        ArrayList<CarComponent> c = components.get(modelId);
        for(int i=0; i<c.size(); i++) {
            Integer f = counts.get(i);
            if(f == null) {
                continue;
            }
            S += f*c.get(i).getPrice();
        }
        return S;
    }
    
    public float getRapairCost(float hours) {
        float S = 0, factor;
        String query = "SELECT * FROM repair_cost WHERE model_id="+modelId.getId()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                System.out.println("repair not found");
                return 0;
            }
            factor = result.getFloat("val");
        } catch (SQLException e) {
            System.out.println("factor exceprion");
            return 0;
        }
        S = factor * hours;
        return S;
    }
    
    public float getIncreasePrice(ArrayList<Integer> selectedComponents) {
        String query = "SELECT val FROM price_factors WHERE mass >= "+mass
                + " AND year=YEAR(CURDATE())-"+year+" ORDER BY mass ASC LIMIT 1;";
        float factor = 0, S = 0;
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                System.out.println("price factor not found");
                return 0;
            }
            factor = result.getFloat("val");
        } catch (SQLException e) {
            System.out.println("factor exceprion");
            return 0;
        }
        for(Integer i : selectedComponents) {
            CarComponent c = components.get(modelId).get(i);
            S += c.getPrice()*factor/100;
        }
        return S;
    }
    
    public float getEquipmentPrice(ArrayList<Pair<Integer, Integer>> selectedEquipments) {
        float S = 0;
        ArrayList<CarComponent> e = equipments.get(modelId);
        for(Pair p : selectedEquipments) {
            int t = (int)p.second > 95 ? 95 : (int)p.second;
            S += 0.7*e.get((int)p.first).getPrice()*Math.pow(0.97, t);
        }
        return S;
    }
    
    public Float getAveragePrice(int brandIndex, int modelIndex, int year, float volume,
            int bodyIndex, float tax, int region) {
        CarBrand brand = brands.get(brandIndex);
        CarModel model = models.get(brand).get(modelIndex);
        CarBody body = bodies.get(bodyIndex);
        boolean fullOverlap = true;
        float editionPrice=0, yearFactor=0, regionFactor=0, price=0;
        Float value = null;
        String query = "SELECT price FROM editions WHERE model_id="+model.getId()+
                " AND volume="+volume+" AND year="+year+" AND body_id="+body.getId()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                fullOverlap = false;
            } else {
                editionPrice = result.getFloat("price");
            }
        } catch (SQLException e) {
            return null;
        }
        
        if(!fullOverlap) {
            return getAveragePriceByAnalogs(model, year, tax, region);
        }
        System.out.println("price: "+editionPrice);
        
        query = "SELECT y.val FROM models m JOIN year_factors y "
                + "ON m.year_factor=y.id WHERE m.id="+model.getId()
                + " AND y.year=YEAR(CURDATE())-"+year+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                return null;
            }
            yearFactor = result.getFloat("val");
        } catch (SQLException e) {
            return null;
        }
        
        System.out.println("year factor: "+yearFactor);
        
        value = getRegionFactor(model.getId(), region);
        if(value == null) {
            return null;
        }
        regionFactor = value;
        
        System.out.println("region factor: "+regionFactor);
        
        price = editionPrice * yearFactor * regionFactor + tax;
        
        return price;
    }
    
    public float getUsageConditionsFactor(int year,
            ArrayList<Boolean> usageConditionsSelections, 
            ArrayList<Boolean> damagesSelections) {
        float factor = 0;
        for(int i=0; i<usageConditions.size(); i++) {
            if(usageConditionsSelections.get(i)) {
                factor += usageConditions.get(i).getValue();
            }
        }
        float bodyDamage = 0;
        for(int i=0; i<damages.size(); i++) {
            if(damagesSelections.get(i)) {
                bodyDamage += damages.get(i).getValue();
            }
        }
        if(Calendar.getInstance().get(Calendar.YEAR) - year > 8) {
            bodyDamage /= 2;
        }
        if(bodyDamage > 30) {
            bodyDamage = 30;
        }
        return factor + bodyDamage;
    }
    
    //TODO учитывается не для всех категорий машин
    public float getMileageFactor(int year, float mass, int mileage) {
        String query = "SELECT val FROM mileages WHERE year=YEAR(CURDATE())-"+year+";";
        int normalMileage = 0;
        int difference, direction = 0;
        float factor;
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                System.out.println("normalMileage not found");
                return 0;
            }
            normalMileage = result.getInt("val");
        } catch (SQLException e) {
            System.out.println("normalMileage exceprion");
            return 0;
        }
        difference = normalMileage - mileage;
        if(difference > 0) {
            direction = 1;
        }
        difference = Math.abs(difference);
        query = "SELECT val FROM mileage_factors WHERE mass >= "+mass+""
                + " AND direction = "+direction+" ORDER BY mass ASC, "
                + "ABS("+difference+"-difference) ASC LIMIT 1;";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                System.out.println("factor not found");
                return 0;
            }
            factor = result.getFloat("val");
        } catch (SQLException e) {
            System.out.println("factor exceprion");
            return 0;
        }
        return factor;
    }
    
    private Float getAveragePriceByAnalogs(CarModel model, int year, float tax, int region) {
        float averagePrice = 0, wearFactor=0;
        int amount = 0;
        Float regionFactor = getRegionFactor(model.getId(), region);
        if(regionFactor == null) {
            return null;
        }
        System.out.println("Analog!");
        System.out.println("region factor: "+regionFactor);
        String query = "SELECT w.val FROM models m JOIN wear_factors w "
                + "ON m.wear_factor=w.id WHERE m.id="+model.getId()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                return null;
            }
            wearFactor = result.getFloat("val");
        } catch (SQLException e) {
            return null;
        }
        System.out.println("wear factor: "+wearFactor);
        
        query = "SELECT price FROM editions WHERE model_id="+model.getId()+
                " AND year BETWEEN YEAR(CURDATE())-"+year+" AND YEAR(CURDATE())+"
                +year+";";
        try(ResultSet result = statement.executeQuery(query)) {
            while(result.next()) {
                averagePrice += result.getFloat("price") * wearFactor * regionFactor + tax;
                System.out.println("Price: "+result.getFloat("price"));
                amount++;
            }
        } catch (SQLException e) {
            return null;
        }
        
        if(amount == 0) {
            return null;
        }
        return averagePrice / amount;
    }
    
    private Float getRegionFactor(int modelId, int region) {
        float factor;
        String query = "SELECT r.* FROM models m JOIN region_factors r "
                + "ON m.region_factor=r.id WHERE m.id="+modelId+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                return null;
            }
            factor = result.getFloat(regionName[region]);
        } catch (SQLException e) {
            return null;
        }
        return factor;
    }
    
}
