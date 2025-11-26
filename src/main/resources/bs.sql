create database DriveX;
Use DriveX;
Create Table vehicles(
                         id int primary key auto_increment,
                         reference varchar(50) not null,
                         brand ENUM(
    -- Coches de lujo y deportivos
    'Ferrari','Lamborghini','Porsche','Maserati','Bentley','Rolls-Royce','McLaren','Pagani','Koenigsegg','Lotus','Aston Martin','Bugatti',
    -- Coches generalistas
    'Audi','BMW','Mercedes','Volkswagen','Opel','Renault','Peugeot','Citroen','Ford','Toyota','Honda','Nissan','Mazda','Subaru','Hyundai','Kia','Fiat','Seat','Skoda','Dacia','Alfa Romeo','Jaguar','Land Rover','Chevrolet','Chrysler','Jeep','GMC','Cadillac','Mini','Infiniti','Acura','Buick','Lincoln','Tesla','Mitsubishi','Suzuki',
    -- Marcas de motos
    'Yamaha','Kawasaki','Ducati','KTM','Triumph','Harley-Davidson','Indian','Royal Enfield','Aprilia','Moto Guzzi','Bajaj','Victory','Can-Am','Zero','BMW Motorrad','CFMOTO','Sherco','SYM','Malaguti','Beta','Gas Gas','Husaberg','Kymco','Rieju','Montesa','Ossa','Bimota','Cagiva','Laverda','Vespa','Italjet','Mondial','Peugeot Scooters','Aprilia Moto','MBK','KTM RC','Beta Motorcycles','Zero Motorcycles',
    -- Otras marcas de coches exóticas o menos comunes
    'MG','Smart','Alpine','DS','Plymouth','Saab','Scion','Daihatsu','Holden','Tata','Proton','Perodua','Lada','Geely','Chery','Great Wall','Mahindra','Zotye','Fisker','Rimac','Lucid','Polestar','BYD','Nio','XPeng',
    -- Marcas clásicas o históricas
    'Pontiac','Oldsmobile','Packard','Studebaker','Hummer','Delorean','Vector','Singer','Facel Vega','TVR','Bristol','Morgan','Caterham','Ginetta','Rover','Austin','Hillman','Sunbeam','Talbot','Simca','Datsun'
),
model varchar(20),
hp int,
autonomy int,
average_consumption REAL,
description varchar(200),
price REAL,
year Int,
mileage BIGINT,
fuel_type varchar(20),
extras varchar(500),
created_at timestamp,
updated_at timestamp

);

ALTER TABLE vehicles
    MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;

