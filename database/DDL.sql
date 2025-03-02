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
CREATE TABLE Administrador (
    AdminID BIGINT,
    Nombre VARCHAR(50),
    ApPaterno VARCHAR(50),
    ApMaterno VARCHAR(50),
    Password VARCHAR(50)
);


-- Tabla para almacenar información de los clientes
CREATE TABLE Cliente (
    ClienteID BIGINT,
    Nombre VARCHAR(100),
    ApPaterno VARCHAR(100),
    ApMaterno VARCHAR(100),
    Correo VARCHAR(50),
    Password VARCHAR(50)
);

-- Tabla para almacenar categorías de incidentes
CREATE TABLE Categoria (
    CategoriaID BIGINT,
    Categoria VARCHAR(80)
);


-- Tabla para gestionar incidentes por administradores
CREATE TABLE Gestionar (
    IncidenteID BIGINT,
    AdminID BIGINT
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

-- RESTRICCIONES TABLA ADMINISTRADOR
ALTER TABLE Administrador ALTER COLUMN AdminID SET NOT NULL;
ALTER TABLE Administrador ADD CONSTRAINT administrador_d1 CHECK (Nombre <> '');
ALTER TABLE Administrador ADD CONSTRAINT administrador_d2 CHECK (ApPaterno <> '');

-- RESTRICCIONES TABLA CLIENTE 
ALTER TABLE Cliente ALTER COLUMN ClienteID SET NOT NULL;
ALTER TABLE Cliente ADD CONSTRAINT cliente_d1 CHECK (Nombre <> '');
ALTER TABLE Cliente ADD CONSTRAINT cliente_d2 CHECK (ApPaterno <> '');
ALTER TABLE Cliente ADD CONSTRAINT cliente_d3 CHECK (Correo like '%_@_%._%');

-- RESTRICCIONES TABLA CATEGORIA
ALTER TABLE Categoria ALTER COLUMN CategoriaID SET NOT NULL;
ALTER TABLE Categoria ADD CONSTRAINT categoria_d1 CHECK (Categoria <> '');

-- RESTRICCIONES TABLA GESTIONAR
ALTER TABLE Gestionar ALTER COLUMN IncidenteID SET NOT NULL;
ALTER TABLE Gestionar ALTER COLUMN AdminID SET NOT NULL;


---- LLAVES PRIMARIAS ----

-- LLAVES PRIMARIAS INCIDENTE
ALTER TABLE Incidente ADD CONSTRAINT pk_incidente PRIMARY KEY (IncidenteID);

-- LLAVES PRIMARIAS TABLA FOTO
ALTER TABLE Foto ADD CONSTRAINT pk_foto PRIMARY KEY (FotoID, IncidenteID);

-- LLAVES PRIMARIAS ADMINISTRADOR
ALTER TABLE Administrador ADD CONSTRAINT pk_admin PRIMARY KEY (AdminID);

-- LLAVES PRIMARIAS CLIENTE
ALTER TABLE Cliente ADD CONSTRAINT pk_cliente PRIMARY KEY (ClienteID);

-- LLAVES PRIMARIAS CATEGORIA
ALTER TABLE Categoria ADD CONSTRAINT pk_categoria PRIMARY KEY (CategoriaID);



---- LLAVES FORANEAS ----

-- LLAVES FORANEAS INCIDENTE
ALTER TABLE Incidente ADD CONSTRAINT fk_incidente_categoria FOREIGN KEY (CategoriaID) REFERENCES Categoria(CategoriaID)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE Incidente ADD CONSTRAINT fk_incidente_cliente FOREIGN KEY (ClienteID) REFERENCES Cliente(ClienteID)
    ON DELETE CASCADE ON UPDATE CASCADE;   
   
--LLAVES FORANEAS FOTO
ALTER TABLE Foto ADD CONSTRAINT fk_foto_incidente FOREIGN KEY (IncidenteID) REFERENCES Incidente(IncidenteID)
    ON DELETE CASCADE ON UPDATE CASCADE;  

-- LLAVES FORANEAS GESTIONAR
ALTER TABLE Gestionar ADD CONSTRAINT fk_admin_gestionar FOREIGN KEY (AdminID) REFERENCES Administrador(AdminID)
    ON DELETE CASCADE ON UPDATE CASCADE;  
ALTER TABLE Gestionar ADD CONSTRAINT fk_incidente_gestionar FOREIGN KEY (IncidenteID) REFERENCES Incidente(IncidenteID)
    ON DELETE CASCADE ON UPDATE CASCADE;   

