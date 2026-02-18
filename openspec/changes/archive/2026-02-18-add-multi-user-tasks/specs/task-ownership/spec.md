## ADDED Requirements

### Requirement: Tareas propias del usuario
El sistema DEBE permitir que cada usuario vea y gestione únicamente sus propias tareas.

#### Scenario: Obtener tareas del usuario autenticado
- **WHEN** usuario autenticado con subject "user1" consulta la lista de tareas
- **THEN** solo retorna las tareas creadas por "user1"

#### Scenario: Crear tarea asociada al usuario
- **WHEN** usuario autenticado con subject "user1" crea una tarea
- **THEN** la tarea se asocia automáticamente a "user1"
- **AND** la tarea aparece en las consultas posteriores de "user1"

#### Scenario: Usuario no ve tareas de otros
- **WHEN** usuario "user1" crea una tarea
- **AND** usuario "user2" consulta la lista de tareas
- **THEN** usuario "user2" no ve la tarea creada por "user1"

## MODIFIED Requirements

### Requirement: Obtener lista de tareas
El sistema DEBE permitir a usuarios autenticados obtener su lista de tareas. Modificado para filtrar por usuario autenticado.

#### Scenario: Obtener la lista de tareas autenticado
- **WHEN** usuario autenticado con scope "read" consulta la lista de tareas
- **THEN** el sistema retorna código HTTP 200
- **AND** la respuesta contiene solo las tareas del usuario autenticado
- **AND** cada tarea contiene los campos: uuid, asunto, fecha, estado, usuarioId

### Requirement: Crear tarea
El sistema DEBE permitir a usuarios autenticados con scope 'write' crear nuevas tareas. Modificado para asociar automáticamente el usuario.

#### Scenario: Crear tarea exitosamente
- **WHEN** usuario autenticado "pepe" con permiso "write" crea una tarea con asunto "Nueva tarea"
- **THEN** el sistema retorna código HTTP 201 Created
- **AND** la tarea contiene el campo usuarioId igual al subject del JWT del usuario
