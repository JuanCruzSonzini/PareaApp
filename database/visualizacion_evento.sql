-- Tabla visualizacion_evento
-- Registra cada vez que alguien ve el detalle de un evento (para las
-- estadisticas de visualizacion). Depende de evento y usuario.

CREATE TABLE visualizacion_evento (
    id_visualizacion BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_evento        BIGINT      NOT NULL REFERENCES evento(id_evento) ON DELETE CASCADE,
    -- Si se borra el evento, no tiene sentido conservar el historial
    -- de visualizaciones de algo que ya no existe.

    id_usuario       BIGINT      REFERENCES usuario(id_usuario),
    -- Sin NOT NULL a proposito: id_usuario en NULL representa una visita
    -- anonima (alguien que mira el evento sin haber iniciado sesion).

    fh_visualizacion TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Datos de prueba: una visualizacion identificada y una anonima,
-- ambas sobre el evento que ya cargamos en evento.sql
INSERT INTO visualizacion_evento (id_evento, id_usuario, fh_visualizacion)
SELECT e.id_evento, u.id_usuario, now()
  FROM evento e, usuario u
 WHERE e.titulo = 'Jornada de tecnologia UTN' AND u.email = 'agustina@parea.test';

INSERT INTO visualizacion_evento (id_evento, id_usuario, fh_visualizacion)
SELECT e.id_evento, NULL, now()
  FROM evento e
 WHERE e.titulo = 'Jornada de tecnologia UTN';
