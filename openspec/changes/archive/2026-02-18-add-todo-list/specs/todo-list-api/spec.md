## ADDED Requirements

### Requirement: Obtener lista de tareas
El sistema SHALL permitir obtener la lista de todas las tareas del usuario con su información básica.

#### Scenario: Solicitar lista de tareas exitosamente
- **WHEN** el cliente envía una petición GET a `/example/demo/todos`
- **THEN** el servidor retorna un código de estado 200 OK con un JSON array conteniendo las tareas

#### Scenario: Lista de tareas vacía
- **WHEN** el cliente envía una petición GET a `/example/demo/todos` y no existen tareas
- **THEN** el servidor retorna un código de estado 200 OK con un JSON array vacío `[]`

### Requirement: Estructura de datos de tarea
El sistema SHALL retornar la información de cada tarea con los campos: asunto, fecha y estado.

#### Scenario: Estructura de tarea en respuesta
- **WHEN** el servidor retorna una lista de tareas
- **THEN** cada tarea contiene los campos: `id` (identificador), `asunto` (string), `fecha` (ISO 8601), `estado` (string)

### Requirement: Formato de fecha
El sistema SHALL utilizar formato ISO 8601 (yyyy-MM-dd) para las fechas.

#### Scenario: Formato de fecha en respuesta
- **WHEN** el servidor retorna una tarea con fecha
- **THEN** la fecha está en formato `yyyy-MM-dd` (ejemplo: "2025-01-15")

### Requirement: Estados de tarea
El sistema SHALL soportar los estados: "pendiente", "en_progreso", "completada".

#### Scenario: Tarea con estado válido
- **WHEN** el servidor retorna una tarea
- **THEN** el campo `estado` contiene uno de los valores: "pendiente", "en_progreso", "completada"
