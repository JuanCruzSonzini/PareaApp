-- Tabla usuario
-- Todavia no depende de nada.
-- Sin CHECK: las validaciones propuestas estan documentadas en README.md
-- (seccion "Validaciones pendientes"), a la espera de definirlas con el equipo.

CREATE TABLE usuario (
    id_usuario       BIGINT       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email            CITEXT       NOT NULL UNIQUE,
    hash_contrasena  VARCHAR(255) NOT NULL,
    -- Nunca se guarda la contrasena en texto plano, solo su hash
    -- (eso lo calcula el backend con BCrypt o similar antes de insertar).

    nombre           VARCHAR(80)  NOT NULL,
    apellido         VARCHAR(80)  NOT NULL,
    fecha_nacimiento DATE,
    -- Fecha pura sin hora, por eso queda como fecha_ y no como fh_.

    telefono         VARCHAR(30),

    rol              VARCHAR(20)  NOT NULL DEFAULT 'USUARIO',
    -- Un usuario, un solo rol a la vez (ver aclaracion en el mensaje).

    email_confirmado BOOLEAN      NOT NULL DEFAULT FALSE,
    estado_cuenta    VARCHAR(20)  NOT NULL DEFAULT 'activo',
    verificado       BOOLEAN      NOT NULL DEFAULT FALSE,

    fh_alta          TIMESTAMPTZ  NOT NULL DEFAULT now(),
    fh_baja          TIMESTAMPTZ
);

INSERT INTO usuario (email, hash_contrasena, nombre, apellido, fecha_nacimiento, rol) VALUES
    ('facundo@parea.test',  '$2a$10$hashficticio1', 'Facundo',  'Kocina',   '2003-07-10', 'USUARIO'),
    ('agustina@parea.test', '$2a$10$hashficticio2', 'Agustina', 'Gonzalez', '2003-02-28', 'ORGANIZADOR');
