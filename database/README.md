# Base de datos - Parea

PostgreSQL 16. Todos los identificadores (tablas, columnas, constraints,
indices y nombres de archivo) estan en **snake_case** y sin comillas dobles,
que es la convencion de Postgres.

---

## 1. Levantar la base con Docker

Necesitas Docker corriendo en tu maquina (en Windows y macOS eso es
**Docker Desktop**; en Linux alcanza con el paquete `docker` + `docker compose`).

```bash
docker run --name parea-db \
  -e POSTGRES_PASSWORD=parea \
  -e POSTGRES_DB=parea \
  -p 5432:5432 \
  -d postgres:16
```

Esto se hace **una sola vez**. Despues, para arrancar y parar:

```bash
docker start parea-db
docker stop  parea-db
```

Los datos quedan guardados dentro del contenedor: si lo paras y lo volves a
arrancar, no se pierde nada. Solo se pierde si haces `docker rm parea-db`.

### Datos de conexion

| Parametro | Valor       |
|-----------|-------------|
| Host      | `localhost` |
| Puerto    | `5432`      |
| Base      | `parea`     |
| Usuario   | `postgres`  |
| Password  | `parea`     |

URL JDBC para el backend: `jdbc:postgresql://localhost:5432/parea`

---

## 2. Cargar el esquema

`init_db.sql` crea todas las tablas **en orden de dependencias** y carga unos
datos de prueba. Todo corre dentro de una transaccion: o entra todo, o no
entra nada.

Desde esta carpeta (`database/`):

```bash
docker cp . parea-db:/tmp/db
docker exec parea-db psql -U postgres -d parea -f /tmp/db/init_db.sql
```

Si tenes `psql` instalado localmente, tambien sirve:

```bash
psql -h localhost -U postgres -d parea -f init_db.sql
```

Para verificar que salio bien:

```bash
docker exec -it parea-db psql -U postgres -d parea -c "\dt"
```

Tienen que aparecer **18 tablas**.

### Volver a cargar desde cero

Si ya cargaste el esquema y queres rehacerlo, la forma mas simple es recrear
el contenedor:

```bash
docker rm -f parea-db
# y volver a correr el docker run de arriba
```

(Tambien podes descomentar el `DROP SCHEMA public CASCADE;` que esta al
principio de `init_db.sql`, pero eso borra todos los datos sin preguntar.)

---

## 3. Orden de dependencias

No se pueden correr los archivos en orden alfabetico: una tabla no se puede
crear antes que las tablas a las que referencia. El orden correcto (el que ya
usa `init_db.sql`) es:

1. `usuario.sql`, `categoria.sql`, `ubicacion.sql`, `estado_evento.sql` (sin dependencias)
2. `evento.sql`, `evento_categoria.sql`
3. `inscripcion.sql`, `entrada.sql`, `asistencia.sql`
4. `resena.sql`, `alerta_evento.sql`, `cambio_estado_evento.sql`, `notificacion.sql`, `sesion.sql`, `visualizacion_evento.sql`
5. `conversacion_ia.sql`, `mensaje_ia.sql`, `mensaje_evento_sugerido.sql`

---

## 4. Convenciones

- **snake_case en todo**, siempre en minusculas y sin tildes ni `ñ`.
  Postgres pasa a minusculas cualquier identificador que no este entre
  comillas dobles, asi que `CREATE TABLE "estadoEvento"` obliga a escribir
  `"estadoEvento"` con comillas **en todas** las consultas posteriores. Por eso
  no usamos comillas dobles en ningun lado.
- **PK**: `id_<tabla>` (`id_usuario`, `id_evento`, ...).
- **FK**: mismo nombre que la PK a la que apunta.
- **Fechas**: TODA columna de fecha y hora lleva el prefijo `fh_`
  (`fh_alta`, `fh_baja`, `fh_inicio`, `fh_fin`, `fh_emision`, `fh_creacion`,
  `fh_lectura`, ...). Ni `fecha_alta` ni `fecha_hora`. La unica excepcion son
  las columnas de fecha pura sin hora (tipo `DATE`), que siguen con `fecha_`:
  hoy la unica es `usuario.fecha_nacimiento`.
