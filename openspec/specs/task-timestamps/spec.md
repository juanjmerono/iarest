# Capability: task-timestamps

## Purpose

TBD

## Requirements

### Requirement: Almacenar fecha y hora de creación
El sistema DEBE almacenar la fecha y hora exactas (timestamp) cuando se crea una tarea.

#### Scenario: Tarea creada con timestamp de creación
- **WHEN** usuario autenticado con permiso 'write' crea una tarea exitosamente
- **THEN** la tarea tiene campo fechaCreacion con fecha y hora actual del sistema

#### Scenario: Timestamp de creación en formato ISO 8601
- **WHEN** se crea una tarea
- **THEN** el campo fechaCreacion está en formato ISO 8601 con fecha y hora (ejemplo: "2026-02-19T14:30:00")

### Requirement: Almacenar fecha y hora de resolución
El sistema DEBE almacenar la fecha y hora exactas (timestamp) cuando se completa una tarea.

#### Scenario: Tarea completada con timestamp de resolución
- **WHEN** usuario autenticado completa una tarea exitosamente
- **THEN** la tarea tiene campo fechaResolucion con fecha y hora actual del sistema

#### Scenario: Timestamp de resolución en formato ISO 8601
- **WHEN** se completa una tarea
- **THEN** el campo fechaResolucion está en formato ISO 8601 con fecha y hora (ejemplo: "2026-02-19T15:45:00")

### Requirement: Timestamp de resolución no se modifica
El sistema DEBE mantener la fecha y hora de resolución original si la tarea ya estaba completada.

#### Scenario: Completar tarea ya completada no modifica timestamp
- **WHEN** se intenta completar una tarea que ya tiene fechaResolucion
- **THEN** el campo fechaResolucion mantiene su valor original
- **AND** no se actualiza a la fecha/hora actual

### Requirement: Respuesta API incluye timestamps
El sistema DEBE incluir los campos fechaCreacion y fechaResolucion en las respuestas de la API.

#### Scenario: GET tareas incluye timestamps
- **WHEN** usuario autenticado obtiene la lista de tareas
- **THEN** cada tarea contiene los campos fechaCreacion y fechaResolucion
- **AND** si la tarea no está completada, fechaResolucion puede ser null

#### Scenario: POST crear tarea incluye fechaCreacion en respuesta
- **WHEN** usuario autenticado crea una tarea exitosamente
- **THEN** la respuesta contiene el campo fechaCreacion con la fecha y hora de creación