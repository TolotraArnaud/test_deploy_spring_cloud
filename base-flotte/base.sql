create database flottevehicule;
create user flottevehicule with password 'flottevehicule';
grant all privileges on database flottevehicule to flottevehicule;
\c flottevehicule flottevehicule;

create table UserStatus(
    id serial primary key,
    description varchar(100)
);
insert into UserStatus(description) values ('readonly');
insert into UserStatus(description) values ('allaccess');

create table Utilisateur(
    id serial primary key,
    nom varchar(100),
    identifiant varchar(100) unique,
    mdp varchar(100),
    idUserStatus int references UserStatus(id)
);
insert into Utilisateur(nom, identifiant, mdp, idUserStatus) values ('Rakoto', 'rakoto@gmail.com', 'rakoto123', 1);
insert into Utilisateur(nom, identifiant, mdp, idUserStatus) values ('Randria', 'randria@gmail.com', 'randria123', 1);
insert into Utilisateur(nom, identifiant, mdp, idUserStatus) values ('Rabe', 'rabe@gmail.com', 'rabe123', 1);

create table Marque(
    id serial primary key,
    nom varchar(100)
);
insert into Marque(nom) values ('Toyota');
insert into Marque(nom) values ('Hyundai');
insert into Marque(nom) values ('Haval');
insert into Marque(nom) values ('Chevrolet');
insert into Marque(nom) values ('Mazda');

create table Modele(
    id serial primary key,
    idMarque int references Marque(id),
    nom varchar(100)
);
insert into Modele(idMarque, nom) values (1, 'Yarris');
insert into Modele(idMarque, nom) values (1, 'Corolla');
insert into Modele(idMarque, nom) values (1, 'Mirai');

insert into Modele(idMarque, nom) values (2, 'Ioniq');
insert into Modele(idMarque, nom) values (2, 'Kona');

insert into Modele(idMarque, nom) values (3, 'Dargo');
insert into Modele(idMarque, nom) values (3, 'Jolion');

insert into Modele(idMarque, nom) values (4, 'Alero');
insert into Modele(idMarque, nom) values (4, 'Apache');
insert into Modele(idMarque, nom) values (4, 'Avalanche');
insert into Modele(idMarque, nom) values (4, 'Aveo');

insert into Modele(idMarque, nom) values (5, 'Hatchback');
insert into Modele(idMarque, nom) values (5, 'Sedan');
insert into Modele(idMarque, nom) values (5, 'CX-5');
insert into Modele(idMarque, nom) values (5, 'CX-30');
insert into Modele(idMarque, nom) values (5, 'MX-5');

create table Transmission(
    id serial primary key,
    nom varchar(100)
);
insert into Transmission(nom) values ('traction');
insert into Transmission(nom) values ('propulsion');
insert into Transmission(nom) values ('integrale');

create table Vehicule(
    id serial primary key,
    date_fabrication date,
    couleur varchar(10),
    idModele int references Modele(id)
);
insert into vehicule(date_fabrication, couleur, idModele) values ('2022-10-13', 'rouge', 1);
insert into vehicule(date_fabrication, couleur, idModele) values ('2022-10-13', 'bleu', 1);
insert into vehicule(date_fabrication, couleur, idModele) values (now(), 'lavande', 2);
insert into vehicule(date_fabrication, couleur, idModele) values ('2021-11-23', 'noir', 3);
insert into vehicule(date_fabrication, couleur, idModele) values ('2020-08-08', 'bleu', 4);
insert into vehicule(date_fabrication, couleur, idModele) values ('2020-12-27', 'blanc', 5);
insert into vehicule(date_fabrication, couleur, idModele) values ('2019-04-03', 'vert', 6);
insert into vehicule(date_fabrication, couleur, idModele) values ('2022-06-17', 'jaune', 7);
insert into vehicule(date_fabrication, couleur, idModele) values ('2023-01-19', 'bleu', 8);
insert into vehicule(date_fabrication, couleur, idModele) values ('2021-02-18', 'noir', 9);
insert into vehicule(date_fabrication, couleur, idModele) values ('2021-05-14', 'bleu', 10);
insert into vehicule(date_fabrication, couleur, idModele) values ('2021-06-11', 'rouge', 11);
insert into vehicule(date_fabrication, couleur, idModele) values ('2022-06-25', 'orange', 12);
insert into vehicule(date_fabrication, couleur, idModele) values ('2022-11-27', 'noir', 13);
insert into vehicule(date_fabrication, couleur, idModele) values ('2018-02-02', 'noir', 14);

create table Kilometrage(
    id serial primary key,
    idVehicule int references Vehicule(id),
    debut double precision,
    fin double precision,
    date date
);
ALTER TABLE Kilometrage
ADD CONSTRAINT check_fin_sup_debut
CHECK (fin > debut);

CREATE OR REPLACE FUNCTION check_date_constraint()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.date <= (SELECT date_fabrication FROM Vehicule WHERE id = NEW.idVehicule) THEN
    RAISE EXCEPTION 'Date must be greater than the fabrication_date of the corresponding Vehicule';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_check_date
BEFORE INSERT ON Kilometrage
FOR EACH ROW EXECUTE FUNCTION check_date_constraint();

-- Create a function that checks the condition before insert
CREATE OR REPLACE FUNCTION check_km_condition()
RETURNS TRIGGER AS $$
BEGIN
  -- Check if there are existing records for the corresponding Vehicule
  IF EXISTS (
    SELECT 1
    FROM Kilometrage
    WHERE idVehicule = NEW.idVehicule
  ) THEN
    -- Check if begin_km is not equal to the last ending_km or the date is not equal to the last record's date
    IF NEW.debut <> (SELECT fin
                        FROM Kilometrage
                        WHERE idVehicule = NEW.idVehicule
                        ORDER BY date DESC
                        LIMIT 1)
        OR NEW.date <= (SELECT date
                        FROM Kilometrage
                        WHERE idVehicule = NEW.idVehicule
                        ORDER BY date DESC
                        LIMIT 1) THEN
      RAISE EXCEPTION 'debut must be equal to the last fin, and date must be greater than the last record date';
    END IF;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger to call the function before insert
CREATE TRIGGER check_km_trigger
BEFORE INSERT ON Kilometrage
FOR EACH ROW EXECUTE FUNCTION check_km_condition();

insert into Kilometrage(idVehicule, debut, fin, date) values (1, 0, 40, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (1, 40, 100, '2023-01-24');

insert into Kilometrage(idVehicule, debut, fin, date) values (1, 0, 40, '2023-01-22');

insert into Kilometrage(idVehicule, debut, fin, date) values (2, 20, 30, '2022-11-10');
insert into Kilometrage(idVehicule, debut, fin, date) values (4, 0, 5, '2022-02-07');
insert into Kilometrage(idVehicule, debut, fin, date) values (5, 0, 20, '2021-05-06');
insert into Kilometrage(idVehicule, debut, fin, date) values (6, 0, 14, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (7, 0, 18, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (8, 0, 19, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (9, 0, 13, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (10, 0, 8, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (11, 0, 2, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (12, 0, 6, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (12, 0, 13, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (13, 0, 22, '2023-01-22');
insert into Kilometrage(idVehicule, debut, fin, date) values (14, 0, 24, '2023-01-22');

