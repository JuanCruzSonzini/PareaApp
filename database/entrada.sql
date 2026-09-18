-- Tabla entrada
-- Depende de inscripcion. Es el "ticket" (con su codigo QR) que se emite
-- para poder ingresar al evento.

CREATE TABLE entrada (
    id_entrada     BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_inscripcion BIGINT      NOT NULL REFERENCES inscripcion(id_inscripcion),
    -- Sin UNIQUE a proposito: el diagrama marca la UK en "codigo", no aca,
    -- asi que una misma inscripcion podria tener mas de una entrada.

    codigo         UUID        NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    -- gen_random_uuid() es funcion nativa de Postgres desde la version 13,
    -- no hace falta instalar ninguna extension para usarla.

    estado_entrada VARCHAR(15) NOT NULL DEFAULT 'emitida',
    -- PENDIENTE: definir con el equipo los valores validos de estado_entrada
    -- (la idea original era 'emitida' / 'utilizada' / 'anulada'). Hasta que
    -- eso este acordado, la columna acepta cualquier texto: no hay CHECK.

    fh_emision     TIMESTAMPTZ NOT NULL DEFAULT now(),
    fh_validacion  TIMESTAMPTZ
    -- fh_validacion NULL = todavia no se escaneo la entrada.
    -- PENDIENTE: junto con los valores de estado_entrada habria que volver
    -- a atar que fh_validacion solo tenga valor cuando la entrada se uso.
);

INSERT INTO entrada (id_inscripcion)
SELECT i.id_inscripcion
  FROM inscripcion i
  JOIN usuario u ON u.id_usuario = i.id_usuario
 WHERE u.email = 'facundo@parea.test';

INSERT INTO entrada (id_inscripcion)
SELECT i.id_inscripcion
  FROM inscripcion i
  JOIN usuario u ON u.id_usuario = i.id_usuario
 WHERE u.email = 'agustina@parea.test';
