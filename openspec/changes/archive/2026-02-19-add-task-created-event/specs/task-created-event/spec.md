# Capability: task-created-event

## ADDED Requirements

### Requirement: Publicar evento al crear tarea
El sistema DEBE publicar un evento de dominio `TareaCreadaEvent` cuando se crea una nueva tarea exitosamente.

#### Scenario: Evento publicado tras creación exitosa
- **WHEN** un usuario autenticado con permiso 'write' crea una tarea exitosamente mediante POST a /example/demo/todos
- **AND** la tarea se persiste correctamente en la base de datos
- **THEN** el sistema publica un evento `TareaCreadaEvent` con los datos de la tarea creada

### Requirement: Contenido del evento TareaCreadaEvent
El evento `TareaCreadaEvent` DEBE contener la siguiente información de la tarea creada:
- uuid: Identificador único de la tarea
- asunto: Descripción de la tarea
- fecha: Fecha de creación en formato ISO 8601 (yyyy-MM-dd)
- usuarioId: ID del usuario que creó la tarea

#### Scenario: Evento contiene datos正确os
- **WHEN** se publica el evento `TareaCreadaEvent`
- **THEN** el evento contiene los campos: uuid, asunto, fecha, usuarioId
- **AND** el campo uuid corresponde al UUID asignado a la tarea
- **AND** el campo asunto contiene el valor enviado en la petición
- **AND** el campo fecha contiene la fecha actual del sistema
- **AND** el campo usuarioId contiene el subject del token JWT del usuario

### Requirement: Suscriptor puede reaccionar al evento
El sistema DEBE permitir que componentes subscribed al evento `TareaCreadaEvent` puedan reaccionar a la creación de tareas.

#### Scenario: Suscriptor recibe el evento
- **WHEN** existe un componente registrado como listener de `TareaCreadaEvent`
- **AND** se crea una nueva tarea
- **THEN** el listener recibe el evento con los datos de la tarea creada

### Requirement: Evento no altera la respuesta API
La publicación del evento DEBE ser transparente para el cliente que creó la tarea. El evento no debe afectar el tiempo de respuesta ni el contenido de la respuesta HTTP.

#### Scenario: Respuesta API no afectada por evento
- **WHEN** un usuario crea una tarea exitosamente
- **THEN** el tiempo de respuesta no se ve incrementado significativamente por la publicación del evento
- **AND** la respuesta HTTP contiene el código 201 Created como siempre
- **AND** la respuesta incluye la tarea creada con sus datos
