-- ============================================================
--  Parea - carga completa de la base de datos
-- ============================================================
--  Uso:
--      psql -h localhost -U postgres -d parea -f init_db.sql
--
--  Los archivos se incluyen en ORDEN DE DEPENDENCIAS (no alfabetico):
--  una tabla no se puede crear antes que las tablas a las que
--  referencia con sus claves foraneas.
--
--  \ir incluye el archivo tomando la ruta relativa a ESTE archivo,
--  asi que no importa desde que carpeta se ejecute psql.
-- ============================================================

-- Si cualquier sentencia falla, psql corta la ejecucion en vez de
-- seguir adelante dejando la base a medio cargar.
\set ON_ERROR_STOP on

-- Descomentar SOLO si se quiere borrar todo y volver a cargar de cero.
-- CUIDADO: esto elimina todas las tablas y todos los datos.
-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;

-- citext = "case-insensitive text". Lo usan usuario.email y categoria.nombre
-- para que 'Musica' y 'musica' se consideren el mismo valor.
-- Viene incluido en la imagen oficial de Postgres, solo hay que activarlo.
CREATE EXTENSION IF NOT EXISTS citext;

BEGIN;

-- 1) Tablas sin dependencias
\ir usuario.sql
\ir categoria.sql
\ir ubicacion.sql
\ir estado_evento.sql

-- 2) Evento y su relacion N:M con categoria
\ir evento.sql
\ir evento_categoria.sql

-- 3) Inscripcion y todo lo que cuelga de ella
\ir inscripcion.sql
\ir entrada.sql
\ir asistencia.sql

-- 4) Interaccion de los usuarios con los eventos
\ir resena.sql
\ir alerta_evento.sql
\ir cambio_estado_evento.sql
\ir notificacion.sql
\ir sesion.sql
\ir visualizacion_evento.sql

-- 5) Asistente de IA
\ir conversacion_ia.sql
\ir mensaje_ia.sql
\ir mensaje_evento_sugerido.sql

COMMIT;
