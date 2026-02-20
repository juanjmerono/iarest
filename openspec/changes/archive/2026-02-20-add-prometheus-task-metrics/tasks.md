## 1. Configuración de métricas

- [x] 1.1 Identificar eventos de dominio TaskCreated y TaskCompleted existentes
- [x] 1.2 Verificar configuración de Micrometer y endpoint /actuator/prometheus

## 2. Implementación de listeners de eventos

- [x] 2.1 Crear TaskMetricsEventListener en capa de aplicación
- [x] 2.2 Implementar contador para tareas creadas (@EventListener para TaskCreated)
- [x] 2.3 Implementar contador para tareas completadas (@EventListener para TaskCompleted)
- [x] 2.4 Implementar gauge para ratio de completación usando MeterRegistry

## 3. Pruebas BDD con Cucumber

- [x] 3.1 Crear feature file en español para métricas de tareas
- [x] 3.2 Implementar step definitions para verificar contadores de métricas
- [x] 3.3 Implementar step definitions para verificar gauge de ratio
- [x] 3.4 Ejecutar tests de Cucumber con HSQLDB en memoria
- [x] 4.1 Crear tests unitarios para TaskMetricsEventListener
- [x] 4.2 Testear incrementos de contadores con eventos simulados
- [x] 4.3 Testear cálculo de ratio en gauge
- [x] 4.4 Verificar inyección correcta de MeterRegistry

## 5. Verificación de integración

- [x] 5.1 Probar endpoint /actuator/prometheus expone métricas correctas
- [x] 5.2 Ejecutar suite completa de tests (unit + integration + BDD)
- [x] 5.3 Verificar compatibilidad con arquitectura hexagonal</content>
<parameter name="filePath">openspec/changes/add-prometheus-task-metrics/tasks.md