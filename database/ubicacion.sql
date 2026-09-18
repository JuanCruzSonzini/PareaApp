-- Tabla ubicacion
-- No depende de otras tablas.
-- Sin CHECK: ver "Validaciones pendientes" en README.md.

CREATE TABLE ubicacion (
    id_ubicacion BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_lugar VARCHAR(150),
    calle        VARCHAR(150),
    numero       VARCHAR(15),
    barrio       VARCHAR(100),
    ciudad       VARCHAR(100) NOT NULL,
    provincia    VARCHAR(100) NOT NULL DEFAULT 'Cordoba',
    latitud      NUMERIC(9,6),
    longitud     NUMERIC(9,6)
);

INSERT INTO ubicacion (nombre_lugar, calle, numero, barrio, ciudad, latitud, longitud) VALUES
    ('Centro Cultural Cordoba', 'Av. Poeta Lugones', '401', 'Nueva Cordoba', 'Cordoba', -31.428500, -64.183900),
    ('UTN FRC', 'Maestro Marcelo Lopez', 'S/N', 'Ciudad Universitaria', 'Cordoba', -31.442800, -64.194700);
