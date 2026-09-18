-- Tabla estado_evento
-- Catalogo de los estados posibles de un evento. No depende de otra tabla.
-- evento.id_estado_evento apunta aca, asi que este archivo tiene que
-- cargarse ANTES que evento.sql.

CREATE TABLE estado_evento (
    id_estado_evento     BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre               VARCHAR(20) NOT NULL UNIQUE,
    descripcion          VARCHAR(255),
    permite_inscripcion  BOOLEAN     NOT NULL DEFAULT FALSE
);

INSERT INTO estado_evento (nombre, descripcion, permite_inscripcion) VALUES
    ('borrador',   'El organizador todavia lo esta cargando, no es visible al publico.', FALSE),
    ('publicado',  'Visible al publico y acepta inscripciones.',                         TRUE),
    ('cancelado',  'El organizador dio de baja el evento.',                              FALSE),
    ('finalizado', 'El evento ya ocurrio.',                                              FALSE);
