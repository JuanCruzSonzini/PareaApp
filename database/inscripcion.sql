-- Tabla inscripcion
-- Depende de usuario y evento. Conecta "quien se anota" con "a que evento".

CREATE TABLE inscripcion (
    id_inscripcion      BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_usuario          BIGINT      NOT NULL REFERENCES usuario(id_usuario),
    id_evento           BIGINT      NOT NULL REFERENCES evento(id_evento),

    estado_inscripcion  VARCHAR(15) NOT NULL DEFAULT 'confirmada',
    fh_inscripcion      TIMESTAMPTZ NOT NULL DEFAULT now(),
    fh_cancelacion      TIMESTAMPTZ
);

-- Un usuario no puede tener dos inscripciones VIGENTES al mismo evento,
-- pero si puede volver a inscribirse despues de haber cancelado.
CREATE UNIQUE INDEX ux_inscripcion_vigente
    ON inscripcion (id_evento, id_usuario) WHERE estado_inscripcion <> 'cancelada';

INSERT INTO inscripcion (id_usuario, id_evento)
SELECT u.id_usuario, e.id_evento
  FROM usuario u, evento e
 WHERE u.email = 'facundo@parea.test' AND e.titulo = 'Jornada de tecnologia UTN';

INSERT INTO inscripcion (id_usuario, id_evento)
SELECT u.id_usuario, e.id_evento
  FROM usuario u, evento e
 WHERE u.email = 'agustina@parea.test' AND e.titulo = 'Jornada de tecnologia UTN';
