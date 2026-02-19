## 1. Tests BDD - Feature de Evento de Tarea Creada

- [ ] 1.1 Crear archivo feature `task-created-event.feature` en `src/test/resources/features/`
- [ ] 1.2 Implementar escenario: Verificar que se publica evento al crear tarea
- [ ] 1.3 Implementar escenario: Verificar contenido correcto del evento (uuid, asunto, fecha, usuarioId)
- [ ] 1.4 Crear clase step definition `TareaCreadaEventSteps.java` en `src/test/java/es/um/example/demo/steps/`
- [ ] 1.5 Configurar MockServer o interceptor para capturar eventos publicados

## 2. Implementación del Evento de Dominio

- [ ] 2.1 Crear clase `TareaCreadaEvent` como record en `domain/model/TareaCreadaEvent.java`
- [ ] 2.2 Definir campos: uuid, asunto, fecha, usuarioId
- [ ] 2.3 Añadir constructor con parámetros

## 3. Modificación del Command Handler

- [ ] 3.1 Modificar `CrearTareaCommandHandler` para inyectar `ApplicationEventPublisher`
- [ ] 3.2 Publicar `TareaCreadaEvent` después de `tareaRepository.save()`
- [ ] 3.3 Verificar que el código compila correctamente

## 4. Pruebas Unitarias

- [ ] 4.1 Crear test unitario `TareaCreadaEventTest` para verificar estructura del evento
- [ ] 4.2 Crear test unitario `CrearTareaCommandHandlerEventTest` para verificar que se publica el evento
- [ ] 4.3 Ejecutar tests y verificar que pasan

## 5. Verificación Integral

- [ ] 5.1 Ejecutar todos los tests: `./mvnw test`
- [ ] 5.2 Ejecutar tests BDD: `./mvnw test -Dtest=RunCucumberTest`
- [ ] 5.3 Verificar que la build es exitosa: `./mvnw verify`
