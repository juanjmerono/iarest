## 1. Capa de Dominio y Puerto

- [ ] 1.1 Crear clase de evento `TareaEliminadaEvent` en `domain/model/`
- [ ] 1.2 Añadir método `deleteByUuid(String uuid)` en interfaz `TareaRepository` (domain/port/)
- [ ] 1.3 Implementar método `deleteByUuid` en `TareaRepositoryAdapter` (adapter/persistence/)

## 2. Capa de Aplicación (Command)

- [ ] 2.1 Crear clase `EliminarTareaCommand` en `application/command/`
- [ ] 2.2 Crear clase `EliminarTareaCommandResult` (record dentro de command handler o separado)
- [ ] 2.3 Crear clase `EliminarTareaCommandHandler` en `application/command/`
- [ ] 2.4 Registrar `EliminarTareaCommandHandler` como componente Spring

## 3. Capa de Adapter REST

- [ ] 3.1 Añadir método DELETE en `TodoController.java` en endpoint `/todos/{uuid}`
- [ ] 3.2 Añadir documentación OpenAPI con anotaciones @Operation y @ApiResponses
- [ ] 3.3 Configurar seguridad con @PreAuthorize y scope Tareas:write

## 4. Tests

- [ ] 4.1 Crear test BDD Cucumber para escenario "Eliminar tarea exitosamente"
- [ ] 4.2 Crear test BDD para escenario "Eliminar tarea inexistente retorna 404"
- [ ] 4.3 Crear test BDD para escenario "Eliminar tarea de otro usuario retorna 404"
- [ ] 4.4 Crear test unitario para `EliminarTareaCommandHandler`

## 5. Verificación

- [ ] 5.1 Ejecutar tests con `./mvnw test`
- [ ] 5.2 Verificar coverage con JaCoCo (objetivo >- [ ] 80%)
5.3 Verificar que Swagger/OpenAPI muestra el nuevo endpoint
