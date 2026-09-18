-- Tabla mensaje_ia
-- Depende de conversacion_ia. Cada fila es un mensaje del usuario o
-- una respuesta del asistente, dentro de una conversacion.

CREATE TABLE mensaje_ia (
    id_mensaje      BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_conversacion BIGINT      NOT NULL REFERENCES conversacion_ia(id_conversacion) ON DELETE CASCADE,
    -- Si se borra la conversacion, sus mensajes se van con ella.

    emisor          VARCHAR(10) NOT NULL,
    -- PENDIENTE: definir con el equipo los valores validos de emisor
    -- (la idea original era 'usuario' / 'asistente'). Sin CHECK por ahora.

    contenido       TEXT        NOT NULL,
    fh_envio        TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO mensaje_ia (id_conversacion, emisor, contenido)
SELECT c.id_conversacion, 'usuario', 'Que actividades gratuitas hay este fin de semana en Cordoba?'
  FROM conversacion_ia c;

INSERT INTO mensaje_ia (id_conversacion, emisor, contenido)
SELECT c.id_conversacion, 'asistente', 'Encontre 1 evento gratuito cerca de tu zona.'
  FROM conversacion_ia c;
