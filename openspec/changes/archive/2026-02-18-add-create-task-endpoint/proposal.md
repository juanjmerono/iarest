## Why

Actualmente la API solo permite obtener la lista de tareas (GET). Se necesita un endpoint para crear nuevas tareas, permitiendo a los usuarios autorizados añadir elementos a la lista de tareas.

## What Changes

- Nuevo endpoint REST POST `/example/demo/todos` para crear una tarea
- Autorización con scope `write` via OAuth2/JWT
- Respuesta 201 Created en caso de éxito con la tarea creada
- Integración con el repositorio existente `TareaRepository`
- **BREAKING**: El campo `fecha` se ignora en la request y se establece automáticamente a la fecha/hora actual del sistema
- **BREAKING**: El campo `estado` se ignora en la request y se establece automáticamente a `PENDIENTE`

## Capabilities

### New Capabilities
- `create-task`: Endpoint para crear nuevas tareas en la lista con validación básica

### Modified Capabilities
- (Ninguno - capability nueva)

## Impact

- Nuevo endpoint en `TodoController`
- Nuevo command handler en `application/command/`
- Nuevo DTO para request (e.g., `CreateTareaRequest`)
- Actualización de tests BDD con scenario para creación exitosa
- Actualización de permisos en `DemoMethodSecurity`
