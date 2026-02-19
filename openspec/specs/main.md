# API de Gestión de Tareas - Universidad de Murcia

## Visión General

API REST para gestionar una lista de tareas (TODO list) con seguridad OAuth2/OIDC. Permite a usuarios autenticados obtener sus tareas y crear nuevas tareas.

## Capabilities

- `todo-list-api`: Endpoint para obtener lista de tareas
- `create-task`: Endpoint para crear nuevas tareas
- `task-uuid-id`: Identificador único UUID para tareas
- `task-ownership`: Propiedad de tareas por usuario

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

| Campo    | Tipo     | Descripción                    |
|----------|----------|--------------------------------|
| id       | UUID     | Identificador único            |
| asunto   | String   | Descripción de la tarea        |
| fecha    | Date     | Fecha de creación (ISO 8601)  |
| estado   | Enum     | PENDIENTE, EN_PROGRESO, COMPLETADA |
| usuarioId | String  | ID del usuario propietario     |

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
| 2026-02-18  | Añadir endpoint GET todos |
| 2026-02-18  | Añadir endpoint POST crear tarea |
| 2026-02-18  | Añadir UUID a tareas      |
| 2026-02-18  | Multi-usuario (propiedad de tareas) |
