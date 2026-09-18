-- Tabla resena
-- Depende de usuario y evento.
-- Sin CHECK: ver "Validaciones pendientes" en README.md.

CREATE TABLE resena (
    id_resena  BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_usuario BIGINT      NOT NULL REFERENCES usuario(id_usuario),
    id_evento  BIGINT      NOT NULL REFERENCES evento(id_evento),

    puntuacion SMALLINT    NOT NULL,
    -- PENDIENTE: definir el rango valido (1..5 o 1..10) antes de poner el CHECK.

    comentario TEXT,
    fh_alta    TIMESTAMPTZ NOT NULL DEFAULT now(),
    fh_baja    TIMESTAMPTZ
);

INSERT INTO resena (id_usuario, id_evento, puntuacion, comentario)
SELECT u.id_usuario, e.id_evento, 5, 'Muy buena organizacion y charlas de nivel.'
  FROM usuario u, evento e
 WHERE u.email = 'facundo@parea.test' AND e.titulo = 'Jornada de tecnologia UTN';
