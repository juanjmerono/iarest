# Capability: task-completed-event

## ADDED Requirements

### Requirement: Publicar evento al completar tarea
El sistema DEBE publicar un evento de dominio `TareaCompletadaEvent` cuando se completa una tarea exitosamente.

#### Scenario: Evento publicado tras completar tarea
- **WHEN** un usuario autenticado con permiso 'write' completa una tarea exitosamente mediante PATCH a `/example/demo/todos/{uuid}/completar`
- **AND** la tarea se persiste correctamente
- **THEN** el sistema publica un evento `TareaCompletadaEvent` con los datos de la tarea

### Requirement: Contenido del evento TareaCompletadaEvent
El evento `TareaCompletadaEvent` DEBE contener la siguiente información:
- uuid: Identificador único de la tarea
- asunto: Descripción de la tarea
- fechaResolucion: Fecha y hora de resolución en formato ISO 8601
- usuarioId: ID del usuario que completó la tarea

#### Scenario: Evento contiene datos correctos
- **WHEN** se publica el evento `TareaCompletadaEvent`
- **THEN** el evento contiene los campos: uuid, asunto, fechaResolucion, usuarioId
- **AND** el campo uuid corresponde al UUID de la tarea
- **AND** el campo asunto contiene el valor de la tarea
- **AND** el campo fechaResolucion contiene la fecha y hora actual del sistema
- **AND** el campo usuarioId contiene el subject del JWT del usuario

### Requirement: Evento no se publica si la tarea ya está completada
El sistema NO DEBE publicar un nuevo evento si la tarea ya estaba completada (operación idempotente).

#### Scenario: No publicar evento al completar tarea ya completada
- **WHEN** usuario autenticado intenta completar una tarea que ya está COMPLETADA
- **THEN** NO se publica ningún evento `TareaCompletadaEvent`
- **AND** el sistema retorna código HTTP 200 OK
