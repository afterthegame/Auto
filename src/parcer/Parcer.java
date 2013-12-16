package parcer;

/**
 *
 * @author afterthegame
 */
import auto.CarEdition;
import auto.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class Parcer {

    private static final Logger LOG = Logger.getLogger(Parcer.class.getName());

    private ArrayList<CarBrand> brandsList;
    private ArrayList<CarBody> bodiesList;
    private ArrayList<CarCategory> categoriesList;

    private HashMap<CarModel, ArrayList<CarEdition>> editionsHashMap;
    private HashMap<CarBrand, ArrayList<CarModel>> modelsHashMap;
    private HashMap<CarModel, ArrayList<CarComponent>> componentsHashMap;
    private HashMap<CarModel, ArrayList<CarComponent>> equipmentsHashMap;
    private HashMap<CarModel, ArrayList<CarFactor>> yearFactorsHashMap;

    private String _brandName;
    private String _modelName;
    private String _bodyName;
    private String _materialName;
    private String _componentName;
    private String _categoryName;
    private int _idModel = 0;
    private int _idEdition = 0;
    private float _repairCost;
    private float _volume;
    private float _price;
    private int _year;
    private float _wearFactor;
    private float _region1;
    private float _region2;
    private float _region3;
    private boolean _flag;

    private final String path;
    private final Statement statement;
    private int _idComponent = 0;
    private int _idEquipment = 0;
    private String _equipmentName;
    private float _factor;

    public Parcer(String path, Statement statement) {
        this.path = path;
        this.statement = statement;
        bodiesList = new ArrayList<>();
        brandsList = new ArrayList<>();
        categoriesList = new ArrayList<>();
        editionsHashMap = new HashMap<>();
        modelsHashMap = new HashMap<>();
        componentsHashMap = new HashMap<>();
        equipmentsHashMap = new HashMap<>();
        yearFactorsHashMap = new HashMap<>();
        
    }

    public void startParcer() {
        CategoryParcer();
        BrandModelEditionBodyCategoryRegionWearRepairParcer();
        ComponentsParcer();
        EquipmentsParcer();
        YearFactorParcer();
        MaterialsParcer();
        UpdateDatabaseAfterParce();
    }

    public void MaterialsParcer() {
        InputStream in = null;
        try {
//            in = new FileInputStream("/home/afterthegame/BrandModelEditionBody.xls");
            in = new FileInputStream(path + "/MATERIALS.xls");
            HSSFWorkbook wb = new HSSFWorkbook(in); //Получаем workbook
            HSSFSheet sheet = wb.getSheetAt(0); // Проверяем только первую страницу
            Iterator<Row> it = sheet.iterator();
//             Пропускаем "шапку" таблицы
//            Названия таблиц
            if (it.hasNext()) {
                it.next();
            }

            while (it.hasNext()) {
                Row row = it.next();
                Iterator<Cell> cells = row.iterator();
                while (cells.hasNext()) {
                    String tmp;
                    Cell cell = cells.next();
                    switch (cell.getColumnIndex()) {
//                        Read materials
//                         name
                        case 0:
                            _materialName = cell.getStringCellValue();
                            break;
//                        price
                        case 1:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _price = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _price = Float.valueOf(tmp);
                            }
//                            statement.executeUpdate("INSERT INTO materials(name, price) VALUES (" 
//                                    + _materialName + "," + _price + ");");
                            break;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void CategoryParcer() {
        InputStream in = null;
        try {
//            in = new FileInputStream("/home/afterthegame/BrandModelEditionBody.xls");
            in = new FileInputStream(path + "/CATEGORIES.xls");
            HSSFWorkbook wb = new HSSFWorkbook(in); //Получаем workbook
            HSSFSheet sheet = wb.getSheetAt(0); // Проверяем только первую страницу
            Iterator<Row> it = sheet.iterator();
//             Пропускаем "шапку" таблицы
//            Названия таблиц
            if (it.hasNext()) {
                it.next();
            }
            while (it.hasNext()) {
                Row row = it.next();
                Iterator<Cell> cells = row.iterator();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getColumnIndex()) {
//                        Read category
//                         name
                        case 0:
                            _categoryName = cell.getStringCellValue();
                            break;
//                        price
                        case 1:
                            if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                                _flag = cell.getBooleanCellValue();
                            }
                            break;
                    }
                    CarCategory _Category = new CarCategory(categoriesList.size() + 1, _categoryName, _flag);
                    categoriesList.add(_Category);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void ComponentsParcer() {
        InputStream in = null;
        CarModel _Model;
        CarBrand _Brand;
        try {
//            in = new FileInputStream("/home/afterthegame/BrandModelEditionBody.xls");
            in = new FileInputStream(path + "/COMPONENTS.xls");
            HSSFWorkbook wb = new HSSFWorkbook(in); //Получаем workbook
            HSSFSheet sheet = wb.getSheetAt(0); // Проверяем только первую страницу
            Iterator<Row> it = sheet.iterator();
//             Пропускаем "шапку" таблицы
//            Названия таблиц
            if (it.hasNext()) {
                it.next();
            }
            _Model = null;
            _Brand = null;
            while (it.hasNext()) {
                Row row = it.next();
                Iterator<Cell> cells = row.iterator();

                _brandName = null;
                _modelName = null;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getColumnIndex()) {
//                        Read category
//                         бренд
                        case 0:
                            _brandName = cell.getStringCellValue();
                            LOG.info(_brandName);
                            break;
//                        price
                        case 1:
                            _modelName = cell.getStringCellValue();
                            LOG.info(_modelName);
                            break;
                        case 2:
                            _componentName = cell.getStringCellValue();
                            LOG.info(_componentName);
                            break;
                        case 3:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _price = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                String tmp = cell.getStringCellValue();
                                _price = Float.valueOf(tmp);
                            }
                            break;
                    }

                }
                LOG.info("brand: " + _brandName);
                LOG.info("model: " + _modelName);
                if (_brandName != null) {
                    for (CarBrand brand : brandsList) {
                        if (brand.getName().equals(_brandName)) {
                            _Brand = brand;
                            break;
                        }
                    }
                    if (_Brand == null) {
                        throw new NullPointerException("Не найден бренд");
                    }

                    ArrayList<CarModel> _currentModelsList;
                    _currentModelsList = modelsHashMap.get(_Brand);
                    for (CarModel model : _currentModelsList) {
                        if (model.getName().equals(_modelName)) {
                            _Model = model;
                            break;
                        }
                    }
                    if (_Model == null) {
                        throw new NullPointerException("Не найдена модель");
                    }
                    ArrayList<CarComponent> _currentComponentsList;
                    _currentComponentsList = componentsHashMap.get(_Model);
                    CarComponent _Component = new CarComponent(++_idComponent, _componentName, _price, _Model);
                    _currentComponentsList.add(_Component);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void EquipmentsParcer() {
        InputStream in = null;
        CarModel _Model;
        CarBrand _Brand;
        try {
//            in = new FileInputStream("/home/afterthegame/BrandModelEditionBody.xls");
            in = new FileInputStream(path + "/EQUIPMENTS.xls");
            HSSFWorkbook wb = new HSSFWorkbook(in); //Получаем workbook
            HSSFSheet sheet = wb.getSheetAt(0); // Проверяем только первую страницу
            Iterator<Row> it = sheet.iterator();
//             Пропускаем "шапку" таблицы
//            Названия таблиц
            if (it.hasNext()) {
                it.next();
            }
            _Model = null;
            _Brand = null;
            while (it.hasNext()) {
                Row row = it.next();
                Iterator<Cell> cells = row.iterator();

                _brandName = null;
                _modelName = null;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getColumnIndex()) {
//                        Read category
//                         бренд
                        case 0:
                            _brandName = cell.getStringCellValue();
                            LOG.info(_brandName);
                            break;
//                        price
                        case 1:
                            _modelName = cell.getStringCellValue();
                            LOG.info(_modelName);
                            break;
                        case 2:
                            _componentName = cell.getStringCellValue();
                            LOG.info(_componentName);
                            break;
                        case 3:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _price = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                String tmp = cell.getStringCellValue();
                                _price = Float.valueOf(tmp);
                            }
                            break;
                    }

                }
                LOG.info("brand: " + _brandName);
                LOG.info("model: " + _modelName);
                if (_brandName != null) {
                    for (CarBrand brand : brandsList) {
                        if (brand.getName().equals(_brandName)) {
                            _Brand = brand;
                            break;
                        }
                    }
                    if (_Brand == null) {
                        throw new NullPointerException("Не найден бренд");
                    }

                    ArrayList<CarModel> _currentModelsList;
                    _currentModelsList = modelsHashMap.get(_Brand);
                    for (CarModel model : _currentModelsList) {
                        if (model.getName().equals(_modelName)) {
                            _Model = model;
                            break;
                        }
                    }
                    if (_Model == null) {
                        throw new NullPointerException("Не найдена модель");
                    }
                    ArrayList<CarComponent> _currentEquipmentsList;
                    _currentEquipmentsList = equipmentsHashMap.get(_Model);
                    CarComponent _Equipment = new CarComponent(++_idEquipment, _equipmentName, _price, _Model);
                    _currentEquipmentsList.add(_Equipment);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void YearFactorParcer() {
        InputStream in = null;
        CarModel _Model;
        CarBrand _Brand;
        try {
//            in = new FileInputStream("/home/afterthegame/BrandModelEditionBody.xls");
            in = new FileInputStream(path + "/YEARFACTOR.xls");
            HSSFWorkbook wb = new HSSFWorkbook(in); //Получаем workbook
            HSSFSheet sheet = wb.getSheetAt(0); // Проверяем только первую страницу
            Iterator<Row> it = sheet.iterator();
//             Пропускаем "шапку" таблицы
//            Названия таблиц
            if (it.hasNext()) {
                it.next();
            }
            _Model = null;
            _Brand = null;
            while (it.hasNext()) {
                Row row = it.next();
                Iterator<Cell> cells = row.iterator();

                _brandName = null;
                _modelName = null;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getColumnIndex()) {
//                        Read category
//                         бренд
                        case 0:
                            _brandName = cell.getStringCellValue();
                            LOG.info(_brandName);
                            break;
//                        price
                        case 1:
                            _modelName = cell.getStringCellValue();
                            LOG.info(_modelName);
                            break;
                        case 2:
                            _year = (int) cell.getNumericCellValue();
                            LOG.info(""+_year);
                            break;
                        case 3:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _factor = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                String tmp = cell.getStringCellValue();
                                _factor = Float.valueOf(tmp);
                            }
                            break;
                    }

                }
                LOG.info("brand: " + _brandName);
                LOG.info("model: " + _modelName);
                if (_brandName != null) {
                    for (CarBrand brand : brandsList) {
                        if (brand.getName().equals(_brandName)) {
                            _Brand = brand;
                            break;
                        }
                    }
                    if (_Brand == null) {
                        throw new NullPointerException("Не найден бренд");
                    }

                    ArrayList<CarModel> _currentModelsList;
                    _currentModelsList = modelsHashMap.get(_Brand);
                    for (CarModel model : _currentModelsList) {
                        if (model.getName().equals(_modelName)) {
                            _Model = model;
                            break;
                        }
                    }
                    if (_Model == null) {
                        throw new NullPointerException("Не найдена модель");
                    }
                    ArrayList<CarFactor> _currentYearFactorsList;
                    _currentYearFactorsList = yearFactorsHashMap.get(_Model);
                    CarFactor _Factor = new CarFactor(_Model.getId(), _year, _factor);
                    _currentYearFactorsList.add(_Factor);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void BrandModelEditionBodyCategoryRegionWearRepairParcer() {
        InputStream in = null;
        CarBrand _Brand;
        CarModel _Model;
        CarCategory _Category;
        CarBody _Body;
        try {
//            in = new FileInputStream("/home/afterthegame/auto.xls");
            in = new FileInputStream(path + "/auto.xls");
//            Получаем workbook
            HSSFWorkbook wb = new HSSFWorkbook(in);
//             Проверяем только первую страницу
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();
//             Пропускаем "шапку" таблицы
//            Названия таблиц
            if (it.hasNext()) {
                it.next();
            }
            while (it.hasNext()) {
                Row row = it.next();
                Iterator<Cell> cells = row.iterator();
                _Brand = null;
                _Model = null;
                _Category = null;
                _Body = null;

                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    String tmp;
                    switch (cell.getColumnIndex()) {
//                        Читаем Бренд
//                         имя
                        case 0:
                            _brandName = cell.getStringCellValue();
                            LOG.log(Level.INFO, "{0}\t", _brandName);
                            break;
                        //Читаем модель
                        //имя
                        case 1:
                            _modelName = cell.getStringCellValue();
                            LOG.log(Level.INFO, "{0}\t", _modelName);
                            break;
//                        Читаем выпуск
//                        год
                        case 2:
                            _year = (int) cell.getNumericCellValue();
                            LOG.log(Level.INFO, "{0}\t", _year);
                            break;
//                        обьем двигателя
                        case 3:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _volume = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _volume = Float.valueOf(tmp);
                            }
                            LOG.log(Level.INFO, "{0}\t", _volume);
                            break;
//                        цена
                        case 4:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _price = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _price = Float.valueOf(tmp);
                            }
                            LOG.log(Level.INFO, "{0}\t", _price);
                            break;
//                        кузов
                        case 5:
                            _bodyName = cell.getStringCellValue();
                            LOG.log(Level.INFO, "{0}\t", _bodyName);
                            break;
//                        категория
                        case 6:
                            _categoryName = cell.getStringCellValue();
                            LOG.log(Level.INFO, "{0}\t", _categoryName);
                            break;
//                            коэф. износа
                        case 7:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _wearFactor = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _wearFactor = Float.valueOf(tmp);
                            }
                            break;
//                            цена ремонта
                        case 8:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _repairCost = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _repairCost = Float.valueOf(tmp);
                            }
                            break;
//                            коэф региона 
                        case 9:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _region1 = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _region1 = Float.valueOf(tmp);
                            }
                            break;
                        case 10:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _region2 = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _region2 = Float.valueOf(tmp);
                            }
                            break;
                        case 11:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                _region3 = (float) cell.getNumericCellValue();
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                tmp = cell.getStringCellValue();
                                _region3 = Float.valueOf(tmp);
                            }
                            break;
                    }
                }

// --------------------------------ищем категорию------------------------------
                for (CarCategory category : categoriesList) {
                    if (category.getName().equals(_categoryName)) {
                        _Category = category;
                        break;
                    }
                }
                if (_Category == null) {
                    throw new NullPointerException("Категория не найдена");
                }

// --------------------------------ищем кузов---------------------------------
                for (CarBody body : bodiesList) {
                    if (body.getName().equals(_bodyName)) {
                        _Body = body;
                        break;
                    }
                }
                if (_Body == null) {
                    _Body = new CarBody(brandsList.size() + 1, _brandName);
                    bodiesList.add(_Body);
                }

// ---------------------------------ищем бренд---------------------------------
                for (CarBrand brand : brandsList) {
                    if (brand.getName().equals(_brandName)) {
                        _Brand = brand;
                        break;
                    }
                }
                if (_Brand == null) {
                    _Brand = new CarBrand(brandsList.size() + 1, _brandName);
                    brandsList.add(_Brand);
                    modelsHashMap.put(_Brand, new ArrayList<CarModel>());
                }

// --------------------------------ищем модель---------------------------------
                ArrayList<CarModel> _currentModelsList;
                _currentModelsList = modelsHashMap.get(_Brand);
                for (CarModel model : _currentModelsList) {
                    if (model.getName().equals(_modelName)) {
                        _Model = model;
                        break;
                    }
                }
                if (_Model == null) {
                    _Model = new CarModel(++_idModel, _modelName, _Brand, _Category.getId(), _repairCost, _wearFactor,
                            _region1, _region2, _region3);
                    _currentModelsList.add(_Model);
                    editionsHashMap.put(_Model, new ArrayList<CarEdition>());
                    componentsHashMap.put(_Model, new ArrayList<CarComponent>());
                    equipmentsHashMap.put(_Model, new ArrayList<CarComponent>());
                    yearFactorsHashMap.put(_Model, new ArrayList<CarFactor>());
                }
// --------------------------------инициализируем выпуск---------------------------------
                ArrayList<CarEdition> _currentEditionsList;
                _currentEditionsList = editionsHashMap.get(_Model);
                CarEdition _Edition = new CarEdition(++_idEdition, _Model, _Brand, _year, _volume, _price);
                _currentEditionsList.add(_Edition);
//------------------------------------------------------------------------------
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Parcer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void UpdateDatabaseAfterParce(){
        
    }
    public static void main(String[] args) {
        Parcer p = new Parcer("/home/afterthegame", null);
        p.startParcer();
//        p.BrandModelEditionBodyCategoryRegionWearRepairParcer();
//        Sort.SortModel(p.getArrayBrandModelEditionBodys());
//            p.Print();
    }
}
