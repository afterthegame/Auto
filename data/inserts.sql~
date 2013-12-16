INSERT INTO brands (name) VALUES ("BMW");
INSERT INTO brands (name) VALUES ("Ford");

INSERT INTO models (name, brand_id, wear_factor, year_factor, region_factor) VALUES ("M3", 1, 1, 1, 1);
INSERT INTO models (name, brand_id, wear_factor, year_factor, region_factor) VALUES ("M5", 1, 2, 2, 1);
INSERT INTO models (name, brand_id, wear_factor, year_factor, region_factor) VALUES ("Focus", 2, 3, 3, 1);
INSERT INTO models (name, brand_id, wear_factor, year_factor, region_factor) VALUES ("Transit", 2, 4, 4, 1);

INSERT INTO editions (year, volume, price, model_id, body_id) VALUES (2010, 2.66, 2000.00, 1, 1);
INSERT INTO editions (year, volume, price, model_id, body_id) VALUES (2011, 3.2, 10000.00, 1, 2);
INSERT INTO editions (year, volume, price, model_id, body_id) VALUES (2012, 2.66, 3000.00, 2, 1);
INSERT INTO editions (year, volume, price, model_id, body_id) VALUES (2012, 3.6, 8000.00, 3, 2);
INSERT INTO editions (year, volume, price, model_id, body_id) VALUES (2011, 2.66, 9000.00, 3, 1);
INSERT INTO editions (year, volume, price, model_id, body_id) VALUES (2010, 3.66, 15000.00, 4, 4);

INSERT INTO bodies(name) VALUES ("Седан");
INSERT INTO bodies(name) VALUES ("Хетчбек");
INSERT INTO bodies(name) VALUES ("Универсал");
INSERT INTO bodies(name) VALUES ("Мнивэн");

INSERT INTO wear_factors(val) VALUES (0.95);
INSERT INTO wear_factors(val) VALUES (0.9);
INSERT INTO wear_factors(val) VALUES (0.85);
INSERT INTO wear_factors(val) VALUES (0.8);

INSERT INTO year_factors(id, year, val) VALUES (1, 1, 0.95);
INSERT INTO year_factors(id, year, val) VALUES (1, 2, 0.90);
INSERT INTO year_factors(id, year, val) VALUES (1, 3, 0.85);
INSERT INTO year_factors(id, year, val) VALUES (2, 1, 0.92);
INSERT INTO year_factors(id, year, val) VALUES (2, 2, 0.9);
INSERT INTO year_factors(id, year, val) VALUES (2, 3, 0.8);
INSERT INTO year_factors(id, year, val) VALUES (3, 1, 0.99);
INSERT INTO year_factors(id, year, val) VALUES (3, 2, 0.95);
INSERT INTO year_factors(id, year, val) VALUES (3, 3, 0.9);
INSERT INTO year_factors(id, year, val) VALUES (4, 1, 0.93);
INSERT INTO year_factors(id, year, val) VALUES (4, 2, 0.87);
INSERT INTO year_factors(id, year, val) VALUES (4, 3, 0.8);

INSERT INTO region_factors(low, high, another) VALUES (0.95, 1.05, 1.0);

INSERT INTO mileages(year, val) VALUES (1, 50);
INSERT INTO mileages(year, val) VALUES (2, 100);
INSERT INTO mileages(year, val) VALUES (3, 180);
INSERT INTO mileages(year, val) VALUES (4, 250);
INSERT INTO mileages(year, val) VALUES (5, 400);

INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 10, 3, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 20, 8, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 30, 13, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 40, 17, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 50, 20, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 10, 3, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 20, 7, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 30, 10, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 40, 13, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 50, 15, 0);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 10, 2, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 20, 6, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 30, 9, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 40, 12, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (2.8, 50, 14, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 10, 2, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 20, 6, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 30, 9, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 40, 12, 1);
INSERT INTO mileage_factors(mass, difference, val, direction) VALUES (5, 50, 14, 1);

INSERT INTO usage_conditions(name, val) VALUES ("Отсутсвие следов аварийных повреждений и перекраски кузова", 5.0);
INSERT INTO usage_conditions(name, val) VALUES ("Выполнен капитальный ремонт двигателя", 5.0);
INSERT INTO usage_conditions(name, val) VALUES ("Эксплуатация в режиме такси", -15.0);
INSERT INTO usage_conditions(name, val) VALUES ("Покрашено в цвет, который не пользуется спросом", -1.5);

INSERT INTO damages(name, val) VALUES ("Повреждена корозией панель пола кузова", 4.0);
INSERT INTO damages(name, val) VALUES ("Повреждена обивка сидений", 2.0);
INSERT INTO damages(name, val) VALUES ("Корозия хромированых покрытий", 3.0);
INSERT INTO damages(name, val) VALUES ("Повреждены стекла", 0.5);

