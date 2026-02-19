# API de Gestión de Tareas - Universidad de Murcia

## Visión General

API REST para gestionar una lista de tareas (TODO list) con seguridad OAuth2/OIDC. Permite a usuarios autenticados obtener sus tareas y crear nuevas tareas.

## Capabilities

- `todo-list-api`: Endpoint para obtener lista de tareas
- `create-task`: Endpoint para crear nuevas tareas
- `task-uuid-id`: Identificador único UUID para tareas
- `task-ownership`: Propiedad de tareas por usuario
- `task-created-event`: Evento de dominio cuando se crea una tarea
- `complete-task`: Endpoint para completar tareas
- `task-timestamps`: Timestamps para creación y resolución
- `task-completed-event`: Evento de dominio al completar tarea

---

## ADDED Requirements

### Capability: todo-list-api

#### Requirement: Obtener lista de tareas
El sistema DEBE permitir obtener la lista de todas las tareas del usuario con su información básica.

##### Scenario: Solicitar lista de tareas exitosamente
- **WHEN** el cliente envía una petición GET a `/example/demo/todos`
- **THEN** el servidor retorna un código de estado 200 OK con un JSON array conteniendo las tareas

##### Scenario: Lista de tareas vacía
- **WHEN** el cliente envía una petición GET a `/example/demo/todos` y no existen tareas
- **THEN** el servidor retorna un código de estado 200 OK con un JSON array vacío `[]`

#### Requirement: Estructura de datos de tarea
El sistema DEBE retornar la información de cada tarea con los campos: asunto, fecha y estado.

##### Scenario: Estructura de tarea en respuesta
- **WHEN** el servidor retorna una lista de tareas
- **THEN** cada tarea contiene los campos: `id` (identificador), `asunto` (string), `fecha` (ISO 8601), `estado` (string)

#### Requirement: Formato de fecha
El sistema DEBE utilizar formato ISO 8601 (yyyy-MM-dd) para las fechas.

##### Scenario: Formato de fecha en respuesta
- **WHEN** el servidor retorna una tarea con fecha
- **THEN** la fecha está en formato `yyyy-MM-dd` (ejemplo: "2025-01-15")

#### Requirement: Estados de tarea
El sistema DEBE soportar los estados: "pendiente", "en_progreso", "completada".

##### Scenario: Tarea con estado válido
- **WHEN** el servidor retorna una tarea
- **THEN** el campo `estado` contiene uno de los valores: "pendiente", "en_progreso", "completada"

---

### Capability: create-task

#### Requirement: Crear tarea
El sistema DEBE permitir a usuarios autenticados con scope 'write' crear nuevas tareas en la lista. Los campos `fecha` y `estado` se establecen automáticamente y se ignoran si se envían en la petición.

##### Scenario: Crear tarea exitosamente
- **WHEN** usuario envía POST a /example/demo/todos con JSON conteniendo solo el campo 'asunto'
- **AND** el usuario tiene scope 'write' válido
- **THEN** el sistema retorna código HTTP 201 Created
- **AND** la respuesta contiene la tarea creada con id asignado
- **AND** la tarea tiene fecha igual a la fecha actual del sistema
- **AND** la tarea tiene estado 'PENDIENTE'

##### Scenario: Crear tarea sin autenticación
- **WHEN** usuario envía POST a /example/demo/todos sin token de autenticación
- **THEN** el sistema retorna código HTTP 401 Unauthorized

##### Scenario: Crear tarea sin scope write
- **WHEN** usuario envía POST a /example/demo/todos con token que tiene solo scope 'read'
- **THEN** el sistema retorna código HTTP 403 Forbidden

##### Scenario: Crear tarea con datos inválidos
- **WHEN** usuario envía POST a /example/demo/todos con campo 'asunto' vacío
- **THEN** el sistema retorna código HTTP 400 Bad Request

---

### Capability: task-uuid-id

#### Requirement: Identificador UUID para tareas
El sistema DEBE usar un identificador único universal (UUID) para cada tarea en lugar de un número secuencial.

##### Scenario: Crear tarea retorna UUID
- **WHEN** usuario crea una tarea exitosamente
- **THEN** la respuesta contiene un campo `uuid` con formato UUID válido

