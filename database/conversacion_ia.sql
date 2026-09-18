-- Tabla conversacion_ia
-- Depende de usuario. Agrupa los mensajes de un mismo hilo de chat
-- con el asistente.

CREATE TABLE conversacion_ia (
    id_conversacion    BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario         BIGINT      NOT NULL REFERENCES usuario(id_usuario),
    titulo             VARCHAR(150),
    fh_inicio          TIMESTAMPTZ NOT NULL DEFAULT now(),
    fh_ultimo_mensaje  TIMESTAMPTZ
);

INSERT INTO conversacion_ia (id_usuario, titulo, fh_ultimo_mensaje)
SELECT id_usuario, 'Planes gratis para el fin de semana', now()
  FROM usuario WHERE email = 'facundo@parea.test';
