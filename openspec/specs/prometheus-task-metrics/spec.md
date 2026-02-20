# Métricas Prometheus de Tareas

## Propósito

TBD

## Requisitos

### Requisito: Métricas de Creación de Tareas
El sistema DEBE incrementar una métrica de tipo counter de Prometheus llamada `tasks_created_total` cada vez que se publique un evento de dominio de creación de tarea.

#### Escenario: Tarea creada exitosamente
- **WHEN** se crea una tarea y se publica el evento de dominio TaskCreated
- **THEN** el counter `tasks_created_total` DEBE incrementarse en 1

### Requisito: Métricas de Completado de Tareas
El sistema DEBE incrementar una métrica de tipo counter de Prometheus llamada `tasks_completed_total` cada vez que se publique un evento de dominio de completado de tarea.

#### Escenario: Tarea completada exitosamente
- **WHEN** se marca una tarea como completada y se publica el evento de dominio TaskCompleted
- **THEN** el counter `tasks_completed_total` DEBE incrementarse en 1

### Requisito: Métricas de Ratio de Completado
El sistema DEBE exponer una métrica de tipo gauge de Prometheus llamada `tasks_completion_ratio` que calcule el porcentaje de tareas completadas respecto a las creadas.

#### Escenario: Cálculo del ratio
- **WHEN** se consulta el endpoint de métricas
- **THEN** el gauge `tasks_completion_ratio` DEBE retornar (tasks_completed_total / tasks_created_total) * 100, o 0 si no hay tareas creadas

### Requisito: Exposición de Métricas
El sistema DEBE exponer todas las métricas de tareas a través del endpoint Prometheus de Spring Boot Actuator en `/actuator/prometheus`.

#### Escenario: Acceso al endpoint de métricas
- **WHEN** un scraper de Prometheus consulta `/actuator/prometheus`
- **THEN** la respuesta DEBE incluir las métricas relacionadas con tareas con el formato adecuado
