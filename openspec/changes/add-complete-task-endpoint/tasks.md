## 1. Tests TDD - Feature Completar Tarea (Primero)

- [ ] 1.1 Crear feature `complete-task.feature` con escenarios de prueba
- [ ] 1.2 Crear step definitions para completar tarea
- [ ] 1.3 Ejecutar tests - deben fallar (rojo)

## 2. Tests TDD - Evento TareaCompletadaEvent

- [ ] 2.1 Crear test para estructura del evento TareaCompletadaEvent
- [ ] 2.2 Crear test para verificar que se publica el evento
- [ ] 2.3 Ejecutar tests - deben fallar (rojo)

## 3. Tests TDD - Timestamps

- [ ] 3.1 Crear test para verificar campo fecha con hora
- [ ] 3.2 Crear test para verificar campo fechaResolucion
- [ ] 3.3 Ejecutar tests - deben fallar (rojo)

## 4. Implementar Modelo de Dominio (Verde)

- [ ] 4.1 Modificar `domain/model/Tarea.java`: cambiar `fecha` de LocalDate a LocalDateTime
- [ ] 4.2 Añadir campo `fechaResolucion` (LocalDateTime) a Tarea
- [ ] 4.3 Añadir getters/setters para fechaResolucion

## 5. Implementar Capa de Persistencia

- [ ] 5.1 Modificar `TareaEntity` para mapear nuevos tipos de datos
- [ ] 5.2 Verificar mapeo en `TareaRepositoryAdapter`

## 6. Modificar Command Handler de Crear

- [ ] 6.1 Modificar `CrearTareaCommand` para usar LocalDateTime
- [ ] 6.2 Modificar `CrearTareaCommandResult` para incluir fechaResolucion
- [ ] 6.3 Actualizar `TodoResponse` para incluir fechaResolucion

## 7. Implementar CompletarTareaCommandHandler

- [ ] 7.1 Crear `CompletarTareaCommand`
- [ ] 7.2 Crear `CompletarTareaCommandHandler`
- [ ] 7.3 Implementar lógica idempotente
- [ ] 7.4 Publicar evento solo si no estaba completada

## 8. Implementar Evento TareaCompletadaEvent

- [ ] 8.1 Crear `TareaCompletadaEvent` como record
- [ ] 8.2 Integrar en CompletarTareaCommandHandler

## 9. Implementar Endpoint REST

- [ ] 9.1 Añadir método en `TareaController` para PATCH `/example/demo/todos/{uuid}/completar`
- [ ] 9.2 Documentar con OpenAPI

## 10. Verificación Integral (Refactorizar)

- [ ] 10.1 Ejecutar `./mvnw test` - todos deben pasar
- [ ] 10.2 Ejecutar `./mvnw verify`
- [ ] 10.3 Revisar y refactorizar código si es necesario