##### Scenario: Obtener tareas incluye UUID
- **WHEN** usuario obtiene la lista de tareas
- **THEN** cada tarea en la respuesta contiene el campo `uuid`

##### Scenario: UUID es único
- **WHEN** se crean múltiples tareas
- **THEN** cada tarea tiene un UUID diferente

---

### Capability: task-ownership

#### Requirement: Tareas propias del usuario
El sistema DEBE permitir que cada usuario vea y gestione únicamente sus propias tareas.

##### Scenario: Obtener tareas del usuario autenticado
- **WHEN** usuario autenticado con subject "user1" consulta la lista de tareas
- **THEN** solo retorna las tareas creadas por "user1"

##### Scenario: Crear tarea asociada al usuario
- **WHEN** usuario autenticado con subject "user1" crea una tarea
- **THEN** la tarea se asocia automáticamente a "user1"
- **AND** la tarea aparece en las consultas posteriores de "user1"

##### Scenario: Usuario no ve tareas de otros
- **WHEN** usuario "user1" crea una tarea
- **AND** usuario "user2" consulta la lista de tareas
- **THEN** usuario "user2" no ve la tarea creada por "user1"

---

### Capability: task-created-event

#### Requirement: Publicar evento al crear tarea
El sistema DEBE publicar un evento de dominio `TareaCreadaEvent` cuando se crea una nueva tarea exitosamente.

##### Scenario: Evento publicado tras creación exitosa
- **WHEN** un usuario autenticado con permiso 'write' crea una tarea exitosamente mediante POST a /example/demo/todos
- **AND** la tarea se persiste correctamente en la base de datos
- **THEN** el sistema publica un evento `TareaCreadaEvent` con los datos de la tarea creada

#### Requirement: Contenido del evento TareaCreadaEvent
El evento `TareaCreadaEvent` DEBE contener la siguiente información de la tarea creada:
- uuid: Identificador único de la tarea
- asunto: Descripción de la tarea
- fecha: Fecha de creación en formato ISO 8601 (yyyy-MM-dd)
- usuarioId: ID del usuario que creó la tarea

##### Scenario: Evento contiene datos correctos
- **WHEN** se publica el evento `TareaCreadaEvent`
- **THEN** el evento contiene los campos: uuid, asunto, fecha, usuarioId
- **AND** el campo uuid corresponde al UUID asignado a la tarea
- **AND** el campo asunto contiene el valor enviado en la petición
- **AND** el campo fecha contiene la fecha actual del sistema
- **AND** el campo usuarioId contiene el subject del token JWT del usuario

#### Requirement: Suscriptor puede reaccionar al evento
El sistema DEBE permitir que componentes subscribed al evento `TareaCreadaEvent` puedan reaccionar a la creación de tareas.

##### Scenario: Suscriptor recibe el evento
- **WHEN** existe un componente registrado como listener de `TareaCreadaEvent`
- **AND** se crea una nueva tarea
- **THEN** el listener recibe el evento con los datos de la tarea creada

#### Requirement: Evento no altera la respuesta API
La publicación del evento DEBE ser transparente para el cliente que creó la tarea. El evento no debe afectar el tiempo de respuesta ni el contenido de la respuesta HTTP.

##### Scenario: Respuesta API no afectada por evento
- **WHEN** un usuario crea una tarea exitosamente
- **THEN** el tiempo de respuesta no se ve incrementado significativamente por la publicación del evento
- **AND** la respuesta HTTP contiene el código 201 Created como siempre
- **AND** la respuesta incluye la tarea creada con sus datos

---

### Capability: complete-task

#### Requirement: Completar tarea
El sistema DEBE permitir a usuarios autenticados con scope 'write' completar una tarea, cambiando su estado a COMPLETADA.

##### Scenario: Completar tarea exitosamente
- **WHEN** usuario autenticado con permiso 'write' envía PATCH a `/example/demo/todos/{uuid}/completar`
- **AND** la tarea existe y pertenece al usuario
- **THEN** el sistema retorna código HTTP 200 OK
- **AND** la respuesta contiene la tarea con estado 'COMPLETADA'
- **AND** la tarea tiene fechaResolucion establecida

