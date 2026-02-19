## 1. Configuración de métricas

- [ ] 1.1 Identificar eventos de dominio TaskCreated y TaskCompleted existentes
- [ ] 1.2 Verificar configuración de Micrometer y endpoint /actuator/prometheus

## 2. Implementación de listeners de eventos

- [ ] 2.1 Crear TaskMetricsEventListener en capa de aplicación
- [ ] 2.2 Implementar contador para tareas creadas (@EventListener para TaskCreated)
- [ ] 2.3 Implementar contador para tareas completadas (@EventListener para TaskCompleted)
- [ ] 2.4 Implementar gauge para ratio de completación usando MeterRegistry

## 3. Pruebas BDD con Cucumber

- [ ] 3.1 Crear feature file en español para métricas de tareas
- [ ] 3.2 Implementar step definitions para verificar contadores de métricas
- [ ] 3.3 Implementar step definitions para verificar gauge de ratio
- [ ] 3.4 Ejecutar tests de Cucumber con HSQLDB en memoria

## 4. Pruebas unitarias con TDD

- [ ] 4.1 Crear tests unitarios para TaskMetricsEventListener
- [ ] 4.2 Testear incrementos de contadores con eventos simulados
- [ ] 4.3 Testear cálculo de ratio en gauge
- [ ] 4.4 Verificar inyección correcta de MeterRegistry

## 5. Verificación de integración

- [ ] 5.1 Probar endpoint /actuator/prometheus expone métricas correctas
- [ ] 5.2 Ejecutar suite completa de tests (unit + integration + BDD)
- [ ] 5.3 Verificar compatibilidad con arquitectura hexagonal</content>
<parameter name="filePath">openspec/changes/add-prometheus-task-metrics/tasks.md