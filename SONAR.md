# SonarQube Analysis Report

**Project:** demo (IA Rest - Universidad de Murcia)  
**Date:** 2026-02-20  
**Lines of Code:** ~1,100

---

## Resumen Ejecutivo

| Métrica | Valor |
|---------|-------|
| **Puntuación** | **D** |
| Problemas Totales | 27 |
| Critical | 2 |
| High | 3 |
| Medium | 12 |
| Low | 10 |
| Deuda Técnica | ~4 horas |

---

## Problemas de Seguridad (8)

### Critical

| # | Archivo | Línea | Problema |
|---|---------|-------|----------|
| 1 | `src/main/resources/application.properties` | 34 | **Actuator endpoints expuestos públicamente** - `management.endpoints.web.exposure.include=*` expone `/env`, `/heapdump`, `/threaddump` sin autenticación |
| 2 | `src/main/java/.../DemoPermissionEvaluator.java` | 29-36 | **Bypass de permisos** - La lógica `\|\| isUMUser` permite a cualquier usuario con `@um.es` evadir los controles de permiso |

### High

| # | Archivo | Línea | Problema |
|---|---------|-------|----------|
| 3 | `src/main/resources/application.properties` | 3 | **URL de API hardcodeada** - `cas.url=https://apitest.um.es` apunta a entorno de test |
| 4 | `src/main/resources/application.properties` | 28 | **Password de BD vacía** - `spring.datasource.password=` sin valor |
| 5 | `src/main/java/.../CompletarTareaCommandHandler.java` | 28-36 | **Information Leakage** - Mismo error 404 para UUIDs inexistentes y ajenos (permite enumeración) |

### Medium

| # | Archivo | Línea | Problema |
|---|---------|-------|----------|
| 6 | `src/main/java/.../DemoApplication.java` | 17 | CSRF deshabilitado (aceptable para APIs stateless) |
| 7 | `src/main/java/.../TodoController.java` | 102 | **Sin validación de UUID** - `@PathVariable String uuid` acepta cualquier string |
| 8 | `src/main/resources/application.properties` | 15 | Client ID hardcodeado en Swagger OAuth |

---

## Problemas de Rendimiento (3)

### Medium

| # | Archivo | Problema |
|---|---------|----------|
| 1 | `TareaEntity.java` | **Falta de índices** - `USUARIO_ID` y `UUID` no tienen índices, causará full table scans |
| 2 | `TodoController.java:62-65` | **Sin paginación** - `obtenerTareas` retorna todas las tareas del usuario |

### Low

| # | Archivo | Problema |
|---|---------|----------|
| 3 | `ObtenerTareasQueryHandler.java` | Creación ineficiente de objetos en stream |

---

## Code Smells (6)

### Medium

| # | Archivo | Problema |
|---|---------|----------|
| 1 | `TareaEntity.java` | Clase grande con 16 getters/setters sin encapsulamiento |
| 2 | `Tarea.java` + `TareaEntity.java` | Duplicación de modelo de dominio y entidad |

### Low

| # | Archivo | Problema |
|---|---------|----------|
| 3 | Múltiples | Magic strings para permisos hardcodeados |
| 4 | `DemoPermissionEvaluator.java:3` | Import no usado (`Serializable`) |
| 5 | `CrearTareaCommandHandler.java:48` | Nombre completamente cualificado (`java.time.LocalDateTime`) |
| 6 | Varios | Mezcla de records y clases regulares (diseño inconsistente) |

---

## Mejores Prácticas (10)

### Medium

| # | Archivo | Problema |
|---|---------|----------|
| 1 | `CrearTareaRequest.java` | **Sin validación de tamaño** - Falta `@Size(max=...)` (potencial DoS) |
| 2 | `CrearTareaCommandHandler.java` | **Sin @Transactional** - Guardado y publicación de eventos no atómicos |
| 3 | `CompletarTareaCommandHandler.java` | **Sin @Transactional** |
| 4 |全局 | **Sin @ControllerAdvice** - No hay manejo centralizado de excepciones |

### Low

| # | Archivo | Problema |
|---|---------|----------|
| 5 | `TareaRepositoryAdapter.java:45-63` | Sin validación de null en `toEntity()` |
| 6 | `TodoModelAssembler.java:21-22` | Paso de null para construir enlaces HATEOAS |
| 7 | `TodoController.java` | Sin estrategia de versionado de API |
| 8 | `application-test.properties` | `ddl-auto=none` vs `update` en producción |
| 9 | `TareaRepository.java:8` | Método `findAll()` no usado (dead code) |
| 10 | `OpenApiConfig.java:24` | `openIdConnectUrl` puede fallar si el issuer no está disponible |

---

## Cobertura de Tests

| Métrica | Valor |
|---------|-------|
| Cobertura de código | **91%** |
| Líneas cubiertas | 68% |
| Instrucciones cubiertas | 91% |
| Clases cubiertas | 100% |

---

## Puntuación Final: **D**

### Justificación

- **2 vulnerabilidades críticas de seguridad** que requieren solución inmediata
- **3 problemas de alto impacto** que exponen datos o permiten evasion de permisos
- Ausencia de paginación y índices que impactarán rendimiento a escala
- Deuda técnica significativa en validación y transacciones

### Recomendaciones Inmediatas

1. **CORREGIR** `DemoPermissionEvaluator.java` - Eliminar el bypass `|| isUMUser`
2. **CONFIGURAR** actuator con autenticación o exponer solo endpoints seguros
3. **AÑADIR** validación de tamaño en DTOs
4. **AÑADIR** `@Transactional` a los command handlers
5. **IMPLEMENTAR** paginación en listados

---

*Generado automáticamente - Análisis estático de código*