##### Scenario: Completar tarea sin autenticación
- **WHEN** usuario envía PATCH a `/example/demo/todos/{uuid}/completar` sin token
- **THEN** el sistema retorna código HTTP 401 Unauthorized

##### Scenario: Completar tarea sin permiso write
- **WHEN** usuario autenticado con solo scope 'read' envía PATCH a `/example/demo/todos/{uuid}/completar`
- **THEN** el sistema retorna código HTTP 403 Forbidden

##### Scenario: Completar tarea que no existe
- **WHEN** usuario autenticado con permiso 'write' envía PATCH a `/example/demo/todos/inexistente/completar`
- **THEN** el sistema retorna código HTTP 404 Not Found

##### Scenario: Completar tarea de otro usuario
- **WHEN** usuario "user1" envía PATCH a `/example/demo/todos/{uuid}/completar` donde la tarea pertenece a "user2"
- **THEN** el sistema retorna código HTTP 404 Not Found

##### Scenario: Completar tarea ya completada (idempotente)
- **WHEN** usuario autenticado con permiso 'write' envía PATCH a `/example/demo/todos/{uuid}/completar` donde la tarea ya está COMPLETADA
- **THEN** el sistema retorna código HTTP 200 OK
- **AND** la respuesta contiene la tarea con estado 'COMPLETADA'
- **AND** la fechaResolucion no se modifica

---

### Capability: task-timestamps

#### Requirement: Almacenar fecha y hora de creación
El sistema DEBE almacenar la fecha y hora exactas (timestamp) cuando se crea una tarea.

##### Scenario: Tarea creada con timestamp de creación
- **WHEN** usuario autenticado con permiso 'write' crea una tarea exitosamente
- **THEN** la tarea tiene campo fechaCreacion con fecha y hora actual del sistema

##### Scenario: Timestamp de creación en formato ISO 8601
- **WHEN** se crea una tarea
- **THEN** el campo fechaCreacion está en formato ISO 8601 con fecha y hora (ejemplo: "2026-02-19T14:30:00")

#### Requirement: Almacenar fecha y hora de resolución
El sistema DEBE almacenar la fecha y hora exactas (timestamp) cuando se completa una tarea.

##### Scenario: Tarea completada con timestamp de resolución
- **WHEN** usuario autenticado completa una tarea exitosamente
- **THEN** la tarea tiene campo fechaResolucion con fecha y hora actual del sistema

##### Scenario: Timestamp de resolución en formato ISO 8601
- **WHEN** se completa una tarea
- **THEN** el campo fechaResolucion está en formato ISO 8601 con fecha y hora (ejemplo: "2026-02-19T15:45:00")

#### Requirement: Timestamp de resolución no se modifica
El sistema DEBE mantener la fecha y hora de resolución original si la tarea ya estaba completada.

##### Scenario: Completar tarea ya completada no modifica timestamp
- **WHEN** se intenta completar una tarea que ya tiene fechaResolucion
- **THEN** el campo fechaResolucion mantiene su valor original
- **AND** no se actualiza a la fecha/hora actual

#### Requirement: Respuesta API incluye timestamps
El sistema DEBE incluir los campos fechaCreacion y fechaResolucion en las respuestas de la API.

##### Scenario: GET tareas incluye timestamps
- **WHEN** usuario autenticado obtiene la lista de tareas
- **THEN** cada tarea contiene los campos fechaCreacion y fechaResolucion
- **AND** si la tarea no está completada, fechaResolucion puede ser null

##### Scenario: POST crear tarea incluye fechaCreacion en respuesta
- **WHEN** usuario autenticado crea una tarea exitosamente
- **THEN** la respuesta contiene el campo fechaCreacion con la fecha y hora de creación

---

### Capability: task-completed-event

#### Requirement: Publicar evento al completar tarea
El sistema DEBE publicar un evento de dominio `TareaCompletadaEvent` cuando se completa una tarea exitosamente.

