## Context

El proyecto actual es una API REST para gestión de tareas con arquitectura hexagonal. Actualmente solo existe el endpoint GET para obtener tareas. Se necesita añadir capacidad de creación de tareas.

**Estado actual:**
- GET `/example/demo/todos` - obtener todas las tareas (scope: read)
- Solo existe `ObtenerTareasQueryHandler` en capa de aplicación
- Entidad `Tarea` con campos: id, asunto, fecha, estado
- Seguridad OAuth2/JWT con permission evaluator personalizado

**Restricciones:**
- Mantener arquitectura hexagonal (ports & adapters)
- Usar patrón CQRS (Command/Query handlers)
- Integrarse con repositorio JPA existente

## Goals / Non-Goals

**Goals:**
- Añadir endpoint POST para crear tareas
- Autorización con scope `write`
- Retornar 201 Created con la tarea creada
- Validación básica de datos de entrada
- `fecha` se establece automáticamente a fecha/hora actual del sistema
- `estado` se establece automáticamente a `PENDIENTE`

**Non-Goals:**
- Actualización de tareas (PUT/PATCH) - fuera de scope
- Eliminación de tareas - fuera de scope
- Validación avanzada de reglas de negocio complejas

## Decisions

### 1. Command Handler para creación
**Decisión:** Crear `CrearTareaCommandHandler` en lugar de usar el controller directamente.

**Alternativas consideradas:**
- Lógica en controller: No siguen patrón CQRS del proyecto
- Usar servicio intermedio: Añadiría capa innecesaria

**Rationale:** Mantiene consistencia con el patrón existente de query handlers.

### 2. DTO para Request
**Decisión:** Crear `CrearTareaRequest` como record con validación.

**Alternativas:**
- Usar la entidad directamente: Viola separación de capas
- Mapstruct automático: Añade complejidad innecesaria

**Rationale:** Permite validación básica y separación clara entre API y dominio.

### 2. DTO para Request
**Decisión:** Crear `CrearTareaRequest` como record con solo el campo `asunto`. Los campos `fecha` y `estado` se ignoran en la request.

**Alternativas:**
- Incluir fecha y estado en request: Permite flexibilidad pero viola principio de que fecha de creación debe ser automática
- Usar la entidad directamente: Viola separación de capas

**Rationale:** Permite validación básica y separación clara entre API y dominio. La fecha de creación debe ser siempre la actual (no retrospectiva), y el estado inicial siempre es PENDIENTE.

### 3. Campos automáticos: fecha y estado
**Decisión:** El campo `fecha` se ignora en la request y se establece a `LocalDate.now()`. El campo `estado` se ignora en la request y se establece a `EstadoTarea.PENDIENTE`.

**Rationale:** La fecha de creación debe ser precisa al momento de la creación real. El estado inicial por defecto es PENDIENTE por definición del dominio.

### 4. Retorno con HATEOAS
**Decisión:** Retornar `EntityModel<TodoResponse>` con enlaces HATEOAS.

**Rationale:** Consistencia con endpoint GET existente que usa `CollectionModel<EntityModel<TodoResponse>>`.

## Risks / Trade-offs

- [Risk] Validación de datos básica → [Mitigation] Usar Jakarta Validation annotations (@NotBlank, @NotNull)
- [Risk] Integridad con estado inicial → [Mitigation] Estado siempre será PENDIENTE por diseño
- [Risk] Fecha no recibida → [Mitigation] Se establece automáticamente a LocalDate.now()
