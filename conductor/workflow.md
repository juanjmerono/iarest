# Workflow - IA API Rest

Este documento describe el flujo de trabajo de desarrollo para el proyecto IA API Rest utilizando Conductor.

## Development Cycle
El desarrollo sigue un ciclo iterativo de **Planificar -> Actuar -> Validar**.

### Test-Driven Development (TDD)
- Se debe seguir un enfoque de TDD. 
- Para cada nueva funcionalidad o cambio, se deben escribir primero los tests que definan el comportamiento esperado antes de implementar la solución.

### Code Coverage
- El nivel mínimo de cobertura de código requerido es del **80%**.
- Se debe utilizar JaCoCo para verificar la cobertura tras la ejecución de los tests.

## Commit Strategy
- Los cambios se deben confirmar mediante commits de Git tras la finalización de cada **Fase** del plan de implementación.
- Los mensajes de commit deben seguir la convención del proyecto e incluir el resumen de las tareas completadas en el cuerpo del mensaje.

## Documentation
- Todas las funcionalidades deben estar documentadas en OpenAPI y acompañadas de sus respectivos escenarios de Cucumber.
