---
description: Audita la evolución de cobertura de tests del proyecto
#agent: build
#model: anthropic/claude-3-5-sonnet-20241022
subtask: true
---

Ejecuta los tests del proyecto y registra en el archivo `QA.md` un resumen del resultado.

**Instrucciones**

1. Compila y ejecuta los tests `./mvnw clean install`

2. Recupera los resultados de número de tests ejecutados con éxito, fallidos o ignorados

3. Recupera el procentaje de cobertura de instrucciones y ramas del fichero `target/site/jacoco/index.html`

4. Indica los datos actualizados en el apartado "Estado atual" del fichero `QA.md` con el formato:

```
Tests: <número de tests total> ejecutados, <número exitosos> exitosos, <número fallidos> fallidos, <número ignorados> ignorados
- Cucumber: <número de tests con cucumber>
- JUnit: <número de tests con junit>

Cobertura:
- Instrucciones: <porcentaje de cobertura de instrucciones>%
- Ramas: <porcentaje de cobertura de ramas>%
```

5. Incluye una entrada en el apartado "Detalle de evolución" del fichero `QA.md` indicando si se ha producido una mejora incrementando el número y/o porcentaje de cobertura de tests o un empeoramiento al bajar alguno de estos valores, con el formato:

```
**<fecha y hora actual>**:
- **<MEJORA/EMPEORA>**: <Texto resumen detallando con datos la mejoría o empeoramiento respecto al estado previo>
```
