## Why

Actualmente las tareas utilizan un identificador numérico secuencial (Long) generado automáticamente por la base de datos. Se necesita cambiar a UUID para mejorar la seguridad y evitar la exposición de información secuencial (enumeration attacks), además de permitir una mejor distribución en sistemas distribuidos.

## What Changes

- Cambiar el tipo del campo `id` de `Long` a `UUID` en la entidad `Tarea`
- Actualizar la generación del ID para usar UUID aleatorio en lugar de secuencial
- **BREAKING**: El endpoint GET ahora retorna `uuid` en lugar de `id` numérico
- **BREAKING**: El endpoint POST ahora retorna la tarea creada con `uuid`

## Capabilities

### New Capabilities
- `task-uuid-id`: Identificador único universal (UUID) para las tareas

### Modified Capabilities
- `create-task`: Modificar para retornar `uuid` en lugar de `id`
- `get-tasks`: Modificar para incluir `uuid` en la respuesta

## Impact

- Entidad `Tarea` - cambio de tipo de id
- `TareaEntity` - cambio de tipo y estrategia de generación
- DTOs `TodoResponse` - cambio de `id` (Long) a `uuid` (String)
- Repositorio y adaptadores JPA
- Tests BDD existentes que verifican campo `id`
