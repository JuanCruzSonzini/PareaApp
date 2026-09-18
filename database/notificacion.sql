-- Tabla notificacion
-- Depende de usuario, evento (opcional) e inscripcion (opcional).
-- Sin CHECK: ver "Validaciones pendientes" en README.md.

CREATE TABLE notificacion (
    id_notificacion BIGINT       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_usuario      BIGINT       NOT NULL REFERENCES usuario(id_usuario),
    id_evento       BIGINT       REFERENCES evento(id_evento),
    id_inscripcion  BIGINT       REFERENCES inscripcion(id_inscripcion),
    -- Ambas nullable: no todas las notificaciones estan atadas a un
    -- evento o inscripcion puntual (por ejemplo, "se aprobo tu cuenta
    -- de organizador" no tiene evento asociado).

    tipo            VARCHAR(40)  NOT NULL,
    titulo          VARCHAR(150) NOT NULL,
    mensaje         TEXT         NOT NULL,
    estado_envio    VARCHAR(15)  NOT NULL,

    fh_creacion     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    -- Momento en que el sistema genero la notificacion.

    fh_lectura      TIMESTAMPTZ
    -- NULL = todavia no fue leida.
);

INSERT INTO notificacion (id_usuario, id_evento, id_inscripcion, tipo, titulo, mensaje, estado_envio)
SELECT i.id_usuario, i.id_evento, i.id_inscripcion,
       'confirmacion_inscripcion', 'Inscripcion confirmada',
       'Tu inscripcion a "' || e.titulo || '" fue confirmada.', 'enviada'
  FROM inscripcion i
  JOIN evento e ON e.id_evento = i.id_evento
 WHERE i.id_usuario = (SELECT id_usuario FROM usuario WHERE email = 'facundo@parea.test');
