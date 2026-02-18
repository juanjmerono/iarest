## 1. Tests BDD (TDD - empezar con tests fallando)

- [x] 1.1 Crear archivo feature: `crear-tarea.feature` en `src/test/resources/features/`
- [x] 1.2 Definir steps en `CrearTareaStepsDefinition.java` extendiendo `GenericStepsDefinition`

## 2. Capa de Dominio y Puerto

- [x] 2.1 Verificar que `TareaRepository` tiene método `save()` (heredado de JpaRepository)

## 3. Capa de Aplicación

- [x] 3.1 Crear `CrearTareaRequest` DTO en `application/dto/` (solo campo 'asunto', ignorar fecha y estado)
- [x] 3.2 Crear `CrearTareaCommand` en `application/command/` (establecer fecha=now, estado=PENDIENTE)
- [x] 3.3 Crear `CrearTareaCommandHandler` en `application/command/`

## 4. Capa de Adapter REST

- [x] 4.1 Añadir método POST en `TodoController` con `@PostMapping`
- [x] 4.2 Añadir `@PreAuthorize("hasPermission('Tareas','write')")`
- [x] 4.3 Documentar con anotaciones OpenAPI (`@Operation`, `@ApiResponses`)
- [x] 4.4 Añadir enlace HATEOAS en el response del POST (self link)
- [x] 4.5 Añadir enlace "create" en el GET endpoint (CollectionModel) para indicar que se pueden crear tareas

## 5. Seguridad

- [x] 5.1 Verificar que `DemoPermissionEvaluator` soporta acción 'write'

## 6. Verificación

- [x] 6.1 Ejecutar `./mvnw test` y verificar que pasan
- [x] 6.2 Verificar con `./mvnw verify`