INSERT INTO price_factors(year, mass, val) VALUES (1, 2.8, 80);
INSERT INTO price_factors(year, mass, val) VALUES (1, 5, 75);
INSERT INTO price_factors(year, mass, val) VALUES (2, 2.8, 65);
INSERT INTO price_factors(year, mass, val) VALUES (2, 5, 61);
INSERT INTO price_factors(year, mass, val) VALUES (3, 2.8, 55);
INSERT INTO price_factors(year, mass, val) VALUES (3, 5, 52);

INSERT INTO components(name, price, model_id) VALUES ("Боковое зеркало", 100, 1);
INSERT INTO components(name, price, model_id) VALUES ("Руль", 200, 1);
INSERT INTO components(name, price, model_id) VALUES ("Ремень безопасности", 300, 1);

INSERT INTO equipments(name, price, model_id) VALUES ("Аккамулятор", 500, 1);
INSERT INTO equipments(name, price, model_id) VALUES ("Система обогрева", 800, 1);
INSERT INTO equipments(name, price, model_id) VALUES ("Мотор", 1000, 1);

INSERT INTO repair_cost(val, model_id) VALUES (10, 1);

INSERT INTO materials(name, price) VALUES ("Краска", 100);
INSERT INTO materials(name, price) VALUES ("Грунт", 100);
INSERT INTO materials(name, price) VALUES ("Шпаклевка", 100);
INSERT INTO materials(name, price) VALUES ("Карбон", 100);
INSERT INTO materials(name, price) VALUES ("Пластик", 100);

INSERT INTO categories(name, flag) VALUES ("Легковой автомобиль", TRUE);
INSERT INTO categories(name, flag) VALUES ("Грузовой автомобить", FALSE);
INSERT INTO categories(name, flag) VALUES ("Автобус", TRUE);
INSERT INTO categories(name, flag) VALUES ("Мотоцикл", FALSE);

INSERT INTO X(A, B, month, val)  VALUES (0.2, 0, 12, 3.0);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0.5, 12, 3.2);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0.7, 12, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 1.0, 12, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 1.3, 12, 4.5);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0, 24, 2.75);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0.5, 24, 3.0);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0.7, 24, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 1.0, 24, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 1.3, 24, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0, 36, 2.5);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0.5, 36, 2.75);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 0.7, 36, 3.0);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 1.0, 36, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.2, 1.3, 36, 3.5);

INSERT INTO X(A, B, month, val)  VALUES (0.33, 0, 12, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0.5, 12, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0.7, 12, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 1.0, 12, 4.25);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 1.3, 12, 4.75);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0, 24, 3.0);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0.5, 24, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0.7, 24, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 1.0, 24, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 1.3, 24, 4.25);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0, 36, 2.75);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0.5, 36, 3.0);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 0.7, 36, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 1.0, 36, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.33, 1.3, 36, 4.0);

INSERT INTO X(A, B, month, val)  VALUES (0.45, 0, 12, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0.5, 12, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0.7, 12, 4.25);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 1.0, 12, 4.5);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 1.3, 12, 5.0);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0, 24, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0.5, 24, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0.7, 24, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 1.0, 24, 4.25);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 1.3, 24, 4.5);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0, 36, 3.0);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0.5, 36, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 0.7, 36, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 1.0, 36, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (0.45, 1.3, 36, 4.25);

INSERT INTO X(A, B, month, val)  VALUES (0.65, 0, 12, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0.5, 12, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0.7, 12, 4.5);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 1.0, 12, 4.75);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 1.3, 12, 5.25);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0, 24, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0.5, 24, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0.7, 24, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 1.0, 24, 4.5);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 1.3, 24, 4.75);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0, 36, 3.25);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0.5, 36, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 0.7, 36, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 1.0, 36, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (0.65, 1.3, 36, 4.5);

INSERT INTO X(A, B, month, val)  VALUES (1, 0, 12, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (1, 0.5, 12, 4.25);
INSERT INTO X(A, B, month, val)  VALUES (1, 0.7, 12, 4.75);
INSERT INTO X(A, B, month, val)  VALUES (1, 1.0, 12, 5.0);
INSERT INTO X(A, B, month, val)  VALUES (1, 1.3, 12, 5.5);
INSERT INTO X(A, B, month, val)  VALUES (1, 0, 24, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (1, 0.5, 24, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (1, 0.7, 24, 4.25);
INSERT INTO X(A, B, month, val)  VALUES (1, 1.0, 24, 4.75);
INSERT INTO X(A, B, month, val)  VALUES (1, 1.3, 24, 5.0);
INSERT INTO X(A, B, month, val)  VALUES (1, 0, 36, 3.5);
INSERT INTO X(A, B, month, val)  VALUES (1, 0.5, 36, 3.75);
INSERT INTO X(A, B, month, val)  VALUES (1, 0.7, 36, 4.0);
INSERT INTO X(A, B, month, val)  VALUES (1, 1.0, 36, 4.25);
INSERT INTO X(A, B, month, val)  VALUES (1, 1.3, 36, 4.75);

