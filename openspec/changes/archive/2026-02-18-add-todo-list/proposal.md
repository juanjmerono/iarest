## Why

Se necesita un servicio REST simple para gestionar una lista de tareas (TODO list) que permita a los usuarios obtener sus tareas pendientes con información básica: asunto, fecha y estado. Este es el primer endpoint del servicio y sentará las bases para futuras funcionalidades de gestión de tareas.

## What Changes

- Crear endpoint GET `/example/demo/todos` que retorna la lista de tareas
- Definir estructura de datos para Tarea con campos: asunto, fecha, estado
- Implementar modelo de dominio, controlador REST y servicio
- Configurar respuesta JSON con formato estándar

## Capabilities

### New Capabilities
- `todo-list-api`: Endpoint para obtener la lista de tareas con asunto, fecha y estado

### Modified Capabilities
- (Ninguno - es el primer endpoint del servicio)

## Impact

- Nuevo código: modelo Tarea, TodoController, TodoService, TodoRepository
- Nueva dependencia: Spring Data JPA (ya configurado en pom.xml)
- Sin impacto en sistemas existentes
