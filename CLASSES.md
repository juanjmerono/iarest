# Diagrama de Clases

```mermaid
classDiagram
    %% Domain Layer
    class EstadoTarea {
        <<enumeration>>
        +pendiente
        +en_progreso
        +completada
    }

    class Tarea {
        -uuid: String
        -asunto: String
        -fecha: LocalDateTime
        -estado: EstadoTarea
        -usuarioId: String
        -fechaResolucion: LocalDateTime
    }

    class TareaRepository {
        <<interface>>
        +findAll(): List~Tarea~
        +findByUsuarioId(String): List~Tarea~
        +findByUuid(String): Optional~Tarea~
        +save(Tarea): Tarea
    }

    class TareaCreadaEvent {
        -uuid: String
        -asunto: String
        -fecha: LocalDateTime
        -usuarioId: String
    }

    class TareaCompletadaEvent {
        -uuid: String
        -fechaResolucion: LocalDateTime
        -usuarioId: String
    }

    %% Application Layer
    class CrearTareaCommandHandler {
        -tareaRepository: TareaRepository
        -eventPublisher: ApplicationEventPublisher
        +handle(CrearTareaRequest, String): CrearTareaCommandResult
    }

    class CompletarTareaCommandHandler {
        -tareaRepository: TareaRepository
        -eventPublisher: ApplicationEventPublisher
        +handle(CompletarTareaCommand): Optional~CompletarTareaCommandResult~
    }

    class ObtenerTareasQueryHandler {
        -tareaRepository: TareaRepository
        +handle(ObtenerTareasQuery, String): List~Tarea~
    }

    class CrearTareaRequest {
        -asunto: String
    }

    class TodoResponse {
        -uuid: String
        -asunto: String
        -fecha: LocalDateTime
        -estado: String
        -usuarioId: String
        -fechaResolucion: LocalDateTime
    }

    class CrearTareaCommand {
        +fromRequest(CrearTareaRequest, String): CrearTareaCommand
    }

    class CompletarTareaCommand {
        -uuid: String
        -usuarioId: String
    }

    class ObtenerTareasQuery {
        <<record>>
    }

    %% Adapter Layer
    class TodoController {
        -queryHandler: ObtenerTareasQueryHandler
        -crearTareaCommandHandler: CrearTareaCommandHandler
        -completarTareaCommandHandler: CompletarTareaCommandHandler
        -assembler: TodoModelAssembler
    }

    class TareaRepositoryAdapter {
        -jpaRepository: TareaJpaRepository
        +findAll(): List~Tarea~
        +findByUsuarioId(String): List~Tarea~
        +findByUuid(String): Optional~Tarea~
        +save(Tarea): Tarea
    }

    class TareaEntity {
        -uuid: String
        -asunto: String
        -fecha: LocalDateTime
        -estado: EstadoTarea
        -usuarioId: String
        -fechaResolucion: LocalDateTime
    }

    class TodoModelAssembler {
        +toModel(TodoResponse): EntityModel~TodoResponse~
        +toDecoratedCollectionModel(List~Tarea~): CollectionModel~EntityModel~TodoResponse~~
    }

    class TareaJpaRepository {
        <<interface>>
        +findAll(): List~TareaEntity~
        +findByUsuarioId(String): List~TareaEntity~
        +findByUuid(String): Optional~TareaEntity~
    }

    %% Relationships
    Tarea --> EstadoTarea
    TareaEntity --> EstadoTarea
    
    TareaRepository <|.. TareaRepositoryAdapter
    
    TareaRepositoryAdapter --> TareaJpaRepository
    TareaRepositoryAdapter --> TareaEntity
    TareaRepositoryAdapter ..> Tarea : maps
    
    TodoController --> CrearTareaCommandHandler
    TodoController --> CompletarTareaCommandHandler
    TodoController --> ObtenerTareasQueryHandler
    TodoController --> TodoModelAssembler
    
    CrearTareaCommandHandler --> TareaRepository
    CrearTareaCommandHandler --> TareaCreadaEvent
    CrearTareaCommandHandler --> CrearTareaRequest
    CrearTareaCommandHandler --> TodoResponse
    
    CompletarTareaCommandHandler --> TareaRepository
    CompletarTareaCommandHandler --> TareaCompletadaEvent
    CompletarTareaCommandHandler --> CompletarTareaCommand
    
    ObtenerTareasQueryHandler --> TareaRepository
    ObtenerTareasQueryHandler --> ObtenerTareasQuery
    
    TodoModelAssembler --> TodoResponse
```

## Descripción de Capas

### Domain Layer
- **Tarea**: Entidad principal del dominio
- **EstadoTarea**: Enumeración con los estados: pendiente, en_progreso, completada
- **TareaRepository**: Interfaz del puerto de repository
- **TareaCreadaEvent / TareaCompletadaEvent**: Eventos del dominio

### Application Layer
- **CrearTareaCommandHandler**: Maneja la creación de tareas
- **CompletarTareaCommandHandler**: Maneja la completado de tareas
- **ObtenerTareasQueryHandler**: Maneja la obtención de tareas
- **DTOs**: CrearTareaRequest, TodoResponse

### Adapter Layer
- **TodoController**: Controlador REST
- **TareaRepositoryAdapter**: Adaptador que implementa TareaRepository
- **TareaEntity**: Entidad JPA
- **TodoModelAssembler**: Ensamblador HATEOAS
