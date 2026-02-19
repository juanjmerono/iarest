## Why

Actualmente el sistema permite crear y obtener tareas, pero no existe forma de completar una tarea. Los usuarios necesitan un endpoint para marcar tareas como completadas, similar a como funciona la creación de tareas. Además, se necesita almacenar la fecha y hora exactas tanto de creación como de resolución de tareas para auditoría.

## What Changes

- Nuevo endpoint PATCH `/example/demo/todos/{uuid}/completar` para completar una tarea
- Nuevo evento de dominio `TareaCompletadaEvent` emitido al completar una tarea
- Modificar campo `fecha` existente de `LocalDate` a `LocalDateTime` para incluir hora
- Añadir campo `fechaResolucion` (LocalDateTime) para almacenar fecha y hora de resolución
- El endpoint debe ser idempotente: completar una tarea ya completada devuelve éxito sin error

## Capabilities

### New Capabilities
- `complete-task`: Endpoint REST para completar una tarea cambiando su estado a COMPLETADA
- `task-completed-event`: Evento de dominio publicado al completar una tarea

### Modified Capabilities
- `create-task`: Modificar campo `fecha` para incluir timestamp (fecha y hora)

## Impact

- **Código afectado**: 
  - `domain/model/Tarea` - Modificar campo `fecha` a LocalDateTime, añadir campo `fechaResolucion`
  - `adapter/persistence/TareaEntity` - Actualizar mapeo de campos de fecha
  - `adapter/rest/` - Nuevo controller para completar tareas
  - `application/command/` - Nuevo command handler y modificar existente
  - `application/dto/TodoResponse` - Añadir campo fechaResolucion
  - `domain/model/` - Nuevo evento TareaCompletadaEvent
- **APIs**: Nuevo endpoint `/example/demo/todos/{uuid}/completar`
- **Dependencias**: Ninguna nueva
