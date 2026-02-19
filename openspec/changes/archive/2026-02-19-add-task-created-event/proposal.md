## Why

Actualmente, el sistema de gestión de tareas no proporciona ningún mecanismo para notificar a otros componentes cuando se crea una nueva tarea. Esto dificulta la implementación de funcionalidades como sincronización con sistemas externos, logging de auditoría, o reacciones automáticas ante la creación de tareas. Añadir un evento de dominio permitirá desacoplar la creación de tareas de otras operaciones business.

## What Changes

- Implementar evento de dominio `TareaCreadaEvent` que se emite cuando se crea una nueva tarea
- El evento contendrá: ID de la tarea, asunto, fecha de creación, usuario creador
- El evento se publicará mediante un `ApplicationEventPublisher` de Spring
- No se modifican los endpoints REST existentes ni la API pública

## Capabilities

### New Capabilities
- `task-created-event`: Capacidad de emitir y subscribe a eventos de dominio cuando se crea una tarea. Permite a otros componentes reaccionar a la creación de tareas de forma desacoplada.

### Modified Capabilities
- (ninguno - no se modifican requisitos existentes)

## Impact

- **Código afectado**: 
  - `domain/model/` - Nueva clase `TareaCreadaEvent`
  - `adapter/persistence/` - Modificar repositorio o servicio para publicar el evento
  - `application/` - Donde se maneje la creación de tareas
- **APIs**: Sin cambios en la API REST
- **Dependencias**: Ninguna nueva (usa Spring Framework existente)
- **Pruebas**: Añadir tests para verificar que el evento se emite correctamente