##### Scenario: Evento publicado tras completar tarea
- **WHEN** un usuario autenticado con permiso 'write' completa una tarea exitosamente mediante PATCH a `/example/demo/todos/{uuid}/completar`
- **AND** la tarea se persiste correctamente
- **THEN** el sistema publica un evento `TareaCompletadaEvent` con los datos de la tarea

#### Requirement: Contenido del evento TareaCompletadaEvent
El evento `TareaCompletadaEvent` DEBE contener la siguiente información:
- uuid: Identificador único de la tarea
- asunto: Descripción de la tarea
- fechaResolucion: Fecha y hora de resolución en formato ISO 8601
- usuarioId: ID del usuario que completó la tarea

##### Scenario: Evento contiene datos correctos
- **WHEN** se publica el evento `TareaCompletadaEvent`
- **THEN** el evento contiene los campos: uuid, asunto, fechaResolucion, usuarioId
- **AND** el campo uuid corresponde al UUID de la tarea
- **AND** el campo asunto contiene el valor de la tarea
- **AND** el campo fechaResolucion contiene la fecha y hora actual del sistema
- **AND** el campo usuarioId contiene el subject del JWT del usuario

#### Requirement: Evento no se publica si la tarea ya está completada
El sistema NO DEBE publicar un nuevo evento si la tarea ya estaba completada (operación idempotente).

##### Scenario: No publicar evento al completar tarea ya completada
- **WHEN** usuario autenticado intenta completar una tarea que ya está COMPLETADA
- **THEN** NO se publica ningún evento `TareaCompletadaEvent`
- **AND** el sistema retorna código HTTP 200 OK

---

## MODIFIED Requirements

### Requirement: Obtener lista de tareas (de capability: todo-list-api)
El sistema DEBE permitir a usuarios autenticados obtener su lista de tareas. Modificado para filtrar por usuario autenticado.

##### Scenario: Obtener la lista de tareas autenticado
- **WHEN** usuario autenticado con scope "read" consulta la lista de tareas
- **THEN** el sistema retorna código HTTP 200
- **AND** la respuesta contiene solo las tareas del usuario autenticado
- **AND** cada tarea contiene los campos: uuid, asunto, fecha, estado, usuarioId

### Requirement: Crear tarea (de capability: create-task)
El sistema DEBE permitir a usuarios autenticados con scope 'write' crear nuevas tareas. Modificado para asociar automáticamente el usuario.

##### Scenario: Crear tarea exitosamente
- **WHEN** usuario autenticado "pepe" con permiso "write" crea una tarea con asunto "Nueva tarea"
- **THEN** el sistema retorna código HTTP 201 Created
- **AND** la tarea contiene el campo usuarioId igual al subject del JWT del usuario

---

## Modelo de Datos

### Tarea

| Campo          | Tipo        | Descripción                          |
|----------------|-------------|--------------------------------------|
| id             | UUID        | Identificador único                  |
| asunto         | String      | Descripción de la tarea              |
| fechaCreacion  | LocalDateTime | Fecha y hora de creación (ISO 8601) |
| fechaResolucion| LocalDateTime | Fecha y hora de resolución (ISO 8601, nullable) |
| estado         | Enum        | PENDIENTE, EN_PROGRESO, COMPLETADA  |
| usuarioId      | String      | ID del usuario propietario           |

## Seguridad

- **Proveedor OIDC**: https://apitest.um.es/mncs/api-umu/cas/oidc/
- **Algoritmo JWT**: RS512
- **Permisos**: 
  - `Tareas:read` - Leer tareas
  - `Tareas:write` - Crear tareas
- **Swagger**: Configurado con OAuth2 Authorization Code Flow

## Historial de Cambios

| Fecha       | Cambio                    |
|-------------|---------------------------|
| 2026-02-19  | Añadir endpoint completar tarea, timestamps y evento TareaCompletadaEvent |
| 2026-02-19  | Añadir evento de dominio TareaCreadaEvent |
| 2026-02-18  | Añadir endpoint GET todos |
| 2026-02-18  | Añadir endpoint POST crear tarea |
| 2026-02-18  | Añadir UUID a tareas      |
| 2026-02-18  | Multi-usuario (propiedad de tareas) |
