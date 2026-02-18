## Context

Proyecto base de API REST para Universidad de Murcia. Se implementa el primer endpoint del servicio de gestión de tareas (TODO list). El servicio retornará la lista de tareas con asunto, fecha y estado.

Entorno actual:
- Spring Boot 4.0.2 con Java 17
- Maven como herramienta de build
- Oracle DB como base de datos
- Spring Data JPA para persistencia
- Patrón Hexagonal/Clean Architecture

## Goals / Non-Goals

**Goals:**
- Crear endpoint GET `/example/demo/todos` que retorne lista de tareas
- Implementar modelo de dominio Tarea con campos: id, asunto, fecha, estado
- Configurar respuesta JSON con formato estándar
- Sentar bases para futuras funcionalidades CRUD

**Non-Goals:**
- Autenticación/autorización (OAuth2 pendiente)
- Crear, actualizar o eliminar tareas (futuros endpoints)
- Paginación y filtros (v2)
- Documentación OpenAPI (v2)

## Decisions

1. **Estructura de paquetes**: Siguiendo Clean Architecture
   - `es.um.example.demo.domain.model` - Entidad Tarea
   - `es.um.example.demo.domain.port` - Puerto de repository
   - `es.um.example.demo.adapter.persistence` - Adaptador JPA
   - `es.um.example.demo.application` - Servicio de aplicación
   - `es.um.example.demo.adapter.rest` - Controlador REST

2. **Formato de fecha**: ISO 8601 (yyyy-MM-dd) usando `LocalDate`

3. **Estados**: Enum con valores "pendiente", "en_progreso", "completada"

4. **Framework de persistencia**: Spring Data JPA con Repository pattern

## Risks / Trade-offs

- [Riesgo] Sin datos de prueba → [Mitigación] Crear datos iniciales con Flyway o insert SQL
- [Riesgo] Sin paginación → [Mitigación] Aceptable para v1, iterable futuro
