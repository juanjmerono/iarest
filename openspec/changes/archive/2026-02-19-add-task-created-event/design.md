## Context

El sistema actual permite crear tareas (Tarea) a través del comando `CrearTareaCommandHandler`, que utiliza el `TareaRepository` para persistir. No existe actualmente ningún mecanismo para notificar a otros componentes cuando se crea una tarea.

La arquitectura actual sigue el patrón de puertos y adaptadores (Hexagonal):
- Dominio: `domain/model/Tarea`, `domain/port/TareaRepository`
- Aplicación: `application/command/CrearTareaCommandHandler`
- Adaptadores: `adapter/persistence/TareaRepositoryAdapter`

## Goals / Non-Goals

**Goals:**
- Implementar evento de dominio `TareaCreadaEvent` que se emite tras persistir una tarea
- El evento contendrá: uuid, asunto, fecha, usuarioId
- Usar `ApplicationEventPublisher` de Spring para publicar el evento
- Mantener el desacoplamiento entre la creación de tareas y los consumidores del evento

**Non-Goals:**
- No implementar consumidores del evento (queda fuera del scope)
- No modificar la API REST existente
- No añadir persistencia del evento (solo en memoria via Spring)
- No implementar eventos para actualización o eliminación de tareas

## Decisions

### Decisión 1: Usar Spring ApplicationEventPublisher vs EventBus propio

**Opción elegida**: `ApplicationEventPublisher` de Spring

**Alternativas consideradas**:
- EventBus propio (guava): Añadiría dependencia externa
- Spring ApplicationEvent: Integración nativa con Spring, sin dependencias extra

**Justificación**: Spring ya está en el classpath y proporciona capacidades suficientes para eventos de dominio simples.

### Decisión 2: Dónde publicar el evento

**Opción elegida**: En el `CrearTareaCommandHandler` después de invocar `tareaRepository.save()`

**Alternativas consideradas**:
- En el adapter de persistencia: Acoplaría el evento a la infraestructura
- En el dominio (constructor Tarea): violaría principios DDD (entidades no deben publicar eventos)

**Justificación**: El CommandHandler es el lugar adecuado ya que coordina la lógica de aplicación y tiene acceso tanto al dominio como a la infraestructura.

### Decisión 3: Estructura del evento como record

**Opción elegida**: Java 17 record

**Alternativas consideradas**:
- Clase con getters/setters: Más verbosa
- Map: Sin type-safety

**Justificación**: Un record es inmutable, conciso, y perfecto para un evento de dominio que no debe modificarse tras su creación.

## Risks / Trade-offs

- [Riesgo] Los eventos Spring son síncronos por defecto: Si un consumidor falla, podría afectar la respuesta al cliente.
  - **Mitigación**: Mantener los consumidores simples y con manejo de errores robusto. Para procesamiento asíncrono futuro, considerar `@Async` o un EventBus externo.
  
- [Riesgo] Acoplamiento temporal: El evento se emite después del save pero antes de confirmar la transacción.
  - **Mitigación**: Usar `@TransactionalEventListener` con fase `AFTER_COMMIT` si se necesita garantía de persistencia.

## Migration Plan

1. Crear clase `TareaCreadaEvent` en `domain/model/`
2. Modificar `CrearTareaCommandHandler` para inyectar `ApplicationEventPublisher` y publicar el evento
3. Añadir tests unitarios para verificar que el evento se publica correctamente
4. No hay cambios en la API ni necesidad de rollback

## Open Questions

- ¿Se necesita que el evento sea listened asynchronously? (pendiente de requisitos futuros)
- ¿Debe el evento incluir información adicional como IP del cliente o metadata?
