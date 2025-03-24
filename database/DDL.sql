--Borrar base de datos para reinicio
drop schema public cascade;
create schema public;

-- Estados de un incidente
CREATE TYPE ESTADO AS ENUM('reportado','en revision','resuleto');

-- Tabla para almacenar incidentes
CREATE TABLE Incidente (
    IncidenteID BIGINT,
    ClienteID BIGINT,
    CategoriaID BIGINT,
    Nombre VARCHAR(50),
    Descripcion VARCHAR(80),
    Fecha DATE,
    Hora TIME,
    Longitud NUMERIC(10,8),
    Latitud NUMERIC(10,8),
    Estado ESTADO
);


-- Tabla de las fotos relacionadas a los incidentes
CREATE TABLE Foto (
    FotoID BYTEA,
    IncidenteID BIGINT
);

-- Tabla para almacenar usuarios administradores


-- Tabla para almacenar información de los clientes
CREATE TABLE Cliente (
    ClienteID BIGINT,
    Nombre VARCHAR(100),
    ApPaterno VARCHAR(100),
    ApMaterno VARCHAR(100),
    Correo VARCHAR(50),
    Password VARCHAR(50),
    Token VARCHAR(50)
);

-- Tabla para almacenar categorías de incidentes
CREATE TABLE Categoria (
    CategoriaID BIGINT,
    Categoria VARCHAR(80)
);


-- Tabla para gestionar incidentes por administradores
CREATE TABLE Gestionar (
    IncidenteID BIGINT,
    ClienteID BIGINT
);

CREATE TABLE Rol(
    RolID INT,
    Nombre varchar(45)
);

---- RESTRICCIONES ----

-- RESTRICCIONES TABLA INCIDENTE
ALTER TABLE Incidente ALTER COLUMN IncidenteID SET NOT NULL;
ALTER TABLE Incidente ALTER COLUMN CategoriaID SET NOT NULL;
ALTER TABLE Incidente ALTER COLUMN Longitud SET NOT NULL;
ALTER TABLE Incidente ALTER COLUMN Latitud SET NOT NULL;

-- RESTRICCIONES TABLA FOTO
ALTER TABLE Foto ALTER COLUMN IncidenteID SET NOT NULL;
ALTER TABLE Foto ALTER COLUMN FotoID SET NOT NULL;

--RESTRICCIONES TABLA ROL
ALTER TABLE Rol ALTER COLUMN RolID SET NOT NULL;
ALTER TABLE Rol ALTER COLUMN Nombre SET NOT NULL;

-- RESTRICCIONES TABLA CLIENTE
ALTER TABLE Cliente ALTER COLUMN ClienteID SET NOT NULL;
ALTER TABLE Cliente ADD CONSTRAINT cliente_d1 CHECK (Nombre <> '');
ALTER TABLE Cliente ADD CONSTRAINT cliente_d2 CHECK (ApPaterno <> '');
ALTER TABLE Cliente ADD CONSTRAINT cliente_d3 CHECK (ApMaterno <> '');
ALTER TABLE Cliente ADD CONSTRAINT cliente_d4 CHECK (Correo like '%_@_%._%');
-- RESTRICCIONES TABLA CATEGORIA
ALTER TABLE Categoria ALTER COLUMN CategoriaID SET NOT NULL;
ALTER TABLE Categoria ADD CONSTRAINT categoria_d1 CHECK (Categoria <> '');

-- RESTRICCIONES TABLA GESTIONAR
ALTER TABLE Gestionar ALTER COLUMN IncidenteID SET NOT NULL;
ALTER TABLE Gestionar ALTER COLUMN ClienteID SET NOT NULL;


---- LLAVES PRIMARIAS ----

-- LLAVES PRIMARIAS INCIDENTE
ALTER TABLE Incidente ADD CONSTRAINT pk_incidente PRIMARY KEY (IncidenteID);

-- LLAVES PRIMARIAS TABLA FOTO
ALTER TABLE Foto ADD CONSTRAINT pk_foto PRIMARY KEY (FotoID, IncidenteID);

-- LLAVES PRIMARIAS ROL
ALTER TABLE Rol ADD CONSTRAINT pk_rol PRIMARY KEY (RolID);

-- LLAVES PRIMARIAS CLIENTE
ALTER TABLE Cliente ADD CONSTRAINT pk_cliente PRIMARY KEY (ClienteID);

-- LLAVES PRIMARIAS CATEGORIA
ALTER TABLE Categoria ADD CONSTRAINT pk_categoria PRIMARY KEY (CategoriaID);



---- LLAVES FORANEAS ----

-- LLAVES FORANEAS INCIDENTE
ALTER TABLE Incidente ADD CONSTRAINT fk_incidente_cliente FOREIGN KEY (ClienteID) REFERENCES Cliente(ClienteID)
    ON DELETE CASCADE ON UPDATE CASCADE;

--LLAVES FORANEAS FOTO
ALTER TABLE Foto ADD CONSTRAINT fk_foto_incidente FOREIGN KEY (IncidenteID) REFERENCES Incidente(IncidenteID)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- LLAVES FORANEAS GESTIONAR
ALTER TABLE Gestionar ADD CONSTRAINT fk_admin_gestionar FOREIGN KEY (ClienteID) REFERENCES Cliente(ClienteID)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE Gestionar ADD CONSTRAINT fk_incidente_gestionar FOREIGN KEY (IncidenteID) REFERENCES Incidente(IncidenteID)
    ON DELETE CASCADE ON UPDATE CASCADE;

