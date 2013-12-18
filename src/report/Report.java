/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import java.io.*;
import auto.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afterthegame
 */
public class Report {

    InputData operableData;

    public Report(InputData operableData) {
        this.operableData = operableData;
    }

    public void inReport(String fileName) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            writer.println("На экспертизу представлено автомобильное транспортное средство - "
                    + operableData.getBrand().getName() + " "
                    + operableData.getModel().getName() + " "
                    + "\nТип кузова - " + operableData.getBody().getName()
                    + "\nГод выпуска - " + operableData.getYear());
            writer.println("Обьем двигателя (в куб. л) - " + operableData.getEngineVolume()
                    + "\nМасса (в тоннах) - " + operableData.getMass()
                    + "\nПробег (в км) - " + operableData.getMileage());
            writer.println("Регион - " + operableData.getRegion() + " Сумма налога (в грн) - "
                    + operableData.getTax());
            writer.println("Владельца - " + operableData.getFIO() + " Дата рождения - "
                    + operableData.getDateOfBirth() + "\nНомер удостоверения - "
                    + operableData.getIdNumber()
                    + "\nДата - " + dateFormat.format(cal.getTime()));
        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void outReport(String fileName) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            writer.println("По результатам проведения экспертизы транспортного средства - "
                    + operableData.getBrand().getName() + " "
                    + operableData.getModel().getName() + " "
                    + "\nТип кузова - " + operableData.getBody().getName()
                    + "\nГод выпуска - " + operableData.getYear());
            writer.println("Обьем двигателя (в куб. л) - " + operableData.getEngineVolume()
                    + "\nМасса (в тоннах) - " + operableData.getMass()
                    + "\nПробег (в км) - " + operableData.getMileage());
            writer.println("Регион - " + operableData.getRegion() + " Сумма налога (в грн) - "
                    + operableData.getTax());

            writer.println("Условия храниния экплуатации: ");
            ArrayList<UsageConditionFactor> _usageConditionsList = operableData.getUsageConditions();
            if (_usageConditionsList.isEmpty()) {
                writer.println("______________Условия храниния экплуатации нет");
            } else {
            for (UsageConditionFactor iterator : _usageConditionsList) {
                writer.println("______________" + iterator.getName() + " " + iterator.getValue() + " грн");
                }
            }

            writer.println("Дефекты кузова: ");
            ArrayList<UsageConditionFactor> _bodyDamages = operableData.getBodyDamages();
            if (_bodyDamages.isEmpty()) {
                writer.println("______________Дефектов кузова нет");
            } else {
                for (UsageConditionFactor iterator : _bodyDamages) {
                    writer.println("______________" + iterator.getName() + " " + iterator.getValue() + " грн");
                }
            }

            writer.println("Обновленные комплектующие: ");
            ArrayList<Pair<CarComponent, Integer>> _carCarComponentsList = operableData.getNewComponents();
            if (_carCarComponentsList.isEmpty()) {
                writer.println("______________Обновленныx комплектующиx нет");
            } else {
                for (Pair<CarComponent, Integer> comonentPair : _carCarComponentsList) {
                    writer.println("______________" + comonentPair.first.getName()
                            + "  " + comonentPair.second + " шт." 
                            +" Цена (за 1 шт.): "+ comonentPair.first.getPrice()+" грн");
                }
            }

            writer.println("Обновленное оборудование: ");
            ArrayList<Pair<CarComponent, Integer>> _carCarEquipmentsList = operableData.getNewEquipments();
            if (_carCarEquipmentsList.isEmpty()) {
                writer.println("______________Обновленного оборудования нет");
            } else {
                for (Pair<CarComponent, Integer> comonentPair : _carCarEquipmentsList) {
                    writer.println("______________" + comonentPair.first.getName()
                            + "  " + comonentPair.second + " шт." 
                            +" Цена (за 1 шт.): "+ comonentPair.first.getPrice()+" грн");
                }
            }

            writer.println("Материалы: ");
            ArrayList<Pair<CarComponent, Float>> _carCarMaterialsList = operableData.getMaterials();
            if (_carCarMaterialsList.isEmpty()) {
                writer.println("______________Материалов нет");
            } else {
                for (Pair<CarComponent, Float> comonentPair : _carCarMaterialsList) {
                    writer.println("______________" + comonentPair.first.getName()
                            + "  " + comonentPair.second + " шт." 
                            +" Цена (за 1 шт.): "+ comonentPair.first.getPrice()+" грн");
                }
            }

            writer.println("Комплектующие, которые необходимо заменить: ");
            ArrayList<Pair<CarComponent, Integer>> _carRepairComponentsList = operableData.getRepairComponents();
            if (_carRepairComponentsList.isEmpty()) {
                writer.println("______________Комплектующих, которые необходимо заменить - нет");
            } else {
                for (Pair<CarComponent, Integer> comonentPair : _carRepairComponentsList) {
                    writer.println("______________" + comonentPair.first.getName()
                            + "  " + comonentPair.second + " шт." 
                            +" Цена (за 1 шт.): "+ comonentPair.first.getPrice()+" грн");
                }
            }

            writer.println("Трудоемкость ремонта в нормачасах - " + operableData.getRepairComplexity());

            writer.println("Коэфициент ликвидности - " + operableData.getLiquidity_factor());

            writer.print("Условия храниния экплуатации - ");
            if (operableData.isRenewable()) {
                writer.println("обновление ТС требуется");
            } else {
                writer.println("обновление не ТС требуется");
            }
            
            writer.println("Получено следующее: ");
            writer.println("______________" + "Средняя рыночная цена: " + operableData.getAveragePrice() + " грн");
            writer.println("______________" + "Дополнительная рыночная стоимость: " + operableData.getAveragePrice() + " грн");
            writer.println("______________" + "Увеличение стоимости, в случае обновления составляющих: " + operableData.getSv1() + " грн");
            writer.println("______________" + "Величина корректировки, в зависимости от комплектности: " + operableData.getSv2() + " грн");
            writer.println("______________" + "Стоимость ремонта, учитывая коэф. износа: " + operableData.getRepairPriceWithWearFactor() + " грн");
            writer.println("______________" + "Стоимость ремонта: " + operableData.getRepairCost() + " грн");
            writer.println("______________" + "Стоимость материалов: " + operableData.getMaterialsCost() + " грн");
            writer.println("______________" + "Стоимость компонентов: " + operableData.getComponentCost() + " грн");
            writer.println("______________" + "Коэфициент физического износа: 0");
            writer.println("______________" + "Величина утраты товарной стоимости: " + operableData.getLossOfCommercialCost() + " грн");
            writer.println("______________" + "Ликвидационная стоимость: " + operableData.getLiquidityPrice() + " грн");
            writer.println("______________" + "Рыночная стоимость: " + operableData.getCost() + " грн");
            writer.println("______________" + "Величина материального ущерба: " + operableData.getDamage() + " грн");

            writer.println("Эксперт - " + operableData.getFIO()
                    + "\nДата - " + dateFormat.format(cal.getTime()));
        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
