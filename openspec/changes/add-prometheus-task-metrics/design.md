## Context

El sistema actual de gestión de tareas no cuenta con métricas de monitoreo para las tasas de creación y finalización de tareas. Los eventos de dominio ya se están lanzando para creación y finalización de tareas, lo que proporciona una base sólida para implementar métricas sin modificar la lógica de negocio existente. Utilizaremos Micrometer (incluido en Spring Boot) para exponer métricas compatibles con Prometheus.

## Goals / Non-Goals

**Goals:**
- Capturar métricas de contador para tareas creadas y completadas basadas en eventos de dominio
- Calcular y exponer el ratio de tareas completadas vs creadas como una métrica gauge
- Integrar con el endpoint /actuator/prometheus existente de Spring Boot
- Mantener el impacto mínimo en el rendimiento y la arquitectura hexagonal

**Non-Goals:**
- Modificar APIs existentes o comportamientos de negocio
- Implementar alertas o dashboards (solo métricas base)
- Cambiar la estructura de eventos de dominio

## Decisions

**Marco de métricas:** Usar Micrometer con contadores para eventos de creación/completación y un gauge para el ratio calculado.
- **Rationale:** Micrometer es el estándar en Spring Boot para métricas, proporciona integración nativa con Prometheus y permite cálculos dinámicos sin almacenamiento persistente.
- **Alternatives considered:** Implementación personalizada de métricas vs Micrometer (rechazado por complejidad y mantenimiento).

**Ubicación en arquitectura:** Implementar en la capa de aplicación como event listeners, siguiendo el patrón hexagonal.
- **Rationale:** Los eventos de dominio se manejan en la capa de aplicación, manteniendo la separación de responsabilidades.
- **Alternatives considered:** En capa de infraestructura (rechazado por acoplamiento) vs dominio (rechazado por dependencias externas).

**Cálculo del ratio:** Ratio = tareas_completadas / tareas_creadas (como porcentaje o fracción).
- **Rationale:** Proporciona una métrica clara de eficiencia del sistema.
- **Alternatives considered:** Solo contadores individuales (insuficiente para ratio requerido).

## Risks / Trade-offs

**Riesgo de sobrecarga de rendimiento:** Los listeners de eventos podrían impactar ligeramente el rendimiento en alta carga.
- **Mitigación:** Usar operaciones asíncronas si es necesario, monitorear en producción.

**Dependencia en eventos de dominio:** Si los eventos cambian, las métricas podrían verse afectadas.
- **Mitigación:** Mantener tests integrados que verifiquen la emisión de métricas.

**Complejidad de configuración:** Requiere configuración de Prometheus endpoint.
- **Mitigación:** Documentar claramente en el README del proyecto.</content>
<parameter name="filePath">openspec/changes/add-prometheus-task-metrics/design.md