/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import gui.GuiController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class InputData {
    
    private String regionName[] = {"low", "high", "another"};
    
    private Statement statement;
    private float cost;
    private float averagePrice;
    private float repairCost;
    private float materialsCost;
    private float componentsCost;
    private float repairPriceWithWearFactor;
    private float lossOfCommercialCost;
    
    String FIO, dateOfBirth, idNumber;
    String expert;
    
    CarBrand brand;
    CarModel model;
    CarBody body;
    int year;
    float engineVolume;
    float mass;
    float mileage;
    int region;
    float tax;
    
    float liquidity_factor;
    boolean renewable;
    
    ArrayList<UsageConditionFactor> usageConditions;
    ArrayList<UsageConditionFactor> bodyDamages;
    
    ArrayList<Pair<CarComponent, Integer>> newComponents;
    ArrayList<Pair<CarComponent, Integer>> newEquipments;
    
    float repairComplexity;
    ArrayList<Pair<CarComponent, Float>> materials;
    ArrayList<Pair<CarComponent, Integer>> repairComponents;
    
    public InputData(Statement st) {
        statement = st;
    }
    
    /*public String getDriver() {
        return FIO;
    }
    
    public String getNumber() {
        return idNumber;
    }
    
    public String getBrand() {
        return brand.getName();
    }
    
    public String getModel() {
        return model.getName();
    }
    
    public int getYear() {
        return year;
    }
    
    public float getMileage() {
        return mileage;
    }
    
    public float getMass() {
        return mass;
    }
    
    public float getVolume() {
        return engineVolume;
    }*/
    
    public float getCost() throws Exception {
        cost = 0;
        System.out.println("AveragePrice");
        averagePrice = getAveragePrice();
        System.out.println("Гк и Дз");
        cost = 1 + getMileageFactor()/100 + getUsageConditionsFactor()/100;
        cost *= averagePrice;
        System.out.println("Сдод");
        cost += getAdditionalPrice();
        System.out.println("exit");
        return cost;
    }
    
    public float getLiquidityPrice() throws Exception {
        if(liquidity_factor < 0.8 || liquidity_factor > 0.95) {
            throw new Exception("Неверный коэффициент ликвидности!");
        }
        return cost*liquidity_factor;
    }
    
    public float getDamage() {
        if(!renewable) {
            return cost;
        } 
        if(repairPriceWithWearFactor >= cost) {
            return cost;
        } else if(repairPriceWithWearFactor + lossOfCommercialCost >= cost) {
            return cost;
        }
        return repairPriceWithWearFactor + lossOfCommercialCost;
    }
    
    //Средняя рыночная цена (Сср)
    private float getAveragePrice() throws Exception {
        boolean fullOverlap = true;
        float editionPrice = 0;
        float yearFactor = 0; 
        float price=0;
        //получаем цену, если есть полное совпадение
        String query = "SELECT price FROM editions"
                + " WHERE model_id="+model.getId()
                + " AND volume="+engineVolume+" AND year="+year
                +" AND body_id="+body.getId()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                fullOverlap = false;
            } else {
                editionPrice = result.getFloat("price");
            }
        } catch (SQLException e) {
            throw new Exception("Сбой БД!");
        }
        //Если полного совпадения нет, пытаемся получить цену по аналогам 
        if(!fullOverlap) {
            return getAveragePriceByAnalogs();
        }
        System.out.println("price: "+editionPrice);
        
        //получаем коэффициент уменьшения цены от срока эксплуатации
        query = "SELECT y.val FROM models m JOIN year_factors y "
                + "ON m.year_factor=y.id WHERE m.id="+model.getId()
                + " AND y.year=YEAR(CURDATE())-"+year+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                        "Не найден коэффициент эксплуатации!\nВведите вручную:"
                        + " Модель: "+model.getName()+" Год выпуска: "+year
                    );
                    try {
                        yearFactor = Float.valueOf(r);
                    } catch(NumberFormatException e) {
                        f = true;
                    }
                } while(f);
                //throw new Exception("Не найден коэффициент эксплуатации!");
            } else {
                yearFactor = result.getFloat("val");
            }
        } catch (SQLException e) {
            throw new Exception("Сбой бд!");
        }
        
        System.out.println("year factor: "+yearFactor);
        
        price = editionPrice * yearFactor * getRegionFactor() + tax;
        
        return price;
    }
    
    //Средняя рыночная цена по аналогам
    private float getAveragePriceByAnalogs() throws Exception {
        float averagePrice = 0, wearFactor=0;
        int amount = 0;
        float regionFactor = getRegionFactor();
        System.out.println("Analog!");
        System.out.println("region factor: "+regionFactor);
        //Коэф-т функционального износа (Кз)
        String query = "SELECT w.val FROM models m JOIN wear_factors w "
                + "ON m.wear_factor=w.id WHERE m.id="+model.getId()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                        "Ненайден коэфициент износа!\nВведите вручную:"
                        + " Модель: "+model.getName()
                    );
                    try {
                        wearFactor = Float.valueOf(r);
                    } catch(NumberFormatException e) {
                        f = true;
                    }
                } while(f);
                //throw new Exception("Ненайден коэфициент износа!");
            } else {
                wearFactor = result.getFloat("val");
            }
        } catch (SQLException e) {
            throw new Exception("Сбой БД!");
        }
        System.out.println("wear factor: "+wearFactor);
        
        //поиск аналогов
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
            throw new Exception("Сбой БД!");
        }
        
        if(amount == 0) {
            throw new Exception("Аналоги не найдены!");
        }
        return averagePrice / amount;
    }
     
     //коэф-т корректировки рыночной стоимости от величины пробега (Гк)
    private float getMileageFactor() throws Exception {
        String query = "SELECT flag FROM categories WHERE id="+model.getCategory()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                throw new Exception("Ненайдена категория");
            }
            if(!result.getBoolean("flag")) {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Сбой БД!");
        }
        query = "SELECT val FROM mileages"
                + " WHERE year=YEAR(CURDATE())-"+year+";";
        int normalMileage = 0;
        int difference, direction = 0;
        float factor = 0;
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                        "Ненайдена нормативная величина пробега!\nВведите вручную:"
                        + " Год выпуска: "+year
                    );
                    try {
                        normalMileage = Integer.valueOf(r);
                    } catch(NumberFormatException e) {
                        f = true;
                    }
                } while(f);
                //throw new Exception("Ненайден нормативная величина пробега");
            } else {
                normalMileage = result.getInt("val");                
            }
        } catch (SQLException e) {
            throw new Exception("Сбой БД!");
        }
        difference = normalMileage - (int)Math.round(mileage);
        if(difference > 0) {
            direction = 1;
        }
        difference = Math.abs(difference);
        query = "SELECT val FROM mileage_factors WHERE mass >= "+mass+""
                + " AND direction = "+direction+" ORDER BY mass ASC, "
                + "ABS("+difference+"-difference) ASC LIMIT 1;";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                        "Ненайден коэффициент корректировки по величине пробега!"
                        + "\nВведите вручную: Разность: "+direction*difference
                    );
                    try {
                        factor = Float.valueOf(r);
                    } catch(NumberFormatException e) {
                        f = true;
                    }
                } while(f);
                //throw new Exception("Ненайден коэффициент корректировки по величине пробега");
            } else {
                factor = result.getFloat("val");
            }
        } catch (SQLException e) {
            throw new Exception("Сбой БД!");
        }
        return factor*(direction==1 ? 1 : -1);
    }
    
    //коэф-т изменения стоимости от условий использования (Дз)
    private float getUsageConditionsFactor() {
        float factor = 0;
        for(UsageConditionFactor u : usageConditions) {
            factor += u.getValue();
        }
        float bodyDamage = 0;
        for(UsageConditionFactor u : bodyDamages) {
            bodyDamage += u.getValue();
        }
        if(Calendar.getInstance().get(Calendar.YEAR) - year > 8) {
            bodyDamage /= 2;
        }
        if(bodyDamage > 30) {
            bodyDamage = 30;
        }
        return factor + bodyDamage;
    }
    
    //дополнительное увел. (уменьшение) рыночной стоимости (Сдод)
    private float getAdditionalPrice() throws Exception {
        float additionalPrice = 0;
        System.out.println("IncreasePrice");
        additionalPrice = getIncreasePrice();
        System.out.println("EqPrice");
        additionalPrice += getEquipmentPrice();
        System.out.println("RepairWithWear");
        repairPriceWithWearFactor = getRepairPriceWithWearFactor();
        System.out.println("getLoss");
        lossOfCommercialCost = getLossOfCommercialCost();
        additionalPrice -= repairPriceWithWearFactor + lossOfCommercialCost;
        System.out.println("Сдодexit");
        return additionalPrice;
    }
    
    //увеличение стоимости ТС в случае обновления состовляющих (Св1)
    private float getIncreasePrice() throws Exception {
        //процентный показатель рыночной стоимости (Г)
        String query = "SELECT year, val FROM price_factors WHERE mass ="
                + " (SELECT mass FROM price_factors WHERE mass >= "+mass
                + " ORDER BY mass ASC LIMIT 1);";
        float S = 0;
        int m2y = 0;
        HashMap<Integer, Float> factors = new HashMap<Integer, Float>();
        try(ResultSet result = statement.executeQuery(query)) {
            /*if(!result.next()) {
                System.out.println("no result");
                throw new Exception("Ненайден показатель рыночной стоимости!");
            }*/
            while(result.next()) {
                System.out.println(result.getInt("year") + " " + result.getFloat("val"));
                factors.put(result.getInt("year"), result.getFloat("val"));
            }
            //factor = result.getFloat("val");
        } catch (SQLException e) {
            System.out.println("sql exc");
                throw new Exception("Сбой БД");
        }
        System.out.println("factor");
        for(Pair<CarComponent, Integer> p : newComponents) {
            m2y = (p.second % 12) + 1;
            System.out.println("m2y: "+m2y);
            Float factor = factors.get(m2y);
            if(factor == null) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                        "Ненайден коэффициент корректировки для стоимости компоненты: "
                        + p.first.getName()+"!\n Срок эксплуатации: "+m2y+" Ввести вручную:"
                    );
                    try {
                        factor = Float.valueOf(r);
                    } catch(NumberFormatException e) {
                        f = true;
                    }
                } while(f);
            }
            S += p.first.getPrice()*(factor)/100;
        }
        /*for(CarComponent c : newComponents) {
            S += c.getPrice()*factor/100;
        }*/
        return S;
    }
    
    //Величина корректности стоимости ТС в зависимости от его комплектности (Св2)
    private float getEquipmentPrice() {
        float S = 0;
        for(Pair p : newEquipments) {
            int t = (int)p.second > 95 ? 95 : (int)p.second;
            S += 0.7*((CarComponent)p.first).getPrice()*Math.pow(0.97, t);
        }
        return S;
    }
    
    //Стоимость востановительного ремонта с учетом физического износа (Сврз)
    private float getRepairPriceWithWearFactor() throws Exception {
        System.out.println("repairCost");
        repairCost = getRepairCost();
        System.out.println("MaterialCost");
        materialsCost = getMaterialsCost();
        System.out.println("ComponetnCost");
        componentsCost = getComponentsCost();
        System.out.println("WearExit");
        return repairCost+materialsCost+componentsCost*(1-0);
    }
    
    //Стоимость востановительного ремонта (Ср)
    private float getRepairCost() throws Exception {
        float S = 0, factor = 0;
        String query = "SELECT * FROM repair_cost WHERE model_id="+model.getId()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                        "Ненайдена средняя стоимость нормо-часа ремонта!"
                        + "\nВведите вручную: Модель: "+model.getName()
                    );
                    try {
                        factor = Float.valueOf(r);
                    } catch(NumberFormatException e) {
                        f = true;
                    }
                } while(f);
                //throw new Exception("Ненайдена средняя стоимость нормо-часа ремонта");
            } else {
                factor = result.getFloat("val");
            }
        } catch (SQLException e) {
            throw new Exception("Сбой БД!");
        }
        S = factor * repairComplexity;
        return S;
    }
    
    //Стоимость необходимых для ремонта материалов (См)
    private float getMaterialsCost() {
        float S=0;
        for(Pair p : materials) {
            S += (float)p.second*((CarComponent)p.first).getPrice();
        }
        return S;
    }
    
    //Стоимость комплектующих, которые необходимо заменить при ремонте (Сс)
    private float getComponentsCost() {
        float S=0;
        for(Pair p : repairComponents) {
            S += (int)p.second*((CarComponent)p.first).getPrice();
        }
        return S;
    }
    
   // (ВТВ)
    private float getLossOfCommercialCost() throws Exception {
        float x = 0, A, B;
        A = repairPriceWithWearFactor / averagePrice;
        B = repairCost/(materialsCost + componentsCost);
        int month = (Calendar.getInstance().get(Calendar.YEAR) - year)*12;
        String query = "SELECT val FROM X WHERE A<="+A+" AND B<="+B+
                " AND month>="+month+" ORDER BY A DESC, B DESC, month ASC LIMIT 1;";
        System.out.println(A + " " + B + " " + month);
        try (ResultSet result = statement.executeQuery(query)) {
            if (!result.next()) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                            "Ненайден коэффициент утраты товарной стоимости!"
                            + "\nВведите вручную: A: "+A+" B: "+B
                            +" Срок эксплуатации в месяцах: "+month
                    );
                    try {
                        x = Float.valueOf(r);
                    } catch (NumberFormatException e) {
                        f = true;
                    }
                } while (f);
                //throw new Exception("Не найден коэффициент утраты товарной стоимости!");
            } else {
                x = result.getFloat("val");
            }
        } catch (SQLException e) {
            throw new Exception("Сбой БД!");
        }
        return x/100*(averagePrice + repairPriceWithWearFactor);
    }
    
    //Коэффициент рынка региона
    private Float getRegionFactor() throws Exception {
        float factor = 0;
        String query = "SELECT r.* FROM models m JOIN region_factors r "
                + "ON m.region_factor=r.id WHERE m.id="+model.getId()+";";
        try(ResultSet result = statement.executeQuery(query)) {
            if(!result.next()) {
                boolean f = false;
                do {
                    String r = GuiController.query(
                            "Не найден соответствующий фактор региона!"
                            + "\nВведите вручную: Модель: "+model.getName()
                    );
                    try {
                        factor = Float.valueOf(r);
                    } catch (NumberFormatException e) {
                        f = true;
                    }
                } while (f);
                //throw new Exception("Не найден соответствующий фактор региона!");
            } else {
                factor = result.getFloat(regionName[region]);
            }
        } catch (SQLException e) {
            throw new Exception("Сбой БД!");
        }
        return factor;
    }

    public String getFIO() {
        return FIO;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public CarModel getModel() {
        return model;
    }

    public CarBody getBody() {
        return body;
    }

    public int getYear() {
        return year;
    }

    public float getEngineVolume() {
        return engineVolume;
    }

    public float getMass() {
        return mass;
    }

    public float getMileage() {
        return mileage;
    }

    public int getRegion() {
        return region;
    }

    public float getTax() {
        return tax;
    }

    public float getLiquidity_factor() {
        return liquidity_factor;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public ArrayList<UsageConditionFactor> getUsageConditions() {
        return usageConditions;
    }

    public ArrayList<UsageConditionFactor> getBodyDamages() {
        return bodyDamages;
    }

    public ArrayList<Pair<CarComponent, Integer>> getNewComponents() {
        return newComponents;
    }

    public ArrayList<Pair<CarComponent, Integer>> getNewEquipments() {
        return newEquipments;
    }

    public float getRepairComplexity() {
        return repairComplexity;
    }

    public ArrayList<Pair<CarComponent, Float>> getMaterials() {
        return materials;
    }

    public ArrayList<Pair<CarComponent, Integer>> getRepairComponents() {
        return repairComponents;
    }
    
}
