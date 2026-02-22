## Context

El proyecto es una API REST para gestión de tareas (ToDo) con arquitectura hexagonal/ports & adapters. Actualmente soporta:
- Crear tareas (POST `/todos`)
- Listar tareas (GET `/todos`)
- Completar tareas (PATCH `/todos/{uuid}/completar`)

No existe funcionalidad para eliminar tareas. El usuario necesita poder eliminar tareas que ya no son relevantes.

## Goals / Non-Goals

**Goals:**
- Añadir endpoint DELETE `/todos/{uuid}` para eliminar una tarea
- Retornar 204 No Content si la eliminación es exitosa
- Retornar 404 Not Found si la tarea no existe o no pertenece al usuario
- Verificar que el usuario autenticado es el propietario de la tarea
- Publicar evento de dominio `TareaEliminadaEvent` tras la eliminación
- Tests BDD que cubran el escenario de eliminación

**Non-Goals:**
- Soft delete (eliminación física de la base de datos)
- Eliminación en cascada de entidades relacionadas
- Batch deletion (eliminar múltiples tareas a la vez)
- Trash/recycle bin functionality
- Undelete capability

## Decisions

### 1. DELETE HTTP method para eliminación
**Decisión:** Usar método HTTP DELETE en lugar de POST con acción.

**Alternativas consideradas:**
- POST `/todos/{uuid}/eliminar`: Más verbose, menos RESTful
- PATCH con campo `eliminado: true` (soft delete): No implementado por ahora

**Rationale:** DELETE es el método estándar REST para eliminación de recursos y es idempotente.

### 2. Eliminación física vs soft delete
**Decisión:** Eliminación física directa.

**Alternativas consideradas:**
- Soft delete con campo `fechaEliminacion`: Añade complejidad, requiere filtros en todas las queries

**Rationale:** Simplicidad. El dominio no requiere mantener historial de tareas eliminadas.

### 3. Patrón de diseño: Command Pattern
**Decisión:** Crear `EliminarTareaCommand` y `EliminarTareaCommandHandler` siguiendo el patrón CQRS existente.

**Alternativas consideradas:**
-直接在 Controller: Violaría la arquitectura hexagonal

**Rationale:** Consistencia con operaciones existentes (CrearTareaCommandHandler, CompletarTareaCommandHandler).

### 4. Evento de dominio asíncrono
**Decisión:** Usar `ApplicationEventPublisher` de Spring para publicar `TareaEliminadaEvent`.

**Alternativas consideradas:**
- Eventos síncronos: Mismo comportamiento actual
- No publicar evento: Pierde capacidad de auditoría/métricas

**Rationale:** Consistencia con eventos existentes (TareaCreadaEvent, TareaCompletadaEvent) y permite listener para métricas.

## Risks / Trade-offs

- **[Riesgo] Eliminar tarea con comentarios/subtareas asociadas** → Por ahora no hay entidades relacionadas, pero si se añaden en futuro, habría que definir comportamiento.
- **[Riesgo] Carrera entre completar y eliminar** → El handler verificará existencia antes de operar, operación atómica.
- **[Trade-off] 204 No Content no retorna cuerpo** → No es posible retornar la tarea eliminada. Si se necesitara, usar 200 con respuesta.

## Migration Plan

1. Crear `EliminarTareaCommand.java` y `EliminarTareaCommandResult.java`
2. Crear `EliminarTareaCommandHandler.java`
3. Crear evento `TareaEliminadaEvent.java`
4. Añadir método `deleteByUuid` en `TareaRepository` (interfaz) y `TareaRepositoryAdapter` (implementación)
5. Añadir endpoint DELETE en `TodoController.java`
6. Añadir test BDD para escenario "Eliminar tarea"
7. Actualizar documentación OpenAPI (automático con Springdoc)

**Rollback:** Revertir cambios en controller, handler, command y evento. No hay cambios en modelo de datos.
