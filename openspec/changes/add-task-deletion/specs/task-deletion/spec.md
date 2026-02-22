## ADDED Requirements

### Requirement: Eliminar tarea por UUID
El sistema SHALL permitir a los usuarios eliminar una tarea existente mediante su identificador único (UUID).

#### Scenario: Eliminación exitosa de tarea propia
- **WHEN** el usuario autenticado envía una solicitud DELETE a `/todos/{uuid}` con un UUID de tarea que le pertenece
- **THEN** el sistema elimina la tarea de la base de datos y retorna código de estado 204 No Content

#### Scenario: Eliminación de tarea inexistente
- **WHEN** el usuario autenticado envía una solicitud DELETE a `/todos/{uuid}` con un UUID que no existe en el sistema
- **THEN** el sistema retorna código de estado 404 Not Found

#### Scenario: Eliminación de tarea de otro usuario
- **WHEN** el usuario autenticado envía una solicitud DELETE a `/todos/{uuid}` con un UUID de tarea que pertenece a otro usuario
- **THEN** el sistema retorna código de estado 404 Not Found

#### Scenario: Eliminación sin autenticación
- **WHEN** un usuario no autenticado envía una solicitud DELETE a `/todos/{uuid}`
- **THEN** el sistema retorna código de estado 401 Unauthorized

#### Scenario: Eliminación sin autorización
- **WHEN** el usuario autenticado envía una solicitud DELETE a `/todos/{uuid}` pero no tiene el scope Tareas:write
- **THEN** el sistema retorna código de estado 403 Forbidden

### Requirement: Publicar evento tras eliminación
El sistema SHALL publicar un evento de dominio `TareaEliminadaEvent` después de eliminar una tarea exitosamente.

#### Scenario: Evento publicado correctamente
- **WHEN** el sistema elimina una tarea exitosamente
- **THEN** se publica un evento TareaEliminadaEvent con el UUID de la tarea, asunto, usuarioId y fecha de eliminación
