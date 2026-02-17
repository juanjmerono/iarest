## 1. Tests BDD (TDD - Tests first)

- [ ] 1.1 Crear archivo feature de Cucumber en español para obtener lista de tareas
- [ ] 1.2 Implementar step definitions para los escenarios de spec
- [ ] 1.3 Ejecutar tests y verificar que fallan (rojo)

## 2. Capa de Dominio

- [ ] 2.1 Crear entidad Tarea en `domain/model/Tarea.java`
- [ ] 2.2 Crear enum EstadoTarea con valores: pendiente, en_progreso, completada
- [ ] 2.3 Crear puerto TareaRepository en `domain/port/TareaRepository.java`

## 3. Capa de Adaptadores (Persistencia)

- [ ] 3.1 Crear adapter JPA TareaRepositoryAdapter
- [ ] 3.2 Crear entity JPA TareaEntity
- [ ] 3.3 Configurar mapeo entre TareaEntity y Tarea (Mapper)

## 4. Configuración de Tests con HSQLDB

- [ ] 4.1 Añadir dependencia HSQLDB a pom.xml (scope test)
- [ ] 4.2 Crear script SQL `test-data-todo-list.sql` para popular HSQLDB in-memory
- [ ] 4.3 Configurar application-test.properties con HSQLDB in-memory
- [ ] 4.4 Configurar Spring para ejecutar scripts SQL al iniciar test context
- [ ] 4.5 Verificar que los tests usan la BD HSQLDB real (sin mocks)

## 5. Capa de Aplicación

- [ ] 5.1 Crear servicio TareaService con método obtenerTodas()
- [ ] 5.2 Crear DTO TodoResponse con campos: id, asunto, fecha, estado

## 6. Capa de Adaptadores (REST)

- [ ] 6.1 Crear controlador TodoController con endpoint GET /example/demo/todos
- [ ] 6.2 Configurar serialización JSON con formato de fecha ISO 8601
- [ ] 6.3 Ejecutar tests de integración y verificar pasan

## 7. Verificación Final

- [ ] 7.1 Ejecutar todos los tests
- [ ] 7.2 Compilar proyecto con `./mvnw compile`
- [ ] 7.3 Verificar que endpoint responde correctamente
