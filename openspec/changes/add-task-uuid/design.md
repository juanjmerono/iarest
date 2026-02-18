## Context

El proyecto actual usa identificadores numéricos secuenciales (Long) para las tareas. Este cambio introduce UUIDs para mejorar la seguridad y permitir distribución.

**Estado actual:**
- Entidad `Tarea` con `id: Long`
- `TareaEntity` con `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- DTO `TodoResponse` con campo `id: Long`

**Restricciones:**
- Mantener compatibilidad con la API existente en estructura
- Usar arquitectura hexagonal
- Minimizar cambios en la lógica de negocio

## Goals / Non-Goals

**Goals:**
- Cambiar tipo de `id` (Long) a `uuid` (String/UUID)
- Generar UUID automáticamente al crear tarea
- Actualizar todos los DTOs y respuestas

**Non-Goals:**
- Migración de datos existentes (se asume base de datos vacía o reinicializable)
- Cambiar otros identificadores del sistema
- Implementar UUID en otras entidades

## Decisions

### 1. Estrategia de generación UUID
**Decisión:** Usar `UUID.randomUUID()` en aplicación (no a nivel de BD).

**Alternativas:**
- `GenerationType.UUID`: No soportado nativamente por JPA/Hibernate
- `GenerationType.AUTO`: Dependiente de la BD, puede no ser consistente

**Rationale:** Generar en Java garantiza formato consistente independientemente de la BD.

### 2. Nombre del campo en API
**Decisión:** Usar `uuid` (String) en lugar de `id` para la API externa.

**Alternativas:**
- Mantener `id` como String: Confuso para consumidores
- Usar ambos (`id` numérico interno + `uuid` externo): Mayor complejidad

**Rationale:** Claridad para clientes API - `uuid` es auto-descriptivo.

### 3. Tipo en DTO
**Decisión:** `uuid` como `String` en DTO (serialización JSON).

**Alternativas:**
- Usar tipo `UUID` de Java: Requiere configuración de serialización
- Usar `String`: Más simple, estándar en APIs REST

**Rationale:** Simplicidad y compatibilidad con OpenAPI/Swagger.

## Risks / Trade-offs

- [Risk] Tests fallando por cambio de campo `id` a `uuid` → [Mitigation] Actualizar steps y features de Cucumber
- [Risk] Longitud del UUID ocupa más espacio → [Mitigation] Aceptable, 36 chars vs ~19 digits
- [Risk] Rendimiento en queries por UUID vs entero → [Mitigation] Generalmente aceptable, usar índice en BD
