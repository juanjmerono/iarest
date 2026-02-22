## Why

Actualmente la API permite crear, listar y actualizar tareas, pero no existe forma de eliminarlas. Los usuarios necesitan poder eliminar tareas que ya no son relevantes para mantener sus listas organizadas y reducir clutter.

## What Changes

- Nuevo endpoint DELETE `/api/tareas/{id}` para eliminar una tarea por su ID
- Retorno 204 No Content si la eliminación es exitosa
- Retorno 404 Not Found si la tarea no existe
- Retorno 401 Unauthorized si el usuario no está autenticado

## Capabilities

### New Capabilities
- `task-deletion`: Capability para eliminar tareas existentes via API REST

### Modified Capabilities
- (Ninguno - es una capability nueva)

## Impact

- Nuevo endpoint en la capa de API REST
- Nuevo comando/handler en la capa de aplicación
- Posible evento de dominio `TareaEliminadaEvent`
- Tests BDD para el escenario de eliminación
- Documentación OpenAPI actualizada
