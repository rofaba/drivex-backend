create database DriveX;
Use DriveX;
Create Table vehicles(
                         id integer primary key auto_increment,
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
description varchar(200),
price decimal(8,2),
year Int,
fuel_type enum('Gas', 'Electric', 'Hybrid', 'diesel', 'GLP'),
created_at timestamp,
updated_at timestamp

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
                      role enum('Admin', 'user')
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


select * from Vehicle