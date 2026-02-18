## Why

Actualmente la API permite crear y obtener tareas, pero todas las tareas son compartidas entre todos los usuarios. Se necesita que cada usuario tenga sus propias tareas privadas, de modo que al consultar la lista solo vea las tareas que él ha creado.

## What Changes

- Añadir campo `usuarioId` a la entidad Tarea para identificar el propietario
- Modificar el endpoint GET `/example/demo/todos` para filtrar tareas por usuario autenticado
- Modificar el endpoint POST `/example/demo/todos` para asociar la tarea al usuario autenticado
- Obtener el usuario del token JWT autenticado

## Capabilities

### New Capabilities
- `task-ownership`: Gestión de tareas por usuario

### Modified Capabilities
- `create-task`: Modificar para asociar tarea al usuario autenticado
- `get-tasks`: Modificar para filtrar tareas por usuario autenticado

## Impact

- Entidad `Tarea` - añadir campo usuarioId
- `TareaEntity` - añadir columna en base de datos
- Query handler `ObtenerTareasQueryHandler` - filtrar por usuario
- Command handler `CrearTareaCommandHandler` - asociar usuario al crear
- Tests BDD existentes - actualizar para múltiples usuarios
