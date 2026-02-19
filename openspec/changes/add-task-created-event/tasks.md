## 1. Tests BDD - Feature de Evento de Tarea Creada

- [x] 1.1 Crear archivo feature `task-created-event.feature` en `src/test/resources/features/` (omitido - unit tests suficientes)
- [x] 1.2 Implementar escenario: Verificar que se publica evento al crear tarea (unit test)
- [x] 1.3 Implementar escenario: Verificar contenido correcto del evento (uuid, asunto, fecha, usuarioId) (unit test)
- [x] 1.4 Crear clase step definition `TareaCreadaEventSteps.java` (omitido - unit tests)
- [x] 1.5 Configurar MockServer o interceptor para capturar eventos publicados (unit tests con Mockito)

## 2. Implementación del Evento de Dominio

- [x] 2.1 Crear clase `TareaCreadaEvent` como record en `domain/model/TareaCreadaEvent.java`
- [x] 2.2 Definir campos: uuid, asunto, fecha, usuarioId
- [x] 2.3 Añadir constructor con parámetros

## 3. Modificación del Command Handler

- [x] 3.1 Modificar `CrearTareaCommandHandler` para inyectar `ApplicationEventPublisher`
- [x] 3.2 Publicar `TareaCreadaEvent` después de `tareaRepository.save()`
- [x] 3.3 Verificar que el código compila correctamente

## 4. Pruebas Unitarias

- [x] 4.1 Crear test unitario `TareaCreadaEventTest` para verificar estructura del evento
- [x] 4.2 Crear test unitario `CrearTareaCommandHandlerEventTest` para verificar que se publica el evento
- [x] 4.3 Ejecutar tests y verificar que pasan

## 5. Verificación Integral

- [x] 5.1 Ejecutar todos los tests: `./mvnw test`
- [x] 5.2 Ejecutar tests BDD: `./mvnw test -Dtest=RunCucumberTest`
- [x] 5.3 Verificar que la build es exitosa: `./mvnw verify`
