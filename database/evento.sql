-- Tabla evento
-- Depende de usuario, ubicacion y estado_evento.
-- Sin CHECK: ver "Validaciones pendientes" en README.md.

CREATE TABLE evento (
    id_evento             BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_organizador        BIGINT        NOT NULL REFERENCES usuario(id_usuario),
    id_ubicacion          BIGINT        REFERENCES ubicacion(id_ubicacion),
    id_estado_evento      BIGINT        NOT NULL REFERENCES estado_evento(id_estado_evento),

    titulo                VARCHAR(150)  NOT NULL,
    descripcion           TEXT,
    modalidad             VARCHAR(15)   NOT NULL DEFAULT 'presencial',
    fh_inicio             TIMESTAMPTZ   NOT NULL,
    fh_fin                TIMESTAMPTZ,
    es_gratuito           BOOLEAN       NOT NULL DEFAULT TRUE,
    precio                NUMERIC(12,2) NOT NULL DEFAULT 0,
    requiere_inscripcion  BOOLEAN       NOT NULL DEFAULT TRUE,
    cupo_max              INTEGER,
    verificado            BOOLEAN       NOT NULL DEFAULT FALSE,
    fh_alta               TIMESTAMPTZ   NOT NULL DEFAULT now(),
    fh_baja               TIMESTAMPTZ
);

INSERT INTO evento (id_organizador, id_ubicacion, id_estado_evento, titulo, descripcion,
                    fh_inicio, fh_fin, cupo_max)
SELECT u.id_usuario, ub.id_ubicacion,
       (SELECT id_estado_evento FROM estado_evento WHERE nombre = 'publicado'),
       'Jornada de tecnologia UTN',
       'Charlas de estudiantes y empresas de la region.',
       now() + interval '15 days', now() + interval '15 days' + interval '8 hours',
       100
  FROM usuario u, ubicacion ub
 WHERE u.email = 'facundo@parea.test' AND ub.nombre_lugar = 'UTN FRC';
