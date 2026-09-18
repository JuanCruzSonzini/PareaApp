-- Tabla alerta_evento
-- Depende de usuario y evento. Es el reporte que hace un usuario
-- cuando considera que un evento publicado no es valido.

CREATE TABLE alerta_evento (
    id_alerta     BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_usuario    BIGINT      NOT NULL REFERENCES usuario(id_usuario),
    -- En el diagrama esta celda decia "FK" en vez de "BIGINT" en la
    -- columna de tipo; se interpreta como BIGINT, igual que id_evento.

    id_evento     BIGINT      NOT NULL REFERENCES evento(id_evento),
    motivo        VARCHAR(30) NOT NULL,
    descripcion   TEXT,
    estado_alerta VARCHAR(20) NOT NULL DEFAULT 'pendiente',
    fh_alta       TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO alerta_evento (id_usuario, id_evento, motivo, descripcion)
SELECT u.id_usuario, e.id_evento, 'informacion_falsa',
       'La direccion publicada no coincide con la real.'
  FROM usuario u, evento e
 WHERE u.email = 'agustina@parea.test' AND e.titulo = 'Jornada de tecnologia UTN';