- **Estados**: siempre `estado_<tabla>` (`estado_entrada`, `estado_alerta`,
  `estado_asistencia`, ...), nunca `estado` a secas.
- **CHECK**: por ahora NO hay ninguno en el esquema. Todas las validaciones
  propuestas estan listadas en la seccion 5, pendientes de discutir con el
  equipo. Hoy la base no valida rangos, formatos ni listas de valores.
- **Constraints**: `ck_<tabla>_<que_valida>` para los CHECK,
  `ux_<tabla>_<que>` para UNIQUE e indices unicos.
- **Extension `citext`**: la activa `init_db.sql`. La usan `usuario.email` y
  `categoria.nombre` para que las comparaciones no distingan mayusculas.

---

## 5. Validaciones pendientes de discutir

**El esquema no tiene ningun `CHECK`.** Se sacaron todos a proposito: varios
fijaban listas de valores o rangos que el equipo todavia no acordo, y no
queremos que el codigo cierre esas decisiones por adelantado.

Abajo quedan los 15 que estaban escritos, con el `ALTER TABLE` listo para
copiar y pegar. A medida que se vayan acordando, se agregan de a uno.

> Recordatorio para la charla: agregar un `CHECK` despues es barato, es un
> `ALTER TABLE` de una linea. Lo caro es limpiar los datos invalidos que se
> colaron mientras el `CHECK` no estaba, porque `ALTER TABLE` falla si alguna
> fila existente no cumple la regla.

### 5.1 Listas de valores (dependen de acordar los nombres)

Estas son las columnas que hoy aceptan cualquier texto, incluido un typo:
`usuario.rol`, `usuario.estado_cuenta`, `evento.modalidad`,
`inscripcion.estado_inscripcion`, `asistencia.estado_asistencia`,
`alerta_evento.estado_alerta`, `notificacion.estado_envio`,
`entrada.estado_entrada`, `mensaje_ia.emisor`.

Solo dos llegaron a tener `CHECK` escrito:

```sql
-- Valores originalmente propuestos para entrada.estado_entrada
ALTER TABLE entrada ADD CONSTRAINT ck_entrada_estado
    CHECK (estado_entrada IN ('emitida', 'utilizada', 'anulada'));

-- Valores originalmente propuestos para mensaje_ia.emisor
ALTER TABLE mensaje_ia ADD CONSTRAINT ck_mensaje_emisor
    CHECK (emisor IN ('usuario', 'asistente'));
```

Para las otras siete columnas hay que definir la lista primero. Una propuesta
de arranque, para discutir:

| Columna | Valores propuestos |
|---|---|
| `usuario.rol` | `USUARIO`, `ORGANIZADOR`, `ADMIN` |
| `usuario.estado_cuenta` | `activo`, `suspendido`, `eliminado` |
| `evento.modalidad` | `presencial`, `virtual`, `hibrido` |
| `inscripcion.estado_inscripcion` | `confirmada`, `cancelada`, `lista_espera` |
| `asistencia.estado_asistencia` | `pendiente`, `presente`, `ausente` |
| `alerta_evento.estado_alerta` | `pendiente`, `revisada`, `desestimada` |
| `notificacion.estado_envio` | `pendiente`, `enviada`, `fallida` |

Ojo con una inconsistencia a resolver: `rol` usa MAYUSCULAS y el resto de los
estados van en minuscula. Conviene unificar el criterio.

Alternativa a los `CHECK`: si se preve que una lista va a cambiar seguido,
conviene una tabla catalogo con FK, como ya se hizo con `estado_evento`.

### 5.2 Coherencia entre columnas

