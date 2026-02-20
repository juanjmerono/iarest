## Why

Necesitamos métricas de Prometheus para monitorear el rendimiento del sistema de tareas. Actualmente, no tenemos visibilidad sobre las tasas de creación y finalización de tareas, lo que nos impide identificar patrones de uso y posibles cuellos de botella. Implementar métricas basadas en eventos de dominio nos permitirá calcular el ratio de tareas completadas vs creadas, proporcionando insights valiosos para la mejora continua del sistema.

## What Changes

- Añadir colección de métricas de Prometheus basada en eventos de dominio de creación y finalización de tareas
- Implementar cálculo automático del ratio de tareas completadas/creadas
- Exponer métricas a través del endpoint de Prometheus de Spring Boot
- No hay cambios disruptivos en APIs existentes

## Capabilities

### New Capabilities
- `prometheus-task-metrics`: Sistema de métricas que captura eventos de dominio para calcular ratios de tareas creadas y completadas

### Modified Capabilities
<!-- No existing capabilities are modified -->

## Impact

- Código afectado: Capa de dominio (eventos), capa de aplicación (manejadores de eventos), configuración de Spring Boot
- Nuevas dependencias: Ninguna adicional (Micrometer ya incluido en Spring Boot)
- APIs afectadas: Ninguna
- Sistemas: Endpoint de métricas de Prometheus (/actuator/prometheus)</content>
<parameter name="filePath">openspec/changes/add-prometheus-task-metrics/proposal.md