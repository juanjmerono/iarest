## ADDED Requirements

### Requirement: Crear tarea
El sistema DEBE permitir a usuarios autenticados con scope 'write' crear nuevas tareas en la lista. Los campos `fecha` y `estado` se establecen automáticamente y se ignoran si se envían en la petición.

#### Scenario: Crear tarea exitosamente
- **WHEN** usuario envía POST a /example/demo/todos con JSON conteniendo solo el campo 'asunto'
- **AND** el usuario tiene scope 'write' válido
- **THEN** el sistema retorna código HTTP 201 Created
- **AND** la respuesta contiene la tarea creada con id asignado
- **AND** la tarea tiene fecha igual a la fecha actual del sistema
- **AND** la tarea tiene estado 'PENDIENTE'

#### Scenario: Crear tarea sin autenticación
- **WHEN** usuario envía POST a /example/demo/todos sin token de autenticación
- **THEN** el sistema retorna código HTTP 401 Unauthorized

#### Scenario: Crear tarea sin scope write
- **WHEN** usuario envía POST a /example/demo/todos con token que tiene solo scope 'read'
- **THEN** el sistema retorna código HTTP 403 Forbidden

#### Scenario: Crear tarea con datos inválidos
- **WHEN** usuario envía POST a /example/demo/todos con campo 'asunto' vacío
- **THEN** el sistema retorna código HTTP 400 Bad Request
