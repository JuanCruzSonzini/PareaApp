-- Tabla categoria
-- No depende de otras tablas.

CREATE TABLE categoria (
    id_categoria BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    -- GENERATED ALWAYS AS IDENTITY: Postgres genera el numero solo (1, 2, 3...),
    -- no hace falta que nosotros lo calculemos.

    nombre       CITEXT      NOT NULL UNIQUE,
    -- UNIQUE: no puede haber dos categorias con el mismo nombre.

    descripcion  VARCHAR(255),
    fh_alta      TIMESTAMPTZ NOT NULL DEFAULT now()
    -- DEFAULT now(): si no mandamos la fecha, Postgres pone el momento actual solo.
);

-- Cargamos unas categorias iniciales para poder probar despues.
INSERT INTO categoria (nombre, descripcion) VALUES
    ('Musica',      'Recitales, festivales y shows en vivo'),
    ('Teatro',      'Obras, stand up y artes escenicas'),
    ('Academico',   'Charlas, congresos, cursos y capacitaciones'),
    ('Tecnologia',  'Meetups, hackathons y eventos de tecnologia');
