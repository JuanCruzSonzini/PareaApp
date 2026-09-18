-- Tabla asistencia
-- Depende de inscripcion. Registra si la persona inscripta efectivamente
-- asistio al evento.

CREATE TABLE asistencia (
    id_asistencia      BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_inscripcion     BIGINT      NOT NULL REFERENCES inscripcion(id_inscripcion),

    estado_asistencia  VARCHAR(15) NOT NULL DEFAULT 'pendiente',
    fh_registro        TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT ux_asistencia_inscripcion UNIQUE (id_inscripcion)
        -- Cada inscripcion tiene COMO MAXIMO una fila de asistencia.
        -- Si la persona entra y sale varias veces, no se agregan filas:
        -- se actualiza estado_asistencia de la fila que ya existe.
);

-- Asistencia de prueba: Facundo asistio, Agustina todavia esta pendiente.
INSERT INTO asistencia (id_inscripcion, estado_asistencia, fh_registro)
SELECT i.id_inscripcion, 'presente', now()
  FROM inscripcion i
  JOIN usuario u ON u.id_usuario = i.id_usuario
 WHERE u.email = 'facundo@parea.test';

INSERT INTO asistencia (id_inscripcion, estado_asistencia)
SELECT i.id_inscripcion, 'pendiente'
  FROM inscripcion i
  JOIN usuario u ON u.id_usuario = i.id_usuario
 WHERE u.email = 'agustina@parea.test';
