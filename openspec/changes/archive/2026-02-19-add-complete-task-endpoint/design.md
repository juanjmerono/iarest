## Context

El sistema actual permite crear tareas con fecha (solo fecha, sin hora) y obtener la lista de tareas. No existe endpoint para completar tareas ni almacenamiento de timestamps con hora exacta. El modelo Tarea tiene: uuid, asunto, fecha (LocalDate), estado, usuarioId.

## Goals / Non-Goals

**Goals:**
- Implementar endpoint PATCH `/example/demo/todos/{uuid}/completar` para completar tareas
- Implementar evento de dominio `TareaCompletadaEvent` tras completar exitosamente
- Modificar campo `fecha` existente de `LocalDate` a `LocalDateTime` para incluir hora
- Añadir campo `fechaResolucion` (LocalDateTime) al modelo Tarea
- Hacer el endpoint idempotente: si la tarea ya está completada, devolver éxito

**Non-Goals:**
- No implementar endpoint para reopen/completar tareas
- No implementar actualización de otros campos de la tarea
- No modificar la estructura de la entity JPA (TareaEntity) de forma incompatible

## Decisions

### Decisión 1: Endpoint REST para completar tarea

**Opción elegida**: PATCH `/example/demo/todos/{uuid}/completar`

**Alternativas consideradas**:
- PUT `/example/demo/todos/{uuid}`: Too broad, would allow updating all fields
- POST `/example/demo/todos/{uuid}/complete`: Less RESTful for a state transition

**Justificación**: PATCH es el verbo correcto para actualizar parcialmente un recurso, y la acción "completar" es clara en la URL.

### Decisión 2: Formato de timestamps

**Opción elegida**: `LocalDateTime` para fechaCreacion y fechaResolucion

**Alternativas consideradas**:
- `LocalDate` (actual): Solo fecha, sin hora
- `Instant` con timezone: Más complejo, overkill para el caso de uso

**Justificación**: El requisito especifica "fecha y hora", y LocalDateTime es más legible que Instant para timestamps de negocio.

### Decisión 3: Idempotencia

**Opción elegida**: Devolver 200 OK si la tarea ya está completada

**Alternativas consideradas**:
- 204 No Content: Funciona pero 200 es más consistente
- 400 Bad Request: Incorrecto, no es un error del cliente
- 409 Conflict: No aplica, no hay conflicto de estado

**Justificación**: Es comportamiento idempotente válido - completar una tarea ya completada no es un error, es una operación exitosa que no tiene efecto.

### Decisión 4: Cuándo setear fechaCreacion

**Opción elegida**: En el constructor de Tarea, asignar LocalDateTime.now()

**Alternativas consideradas**:
- En la capa de persistencia: Acoplaría lógica de dominio a infraestructura
- En el controller: Violaría principio de thin controller

**Justificación**: El dominio es responsable de su propio estado. El constructor o un factory method debe inicializar la fecha de creación.

## Risks / Trade-offs

- [Riesgo] La fecha de creación solo se setea al crear - si se modifica el campo, podría perder trazabilidad
  - **Mitigación**: Hacer los campos finales (no setters públicos) o documentar que no deben modificarse

- [Riesgo] Timestamp de resolución debe setearse solo una vez
  - **Mitigación**: Solo asignar si es null, no sobrescribir si ya tiene valor

## Migration Plan

1. Modificar modelo Tarea: añadir campos fechaCreacion, fechaResolucion
2. Modificar TareaEntity: mapear nuevos campos a columnas existentes o nuevas
3. Crear CompletarTareaCommandHandler
4. Crear controller endpoint
5. Crear TareaCompletadaEvent
6. Modificar CrearTareaCommandHandler para setear fechaCreacion
7. Tests unitarios y de integración
8. No hay cambios en API que requieran migration - campos nuevos son opcionales en respuestas

## Open Questions

- ¿El endpoint debe verificar propiedad de la tarea (que el usuario solo pueda completar sus propias tareas)?
- ¿Debe incluirse la hora en el campo `fecha` existente o solo en los nuevos campos de timestamp?