CREATE TABLE vehicle_images (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                vehicle_id INT NOT NULL,
                                image_url VARCHAR(255) NOT NULL,
                                is_main BOOLEAN DEFAULT FALSE,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

create table users(
                      id integer primary key auto_increment,
                      username varchar(20),
                      email varchar(50),
                      password_hash varchar(255),
                      first_name varchar(20),
                      last_name varchar(50),
                      phone_number varchar(15),
                      is_active boolean,
                      created_at timestamp,
                      updated_at timestamp,
                      role varchar(20)
);

create table transactions(
                             id integer primary key auto_increment,
                             vehicle_id int,
                             buyer_id int,
                             seller_id int,
                             transaction_type varchar(20),
                             amount decimal(8,2),
                             order_status ENUM('pending', 'paid', 'shipped', 'delivered', 'cancelled'),
                             transaction_date timestamp,
                             rental_start_date date,
                             rental_end_data date
);

INSERT INTO vehicles (reference, brand, model, description, price, year, fuel_type, created_at, updated_at) VALUES
-- 1–10
('REF001','Ferrari','488 GTB','Superdeportivo V8 biturbo',245000,2020,'Gasolina',NOW(),NOW()),
('REF002','Lamborghini','Huracán','Deportivo V10 de altas prestaciones',260000,2019,'Gasolina',NOW(),NOW()),
('REF003','Porsche','911 Carrera','Icono deportivo alemán',120000,2021,'Gasolina',NOW(),NOW()),
('REF004','Maserati','Ghibli','Berlina deportiva de lujo',68000,2018,'Gasolina',NOW(),NOW()),
('REF005','Bentley','Continental GT','Coupé de lujo con motor W12',210000,2017,'Gasolina',NOW(),NOW()),
('REF006','Rolls-Royce','Ghost','Berlina de superlujo',300000,2022,'Gasolina',NOW(),NOW()),
('REF007','McLaren','720S','Superdeportivo británico extremo',285000,2020,'Gasolina',NOW(),NOW()),
('REF008','Pagani','Huayra','Superdeportivo artesanal italiano',1200000,2016,'Gasolina',NOW(),NOW()),
('REF009','Koenigsegg','Jesko','Hiperauto sueco de 1600 CV',2800000,2023,'Gasolina',NOW(),NOW()),
('REF010','Lotus','Elise','Deportivo ligero de motor central',52000,2019,'Gasolina',NOW(),NOW()),

-- 11–20
('REF011','Aston Martin','DB11','Gran turismo británico',185000,2018,'Gasolina',NOW(),NOW()),
('REF012','Bugatti','Chiron','Hiperauto de 1500CV',2500000,2020,'Gasolina',NOW(),NOW()),
('REF013','Audi','A4','Berlina premium alemana',35000,2021,'Gasolina',NOW(),NOW()),
('REF014','BMW','320d','Berlina diésel eficiente',34000,2018,'Diésel',NOW(),NOW()),
('REF015','Mercedes','C200','Berlina premium alemana',37000,2020,'Gasolina',NOW(),NOW()),
('REF016','Volkswagen','Golf','Compacto alemán icónico',28000,2022,'Gasolina',NOW(),NOW()),
('REF017','Opel','Astra','Compacto europeo',21000,2019,'Gasolina',NOW(),NOW()),
('REF018','Renault','Clio','Utilitario francés',16000,2020,'Gasolina',NOW(),NOW()),
('REF019','Peugeot','308','Compacto moderno',19000,2021,'Diésel',NOW(),NOW()),
('REF020','Citroen','C4','Compacto cómodo y versátil',20000,2022,'Gasolina',NOW(),NOW()),

-- 21–30
('REF021','Ford','Focus','Compacto americano',22000,2019,'Gasolina',NOW(),NOW()),
('REF022','Toyota','Corolla','Compacto fiable',23000,2022,'Híbrido',NOW(),NOW()),
('REF023','Honda','Civic','Compacto japonés deportivo',24000,2020,'Gasolina',NOW(),NOW()),
('REF024','Nissan','Qashqai','SUV superventas',27000,2021,'Gasolina',NOW(),NOW()),
('REF025','Mazda','CX-5','SUV japonés premium',32000,2020,'Gasolina',NOW(),NOW()),
('REF026','Subaru','Impreza','Compacto AWD',26000,2019,'Gasolina',NOW(),NOW()),
('REF027','Hyundai','Tucson','SUV moderno',28000,2022,'Híbrido',NOW(),NOW()),
('REF028','Kia','Sportage','SUV práctico',27000,2021,'Gasolina',NOW(),NOW()),
('REF029','Fiat','500','Ciudadano icónico',15000,2022,'Gasolina',NOW(),NOW()),
('REF030','Seat','León','Compacto español',23000,2020,'Gasolina',NOW(),NOW()),

-- 31–40
('REF031','Skoda','Octavia','Berlina espaciosa',26000,2021,'Diésel',NOW(),NOW()),
('REF032','Dacia','Duster','SUV económico muy vendido',17000,2023,'Gasolina',NOW(),NOW()),
('REF033','Alfa Romeo','Giulia','Berlina deportiva italiana',42000,2019,'Gasolina',NOW(),NOW()),
('REF034','Jaguar','XE','Berlina británica premium',45000,2020,'Gasolina',NOW(),NOW()),
('REF035','Land Rover','Discovery','SUV todoterreno de lujo',65000,2021,'Diésel',NOW(),NOW()),
('REF036','Chevrolet','Camaro','Muscle car americano',48000,2018,'Gasolina',NOW(),NOW()),
('REF037','Jeep','Wrangler','SUV auténtico 4x4',45000,2022,'Gasolina',NOW(),NOW()),
('REF038','Cadillac','Escalade','SUV americano de lujo',90000,2021,'Gasolina',NOW(),NOW()),
('REF039','Mini','Cooper S','Compacto premium británico',28000,2022,'Gasolina',NOW(),NOW()),
('REF040','Tesla','Model 3','Eléctrico superventas',45000,2023,'Eléctrico',NOW(),NOW()),

-- 41–50
('REF041','Mitsubishi','Outlander','SUV híbrido enchufable',35000,2020,'Híbrido',NOW(),NOW()),
('REF042','Suzuki','Swift','Utilitario japonés',16000,2021,'Gasolina',NOW(),NOW()),
('REF043','Yamaha','R1','Superbike japonesa',20000,2020,'Gasolina',NOW(),NOW()),
('REF044','Kawasaki','Ninja 650','Deportiva polivalente',9000,2022,'Gasolina',NOW(),NOW()),
('REF045','Ducati','Panigale V4','Superbike italiana',26000,2021,'Gasolina',NOW(),NOW()),
('REF046','KTM','Duke 390','Naked ligera y moderna',6200,2022,'Gasolina',NOW(),NOW()),
('REF047','Triumph','Street Triple','Naked británica',12000,2020,'Gasolina',NOW(),NOW()),
('REF048','Harley-Davidson','Sportster','Custom icónica americana',15000,2019,'Gasolina',NOW(),NOW()),
('REF049','Indian','Scout','Custom americana',16000,2021,'Gasolina',NOW(),NOW()),
('REF050','Royal Enfield','Interceptor','Clásica retro',6500,2022,'Gasolina',NOW(),NOW()),

-- 51–60
('REF051','Aprilia','RS 660','Deportiva italiana moderna',11500,2021,'Gasolina',NOW(),NOW()),
('REF052','Moto Guzzi','V7','Clásica italiana',9500,2020,'Gasolina',NOW(),NOW()),
('REF053','CFMOTO','800MT','Trail moderna',9500,2023,'Gasolina',NOW(),NOW()),
('REF054','Sherco','SE Factory','Enduro de competición',10500,2022,'Gasolina',NOW(),NOW()),
('REF055','SYM','Joymax 300','Scooter GT',5200,2023,'Gasolina',NOW(),NOW()),
('REF056','Malaguti','Mission 200','Scooter urbano',3000,2022,'Gasolina',NOW(),NOW()),
('REF057','Beta','RR 300','Enduro italiana',9900,2021,'Gasolina',NOW(),NOW()),
('REF058','Gas Gas','EC 250','Enduro española',9400,2021,'Gasolina',NOW(),NOW()),
('REF059','Kymco','AK 550','Maxi scooter',9800,2023,'Gasolina',NOW(),NOW()),
('REF060','Rieju','MR 300','Enduro española',9000,2022,'Gasolina',NOW(),NOW()),

-- 61–70
('REF061','Montesa','4Ride','Trial-trail española',6900,2021,'Gasolina',NOW(),NOW()),
('REF062','Ossa','Explorer','Moto clásica española',4500,1979,'Gasolina',NOW(),NOW()),
('REF063','Bimota','Tesi H2','Superdeportiva exclusiva',65000,2022,'Gasolina',NOW(),NOW()),
('REF064','Cagiva','Mito 125','Clásica deportiva italiana',3000,1998,'Gasolina',NOW(),NOW()),
('REF065','Vespa','GTS 300','Scooter retro italiano',5800,2023,'Gasolina',NOW(),NOW()),
('REF066','Mondial','Pagani 1948','Retro deportiva italiana',6100,2021,'Gasolina',NOW(),NOW()),
('REF067','MG','ZS EV','SUV eléctrico asequible',32000,2022,'Eléctrico',NOW(),NOW()),
('REF068','Smart','ForTwo','Microcoche urbano eléctrico',19000,2020,'Eléctrico',NOW(),NOW()),
('REF069','Alpine','A110','Deportivo francés ligero',68000,2021,'Gasolina',NOW(),NOW()),
('REF070','DS','DS7 Crossback','SUV premium francés',42000,2022,'Híbrido',NOW(),NOW()),

-- 71–80
('REF071','Plymouth','Barracuda','Muscle car clásico',55000,1971,'Gasolina',NOW(),NOW()),
('REF072','Saab','900','Clásico sueco turbo',9000,1994,'Gasolina',NOW(),NOW()),
('REF073','Daihatsu','Terios','SUV compacto japonés',8000,2008,'Gasolina',NOW(),NOW()),
('REF074','Holden','Commodore','Berlina australiana',15000,2015,'Gasolina',NOW(),NOW()),
('REF075','Tata','Harrier','SUV indio moderno',22000,2022,'Diésel',NOW(),NOW()),
('REF076','Proton','Persona','Compacto malayo',13000,2020,'Gasolina',NOW(),NOW()),
('REF077','Lada','Niva','4x4 ruso clásico',11000,2021,'Gasolina',NOW(),NOW()),
('REF078','Geely','Coolray','SUV chino moderno',19000,2023,'Gasolina',NOW(),NOW()),
('REF079','Chery','Tiggo 7','SUV chino',21000,2022,'Gasolina',NOW(),NOW()),
('REF080','Great Wall','Poer','Pick-up china',26000,2023,'Diésel',NOW(),NOW()),

-- 81–90
('REF081','Mahindra','XUV700','SUV indio moderno',25000,2022,'Gasolina',NOW(),NOW()),
('REF082','Fisker','Ocean','SUV eléctrico',42000,2023,'Eléctrico',NOW(),NOW()),
('REF083','Rimac','Nevera','Hiperauto eléctrico croata',2000000,2022,'Eléctrico',NOW(),NOW()),
('REF084','Lucid','Air','Berlina eléctrica premium',95000,2023,'Eléctrico',NOW(),NOW()),
('REF085','Polestar','2','Berlina eléctrica sueca',48000,2022,'Eléctrico',NOW(),NOW()),
('REF086','BYD','Han EV','Berlina eléctrica china',36000,2023,'Eléctrico',NOW(),NOW()),
('REF087','Nio','ET7','Berlina eléctrica china',60000,2023,'Eléctrico',NOW(),NOW()),
('REF088','XPeng','P7','Berlina eléctrica china',52000,2023,'Eléctrico',NOW(),NOW()),
('REF089','Pontiac','Firebird','Muscle clásico americano',38000,1979,'Gasolina',NOW(),NOW()),
('REF090','Oldsmobile','Cutlass','Clásico americano',14000,1985,'Gasolina',NOW(),NOW()),

-- 91–100
('REF091','Packard','Clipper','Clásico americano de lujo',30000,1955,'Gasolina',NOW(),NOW()),
('REF092','Studebaker','Commander','Coche clásico americano',25000,1953,'Gasolina',NOW(),NOW()),
('REF093','Hummer','H2','SUV americano gigante',42000,2006,'Gasolina',NOW(),NOW()),
('REF094','Delorean','DMC-12','Coupé icónico de acero',50000,1982,'Gasolina',NOW(),NOW()),
('REF095','Morgan','Plus Four','Roadster británico artesanal',84000,2021,'Gasolina',NOW(),NOW()),
('REF096','Caterham','Seven 420R','Deportivo ultraligero',62000,2022,'Gasolina',NOW(),NOW()),
('REF097','Ginetta','G40','Coupé británico deportivo',45000,2019,'Gasolina',NOW(),NOW()),
('REF098','Rover','75','Berlina británica clásica',6000,2004,'Gasolina',NOW(),NOW()),
('REF099','Austin','Mini Cooper','Clásico británico original',12000,1985,'Gasolina',NOW(),NOW()),
('REF100','Simca','1000','Clásico francés compacto',5000,1970,'Gasolina',NOW(),NOW());







select * from vehicles;
 drop table vehicles;
drop table vehicle_images;
update users set role = "Admin" where id = 2
2
ALTER TABLE vehicles CHANGE COLUMN `range` autonomy INT;
ALTER TABLE vehicles
    ADD COLUMN average_consumption REAL;
insert into vehicles(id, reference,brand,model) values(2, "4242C", "Ford", "Raptor")
insert into users(username, email) values("Marcos", "marcos@gmail.com")