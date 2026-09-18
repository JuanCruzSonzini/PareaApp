-- Tabla sesion
-- Depende de usuario. Registra cada inicio y fin de sesion.
-- Sin CHECK: ver "Validaciones pendientes" en README.md.

CREATE TABLE sesion (
    id_sesion      BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    id_usuario     BIGINT      NOT NULL REFERENCES usuario(id_usuario),

    fh_inicio      TIMESTAMPTZ NOT NULL DEFAULT now(),
    fh_fin         TIMESTAMPTZ,
    -- fh_fin NULL = la sesion todavia esta activa (el usuario no cerro sesion).

    direccion_ip   INET,
    motivo_cierre  VARCHAR(20)
);

-- Datos de prueba: una sesion ya cerrada y una todavia activa.
INSERT INTO sesion (id_usuario, fh_inicio, fh_fin, direccion_ip, motivo_cierre)
SELECT id_usuario, now() - interval '2 hours', now() - interval '1 hour', '190.191.10.20', 'logout'
  FROM usuario WHERE email = 'facundo@parea.test';

INSERT INTO sesion (id_usuario, direccion_ip)
SELECT id_usuario, '190.191.10.21'
  FROM usuario WHERE email = 'agustina@parea.test';
