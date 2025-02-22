DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

create table if not exists Usuario(
	Nombre varchar(30),
	Email varchar(30),
	password varchar(30),
	token varchar(30)
);