Reglas de "esto es imposible en la realidad". No dependen de ningun nombre de
valor, salvo la primera.

```sql
-- Ataba el estado de la entrada con su fecha de validacion.
-- OJO: depende del valor 'utilizada', asi que primero hay que cerrar 5.1.
ALTER TABLE entrada ADD CONSTRAINT ck_entrada_validacion_coherente
    CHECK ((estado_entrada = 'utilizada') = (fh_validacion IS NOT NULL));

-- Un evento no puede terminar antes de empezar.
ALTER TABLE evento ADD CONSTRAINT ck_evento_fechas
    CHECK (fh_fin IS NULL OR fh_fin >= fh_inicio);

-- es_gratuito = TRUE  <-> precio = 0
-- es_gratuito = FALSE <-> precio > 0
ALTER TABLE evento ADD CONSTRAINT ck_evento_precio_coherente
    CHECK (es_gratuito = (precio = 0));

-- Una sesion no puede cerrarse antes de abrirse.
ALTER TABLE sesion ADD CONSTRAINT ck_sesion_fechas
    CHECK (fh_fin IS NULL OR fh_fin >= fh_inicio);

-- Una notificacion no puede haberse leido antes de existir.
ALTER TABLE notificacion ADD CONSTRAINT ck_notificacion_lectura
    CHECK (fh_lectura IS NULL OR fh_lectura >= fh_creacion);

-- Un "cambio de estado" que deja el mismo estado no es un cambio.
ALTER TABLE cambio_estado_evento ADD CONSTRAINT ck_cambio_estado_distinto
    CHECK (id_estado_anterior IS DISTINCT FROM id_estado_nuevo);
```

### 5.3 Rangos numericos

```sql
-- A DEFINIR: el diagrama no aclara si la puntuacion es 1..5 o 1..10.
ALTER TABLE resena ADD CONSTRAINT ck_resena_puntuacion
    CHECK (puntuacion BETWEEN 1 AND 10);

ALTER TABLE evento ADD CONSTRAINT ck_evento_precio_no_negativo
    CHECK (precio >= 0);

-- cupo_max NULL = sin limite de cupo.
ALTER TABLE evento ADD CONSTRAINT ck_evento_cupo
    CHECK (cupo_max IS NULL OR cupo_max > 0);

ALTER TABLE ubicacion ADD CONSTRAINT ck_ubicacion_latitud
    CHECK (latitud IS NULL OR latitud BETWEEN -90 AND 90);

ALTER TABLE ubicacion ADD CONSTRAINT ck_ubicacion_longitud
    CHECK (longitud IS NULL OR longitud BETWEEN -180 AND 180);
```

### 5.4 Formato

```sql
-- Formato basico de email: algo@algo.tld
ALTER TABLE usuario ADD CONSTRAINT ck_usuario_email_formato
    CHECK (email ~ '^[^@[:space:]]+@[^@[:space:]]+\.[A-Za-z]{2,}$');

-- No se puede haber nacido en el futuro.
ALTER TABLE usuario ADD CONSTRAINT ck_usuario_nacimiento
    CHECK (fecha_nacimiento IS NULL OR fecha_nacimiento < CURRENT_DATE);
```

### 5.5 Lo que SI quedo en el esquema

No son `CHECK` y siguen activos:

- **`UNIQUE`**: `usuario.email`, `categoria.nombre`, `entrada.codigo` y
  `ux_asistencia_inscripcion` (una sola asistencia por inscripcion).
- **`NOT NULL`** y los `DEFAULT` de cada tabla.
- **Las claves foraneas**, que siguen garantizando que no haya referencias
  a filas que no existen.
- **`ux_inscripcion_vigente`**, un indice unico parcial que impide dos
  inscripciones vigentes del mismo usuario al mismo evento. OJO: su condicion
  `WHERE estado_inscripcion <> 'cancelada'` tambien depende de un valor sin
  acordar (ver 5.1), aunque no sea un `CHECK`.
