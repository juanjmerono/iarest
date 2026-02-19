## 1. Tests TDD - Feature Completar Tarea (Primero)

- [x] 1.1 Crear feature `complete-task.feature` con escenarios de prueba
- [x] 1.2 Crear step definitions para completar tarea
- [x] 1.3 Ejecutar tests - deben fallar (rojo)

## 2. Tests TDD - Evento TareaCompletadaEvent

- [x] 2.1 Crear test para estructura del evento TareaCompletadaEvent
- [x] 2.2 Crear test para verificar que se publica el evento
- [x] 2.3 Ejecutar tests - deben fallar (rojo)

## 3. Tests TDD - Timestamps

- [x] 3.1 Crear test para verificar campo fecha con hora
- [x] 3.2 Crear test para verificar campo fechaResolucion
- [x] 3.3 Ejecutar tests - deben fallar (rojo)

## 4. Implementar Modelo de Dominio (Verde)

- [x] 4.1 Modificar `domain/model/Tarea.java`: cambiar `fecha` de LocalDate a LocalDateTime
- [x] 4.2 Añadir campo `fechaResolucion` (LocalDateTime) a Tarea
- [x] 4.3 Añadir getters/setters para fechaResolucion

## 5. Implementar Capa de Persistencia

- [x] 5.1 Modificar `TareaEntity` para mapear nuevos tipos de datos
- [x] 5.2 Verificar mapeo en `TareaRepositoryAdapter`

## 6. Modificar Command Handler de Crear

- [x] 6.1 Modificar `CrearTareaCommand` para usar LocalDateTime
- [x] 6.2 Modificar `CrearTareaCommandResult` para incluir fechaResolucion
- [x] 6.3 Actualizar `TodoResponse` para incluir fechaResolucion

## 7. Implementar CompletarTareaCommandHandler

- [x] 7.1 Crear `CompletarTareaCommand`
- [x] 7.2 Crear `CompletarTareaCommandHandler`
- [x] 7.3 Implementar lógica idempotente
- [x] 7.4 Publicar evento solo si no estaba completada

## 8. Implementar Evento TareaCompletadaEvent

- [x] 8.1 Crear `TareaCompletadaEvent` como record
- [x] 8.2 Integrar en CompletarTareaCommandHandler

## 9. Implementar Endpoint REST

- [x] 9.1 Añadir método en `TareaController` para PATCH `/example/demo/todos/{uuid}/completar`
- [x] 9.2 Documentar con OpenAPI

## 10. Verificación Integral (Refactorizar)

- [x] 10.1 Ejecutar `./mvnw test` - todos deben pasar
- [x] 10.2 Ejecutar `./mvnw verify`
- [x] 10.3 Revisar y refactorizar código si es necesario
