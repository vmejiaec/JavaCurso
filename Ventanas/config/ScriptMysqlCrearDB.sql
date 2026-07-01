drop database  if exists farmacias;
-- Crear la base de farmacias
create database farmacias;

-- Apuntar a la base de farmacias
use farmacias;

-- Crear la tabla de medicamentos
create table medicamento (
   codigo char(5) primary key,
   nombre_comercial varchar(200) not null,
   tipo char(3),
   cantidad int,
   precio decimal(15,2),
   farmaceutica varchar(200)
);

-- Cargamos los datos iniciales

insert into medicamento values ('PAR23','Paracetamol','GEN',12,0.25,'La Sante');
insert into medicamento values ('ACT45','Acetaminofen','GEN',24,0.75,'Bayer');
insert into medicamento values ('FIN77','Finalin Forte','COM',12,0.85,'MK');
insert into medicamento values ('ASP21','Aspirina','COM',30,1.25,'Bayer');
insert into medicamento values ('CAS01','Cardio Aspirina','COM',7,0.95,'MK');








