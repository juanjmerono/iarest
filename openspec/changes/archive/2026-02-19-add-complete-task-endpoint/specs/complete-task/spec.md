# Capability: complete-task

## ADDED Requirements

### Requirement: Completar tarea
El sistema DEBE permitir a usuarios autenticados con scope 'write' completar una tarea, cambiando su estado a COMPLETADA.

#### Scenario: Completar tarea exitosamente
- **WHEN** usuario autenticado con permiso 'write' envía PATCH a `/example/demo/todos/{uuid}/completar`
- **AND** la tarea existe y pertenece al usuario
- **THEN** el sistema retorna código HTTP 200 OK
- **AND** la respuesta contiene la tarea con estado 'COMPLETADA'
- **AND** la tarea tiene fechaResolucion establecida

#### Scenario: Completar tarea sin autenticación
- **WHEN** usuario envía PATCH a `/example/demo/todos/{uuid}/completar` sin token
- **THEN** el sistema retorna código HTTP 401 Unauthorized

#### Scenario: Completar tarea sin permiso write
- **WHEN** usuario autenticado con solo scope 'read' envía PATCH a `/example/demo/todos/{uuid}/completar`
- **THEN** el sistema retorna código HTTP 403 Forbidden

#### Scenario: Completar tarea que no existe
- **WHEN** usuario autenticado con permiso 'write' envía PATCH a `/example/demo/todos/inexistente/completar`
- **THEN** el sistema retorna código HTTP 404 Not Found

#### Scenario: Completar tarea de otro usuario
- **WHEN** usuario "user1" envía PATCH a `/example/demo/todos/{uuid}/completar` donde la tarea pertenece a "user2"
- **THEN** el sistema retorna código HTTP 404 Not Found

#### Scenario: Completar tarea ya completada (idempotente)
- **WHEN** usuario autenticado con permiso 'write' envía PATCH a `/example/demo/todos/{uuid}/completar` donde la tarea ya está COMPLETADA
- **THEN** el sistema retorna código HTTP 200 OK
- **AND** la respuesta contiene la tarea con estado 'COMPLETADA'
- **AND** la fechaResolucion no se modifica
