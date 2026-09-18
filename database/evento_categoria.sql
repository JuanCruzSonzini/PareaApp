-- Tabla evento_categoria
-- Resuelve la relacion N:M entre evento y categoria: un evento puede
-- tener varias categorias, y una categoria puede estar en varios eventos.
-- Depende de evento y categoria.

CREATE TABLE evento_categoria (
    id_evento    BIGINT NOT NULL REFERENCES evento(id_evento)       ON DELETE CASCADE,
    id_categoria BIGINT NOT NULL REFERENCES categoria(id_categoria) ON DELETE CASCADE,

    -- PK compuesta
    PRIMARY KEY (id_evento, id_categoria)
);

-- Le asignamos categorias al evento de prueba que ya cargamos en evento.sql
INSERT INTO evento_categoria (id_evento, id_categoria)
SELECT e.id_evento, c.id_categoria
  FROM evento e, categoria c
 WHERE e.titulo = 'Jornada de tecnologia UTN'
   AND c.nombre IN ('Academico', 'Tecnologia');
