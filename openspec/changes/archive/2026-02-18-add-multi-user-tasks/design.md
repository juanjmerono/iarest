## Context

El proyecto actual permite crear y obtener tareas sin distinción de usuario. Todas las tareas son visibles y editables por cualquier usuario autenticado.

**Estado actual:**
- Entidad `Tarea` con campos: uuid, asunto, fecha, estado
- GET retorna todas las tareas sin filtrar por usuario
- POST crea tareas sin asociar a un usuario

**Restricciones:**
- Usar el subject del JWT como identificador de usuario
- Mantener arquitectura hexagonal
- No cambiar la estructura de la API existente (mismos endpoints)

## Goals / Non-Goals

**Goals:**
- Añadir campo `usuarioId` a las tareas
- Filtrar tareas por usuario autenticado en GET
- Asociar automáticamente el usuario autenticado al crear tareas

**Non-Goals:**
- Autenticación de usuarios (ya existe con OAuth2/JWT)
- Compartir tareas entre usuarios
- Permisos de edición/eliminación entre usuarios

## Decisions

### 1. Identificador de usuario
**Decisión:** Usar el `subject` del JWT como `usuarioId`.

**Alternativas:**
- Generar ID interno: Añadiría complejidad innecesaria
- Usar email del usuario: Puede cambiar, subject es estable

**Rationale:** El subject del JWT es único y estable por usuario.

### 2. Obtención del usuario en handlers
**Decisión:** Pasar el usuario desde el controller al handler via el request/command.

**Alternativas:**
- Inyectar SecurityContext en handler: Acopla lógica de seguridad al handler
- Usar ThreadLocal: Difícil de testear

**Rationale:** Separación de responsabilidades, más testeable.

### 3. Filtrado en repository
**Decisión:** Añadir método `findByUsuarioId` en el repository.

**Alternativas:**
- Filtrar en memoria: Ineficiente para grandes volúmenes
- Filtrar en query: Más eficiente con índice en BD

**Rationale:** Mejor rendimiento con filtrado en BD.

## Risks / Trade-offs

- [Risk] Datos existentes sin usuarioId → [Mitigation] Asignar a usuario "system" o filtrar con NULL
- [Risk] Tests existentes fallando → [Mitigation] Actualizar BDD tests para usar usuario específico
- [Risk] Rendimiento con muchos usuarios → [Mitigation] Añadir índice en columna usuarioId
