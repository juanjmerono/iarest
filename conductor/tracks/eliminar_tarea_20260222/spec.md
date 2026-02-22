# Specification: Implementar la eliminación de tareas.

Este track aborda la necesidad de proporcionar una funcionalidad completa para la gestión del ciclo de vida de las tareas.

## Contexto
Actualmente, la API permite crear, listar y completar tareas, pero no existe un endpoint para eliminarlas definitivamente.

## Requisitos
1. **Endpoint**: `DELETE /example/demo/todos/{uuid}`
2. **Seguridad**: Solo usuarios autenticados con el scope `Tareas:write` pueden eliminar sus propias tareas.
3. **Persistencia**: La tarea debe ser eliminada físicamente de la base de datos (HSQLDB).
4. **Respuesta**:
   - `204 No Content` si se elimina correctamente.
   - `404 Not Found` si la tarea no existe o no pertenece al usuario.
   - `403 Forbidden` si no tiene los permisos adecuados.

## Arquitectura (CQRS)
- **Command**: `EliminarTareaCommand` (uuid, usuarioId)
- **Handler**: `EliminarTareaCommandHandler`
- **Domain**: Utilizar el puerto `TareaRepository` para ejecutar el borrado.
- **Adapter**: Implementar el método de borrado en `TareaRepositoryAdapter`.
