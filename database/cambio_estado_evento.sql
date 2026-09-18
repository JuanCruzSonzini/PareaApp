-- Tabla cambio_estado_evento
-- Log de auditoria: registra cada vez que un evento paso de un estado a
-- otro, quien lo hizo y por que. Depende de evento, estado_evento (dos
-- veces: estado anterior y estado nuevo) y usuario.
-- Sin CHECK: ver "Validaciones pendientes" en README.md.

CREATE TABLE cambio_estado_evento (
    id_cambio_estado    BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_evento           BIGINT      NOT NULL REFERENCES evento(id_evento),
    id_estado_anterior  BIGINT      REFERENCES estado_evento(id_estado_evento),
    -- Nullable: el primer registro de un evento (por ejemplo, cuando se
    -- crea directamente en "borrador") no tiene un estado previo.

    id_estado_nuevo     BIGINT      NOT NULL REFERENCES estado_evento(id_estado_evento),
    id_usuario          BIGINT      NOT NULL REFERENCES usuario(id_usuario),
    -- Quien hizo el cambio (el organizador, o un moderador/admin).

    fh_cambio           TIMESTAMPTZ NOT NULL DEFAULT now(),
    motivo              TEXT
);

-- Ejemplo: el evento paso de borrador a publicado.
INSERT INTO cambio_estado_evento (id_evento, id_estado_anterior, id_estado_nuevo, id_usuario, motivo)
SELECT e.id_evento,
       (SELECT id_estado_evento FROM estado_evento WHERE nombre = 'borrador'),
       (SELECT id_estado_evento FROM estado_evento WHERE nombre = 'publicado'),
       u.id_usuario,
       'Se completaron todos los datos obligatorios y se publico el evento.'
  FROM evento e, usuario u
 WHERE e.titulo = 'Jornada de tecnologia UTN' AND u.email = 'facundo@parea.test';