---- DOCUMENTACIÓN ----

COMMENT ON TYPE ESTADO IS 'Estados posibles de un Incidente';

-- Incidente
COMMENT ON TABLE Incidente IS 'Tabla que contiene los incidentes registrados.';
COMMENT ON COLUMN Incidente.IncidenteID IS 'ID unico para identificar los accidentes.';
COMMENT ON COLUMN Incidente.ClienteID IS 'Identificador unico proveniente del cliente.';
COMMENT ON COLUMN Incidente.CategoriaID IS 'Identificador unico proveniente de la categoria.';
COMMENT ON COLUMN Incidente.Nombre IS 'Nombre del incidente a registrar/ver.';
COMMENT ON COLUMN Incidente.Descripcion IS 'Descripcion del incidente a ver/registrar.';
COMMENT ON COLUMN Incidente.Fecha IS 'Fecha del incidente.';
COMMENT ON COLUMN Incidente.Hora IS 'Hora del incidente.';
COMMENT ON COLUMN Incidente.Longitud IS 'Coordenada geográfica de longitud del incidente.';
COMMENT ON COLUMN Incidente.Latitud IS 'Coordena geográfica de latitud del incidente.';
COMMENT ON COLUMN Incidente.Estado IS 'Estado del incidente [Reportado,En revision,Resuelto].';
COMMENT ON CONSTRAINT pk_incidente ON Incidente IS 'Llave primaria que identifica de manera única cada incidente en la tabla Incidente.';
--COMMENT ON CONSTRAINT fk_incidente_categoria ON Incidente IS 'Llave foránea que referencia la categoría del incidente.';
COMMENT ON CONSTRAINT fk_incidente_cliente ON Incidente IS 'Llave foránea que referencia el cliente asociado al incidente.';

-- Foto
COMMENT ON TABLE Foto IS 'Tabla que contiene las fotos del incidente.';
COMMENT ON COLUMN Foto.FotoID IS 'Identificador unico de la foto.';
COMMENT ON COLUMN Foto.IncidenteID IS 'Identificador unico proveniente del incidente.';
COMMENT ON CONSTRAINT pk_foto ON Foto IS 'Llave primaria que identifica una Foto.';
COMMENT ON CONSTRAINT fk_foto_incidente ON Foto IS 'Llave foránea que referencia el Incidente al que retrata o se le es relevante la Foto';

--Rol
COMMENT ON TABLE Rol IS 'Tabla que contiene los roles de los clientes'
COMMENT ON COLUMN Rol.RolID IS 'id unico del rol que se puede asignar'
COMMENT ON COLUMN Rol.Nombre IS 'nombre del rol que se puede asiganr'
COMMENT ON CONSTRAINT pk_rol on Rol IS 'Restriccions de llave primaria'

--Cliente
COMMENT ON TABLE Cliente IS 'Tabla que contiene los datos del cliente';
COMMENT ON COLUMN Cliente.ClienteID IS 'Identificador unico del cliente.';
COMMENT ON COLUMN Cliente.Nombre IS 'Nombre del cliente.';
COMMENT ON COLUMN Cliente.ApPaterno IS 'Apellido paterno del cliente.';
COMMENT ON COLUMN Cliente.ApMaterno IS 'Apellido materno del cliente.';
COMMENT ON COLUMN Cliente.Correo IS 'Correo registrado del cliente.';
COMMENT ON COLUMN Cliente.Password IS 'Passsword registrada del cliente.';
COMMENT ON CONSTRAINT pk_cliente ON Cliente IS 'Llave primaria que identifica de manera unica a cada cliente.';
COMMENT ON CONSTRAINT cliente_d1 ON Cliente IS 'Restriccion de longitud para la cadena recibida como Nombre, evitando cadena vacia';
COMMENT ON CONSTRAINT cliente_d2 ON Cliente IS 'Restriccion de longitud para la cadena recibida como Apellido Paterno, evitando cadena vacia';
COMMENT ON CONSTRAINT cliente_d3 ON Cliente IS 'Restriccion de longitud para la cadena recibida como Apellido Materno, evitando cadena vacia';
COMMENT ON CONSTRAINT cliente_d4 ON Cliente IS 'Restriccion de longitud para la cadena recibida como Correo, evitando cadena vacia';

-- Categoria
COMMENT ON TABLE Categoria IS 'Tabla quecontiene las categorias de un incidente';
COMMENT ON COLUMN Categoria.CategoriaID IS 'Identificador unico de la categoria';
COMMENT ON COLUMN Categoria.Categoria IS 'Nombre de la categoria misma';
COMMENT ON CONSTRAINT pk_categoria ON Categoria IS 'Llave primaria que identifica de manera unica cada categoria.';
COMMENT ON CONSTRAINT categoria_d1 ON Categoria IS 'Restriccion de longitud para la cadena recibida como Categoria, evitando cadena vacia';

-- Gestionar
COMMENT ON TABLE Gestionar IS 'Tabla que guarda las relaciones entre Incidentes y su gestión por adminisradores';
COMMENT ON COLUMN Gestionar.IncidenteID IS 'Identificador unico proveniente del incidente';
COMMENT ON COLUMN Gestionar.ClienteID IS 'Identificador unico proveniente del administrador';
COMMENT ON CONSTRAINT fk_admin_gestionar ON Gestionar IS 'Llave foránea que referencia';
COMMENT ON CONSTRAINT fk_incidente_gestionar ON Gestionar IS 'Llave foránea que referencia';

insert into categoria (categoriaid, categoria)
values(246832,'baches');
