## 1. Tests BDD (TDD - Tests first)

- [x] 1.1 Crear archivo feature de Cucumber en español para obtener lista de tareas
- [x] 1.2 Implementar step definitions para los escenarios de spec
- [x] 1.3 Ejecutar tests y verificar que fallan (rojo)

## 2. Capa de Dominio

- [x] 2.1 Crear entidad Tarea en `domain/model/Tarea.java`
- [x] 2.2 Crear enum EstadoTarea con valores: pendiente, en_progreso, completada
- [x] 2.3 Crear puerto TareaRepository en `domain/port/TareaRepository.java`

## 3. Capa de Adaptadores (Persistencia)

- [x] 3.1 Crear adapter JPA TareaRepositoryAdapter
- [x] 3.2 Crear entity JPA TareaEntity
- [x] 3.3 Configurar mapeo entre TareaEntity y Tarea (Mapper)

## 4. Configuración de Tests con HSQLDB

- [x] 4.1 Añadir dependencia HSQLDB a pom.xml (scope test)
- [x] 4.2 Crear script SQL `test-data-todo-list.sql` para popular HSQLDB in-memory
- [x] 4.3 Configurar application-test.properties con HSQLDB in-memory
- [x] 4.4 Configurar Spring para ejecutar scripts SQL al iniciar test context
- [x] 4.5 Verificar que los tests usan la BD HSQLDB real (sin mocks)
- [x] 5.1 Crear servicio TareaService con método obtenerTodas()
- [x] 5.2 Crear DTO TodoResponse con campos: id, asunto, fecha, estado
- [x] 6.1 Crear controlador TodoController con endpoint GET /example/demo/todos
- [x] 6.2 Configurar serialización JSON con formato de fecha ISO 8601
- [x] 6.3 Ejecutar tests de integración y verificar pasan

## 7. Verificación Final

- [x] 7.1 Ejecutar todos los tests
- [x] 7.2 Compilar proyecto con `./mvnw compile`
- [x] 7.3 Documentar endpoint con OpenAPI/Swagger
