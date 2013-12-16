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
        try (PrintWriter writer = new PrintWriter(new BufferedWriter
                                            (new FileWriter(fileName)))) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            writer.println("На экспертизу представлено автомобильное транспортное средство" 
                            + operableData.getBrand()+" "+operableData.getModel()+" "+operableData.getBody()
                            +" "+operableData.getYear());
            writer.println("Обьем двигателя (в куб. л) " + operableData.getEngineVolume() 
                            + " Масса (в тоннах) " + operableData.getMass() + "Пробег (в км)"
                            + operableData.getMileage());
            writer.println("Регион" + operableData.getRegion() + "Сумма налога (в грн) "
                            + operableData.getTax());
            writer.println("Владельца " + operableData.getFIO()+" Дата рождения "
                            +operableData.getDateOfBirth()+" Номер удостоверения "+operableData.getIdNumber() 
                            + "Дата " + dateFormat.format(cal.getTime()));
        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void outReport(String fileName) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter
                                            (new FileWriter(fileName)))) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            writer.println("По результатам проведения экспертизы транспортного средства" 
                            + operableData.getBrand()+" "+operableData.getModel()+" "+operableData.getBody()
                            +" "+operableData.getYear());
            writer.println("Обьем двигателя (в куб. л) " + operableData.getEngineVolume() 
                            + " Масса (в тоннах) " + operableData.getMass() + "Пробег (в км)"
                            + operableData.getMileage());
            writer.println("Регион" + operableData.getRegion() + "Сумма налога (в грн) "
                            + operableData.getTax());
            
//Массив            writer.println("Условия храниния экплуатации " + operableData.getUsageConditions());
            
//Массив Проверить на 0            writer.println("Дефекты кузова " + operableData.getBodyDamages()); 
            
         
//Массив пара CarComponent.getName, integer            writer.println("Обновленные комплектующие " + operableData.getNewComponents());
            
//    тоже самое        writer.println("Обновленное оборудование " + operableData.getNewEquipments());
            
//    тоже самое        writer.println("Материалы " + operableData.getMaterials());
            
//    тоже самое        writer.println("Комплектующие, которые необходимо заменить " + operableData.getRepairComponents());
            
            writer.println("Трудоемкость ремонта в нормачасах " + operableData.getRepairComplexity());
            
            writer.println("Коэфициент ликвидности " + operableData.getLiquidity_factor());
            
//       проверить на тру фалс     writer.println("Условия храниния экплуатации " + operableData.isRenewable());
            
            
            
            writer.println("Получено следующее ");
            
            
            
            writer.println("Эксперт " + operableData.getFIO() 
                            + "Дата " + dateFormat.format(cal.getTime()));
        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
