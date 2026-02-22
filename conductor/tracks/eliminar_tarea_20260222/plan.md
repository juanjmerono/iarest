# Implementation Plan: Implementar la eliminación de tareas.

Este plan detalla los pasos para implementar la funcionalidad de eliminación definitiva de tareas siguiendo un enfoque **TDD** y arquitectura **Hexagonal/CQRS**.

---

## Fase 1: Dominio y Puertos
- [ ] **Task: Definir el puerto de eliminación**
    - [ ] Añadir método `void deleteByIdAndUsuarioId(String uuid, String usuarioId)` al puerto `TareaRepository`.
- [ ] **Task: Crear tests de dominio (TDD)**
    - [ ] Añadir escenarios de Cucumber en `src/test/resources/features/eliminar-tarea.feature`.
- [ ] Task: Conductor - User Manual Verification 'Fase 1: Dominio y Puertos' (Protocol in workflow.md)

## Fase 2: Aplicación (Write Side)
- [ ] **Task: Crear Command y Handler**
    - [ ] Implementar `EliminarTareaCommand` (DTO/Record).
    - [ ] Implementar `EliminarTareaCommandHandler` para coordinar el borrado.
- [ ] **Task: Tests unitarios del Handler (TDD)**
    - [ ] Crear test `EliminarTareaCommandHandlerTest` y verificar el comportamiento del borrado.
- [ ] Task: Conductor - User Manual Verification 'Fase 2: Aplicación' (Protocol in workflow.md)

## Fase 3: Adaptadores de Infraestructura y REST
- [ ] **Task: Implementar Adaptador de Persistencia**
    - [ ] Modificar `TareaRepositoryAdapter` y `TareaJpaRepository` para soportar el borrado condicional.
- [ ] **Task: Implementar Endpoint REST**
    - [ ] Añadir método con `@DeleteMapping` en `TodoController`.
    - [ ] Documentar el endpoint con OpenAPI (`@Operation`, `@ApiResponse`).
- [ ] **Task: Verificación Final (BDD)**
    - [ ] Ejecutar todos los tests (Cucumber y JUnit) con `./mvnw test`.
    - [ ] Verificar que la cobertura se mantiene por encima del 80%.
- [ ] Task: Conductor - User Manual Verification 'Fase 3: Adaptadores y REST' (Protocol in workflow.md)
