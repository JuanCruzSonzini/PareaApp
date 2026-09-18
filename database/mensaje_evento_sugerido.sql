-- Tabla mensaje_evento_sugerido
-- Tabla intermedia N:M entre mensaje_ia y evento: registra que eventos
-- sugirio el asistente dentro de un mensaje puntual. Depende de ambas.

CREATE TABLE mensaje_evento_sugerido (
    id_mensaje  BIGINT  NOT NULL REFERENCES mensaje_ia(id_mensaje) ON DELETE CASCADE,
    id_evento   BIGINT  NOT NULL REFERENCES evento(id_evento)      ON DELETE CASCADE,
    orden       INTEGER,

    -- Sin id propio: la PK es la combinacion de las dos FK.
    PRIMARY KEY (id_mensaje, id_evento)
);

INSERT INTO mensaje_evento_sugerido (id_mensaje, id_evento, orden)
SELECT m.id_mensaje, e.id_evento, 1
  FROM mensaje_ia m, evento e
 WHERE m.emisor = 'asistente' AND e.titulo = 'Jornada de tecnologia UTN';
