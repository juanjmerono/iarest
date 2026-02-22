# Product Guidelines - IA API Rest

## General Principles
- **IA-First Design**: Cada componente de código y documentación debe ser claro y explícito para facilitar el análisis por parte de agentes de IA.
- **Transparencia Arquitectónica**: Mantener una separación estricta de responsabilidades (Dominio, Aplicación, Adaptadores) en todo momento.
- **Descubribilidad**: La API debe ser auto-descriptiva y proporcionar enlaces a recursos relacionados (HATEOAS).

## Code & Prose Style
- **Idioma**: Toda la documentación y las respuestas de la API deben estar en español, tal como se especifica en las restricciones del proyecto. El código (nombres de variables, clases, métodos) también debe ser coherente con este idioma, aunque las palabras clave de Java se mantengan en inglés.
- **Claridad Semántica**: Los nombres de los métodos y clases deben reflejar acciones de negocio claras (ej: `CompletarTarea` en lugar de `UpdateStatus`).
- **Documentación de Código**: Utilizar comentarios de Javadoc para explicar la lógica de negocio compleja y las anotaciones de OpenAPI para documentar los endpoints REST.

## User Experience (UX)
- **Consistencia**: Las respuestas de error deben ser uniformes y proporcionar información útil tanto para humanos como para depuración por agentes.
- **Idempotencia**: Las operaciones de actualización, como completar una tarea, deben ser idempotentes para garantizar la consistencia en entornos distribuidos o reintentos automáticos.

## Quality Assurance (QA)
- **Cobertura de Tests**: Se espera una cobertura de tests significativa (>80%) para asegurar que las modificaciones no introduzcan regresiones.
- **BDD como Fuente de Verdad**: Los escenarios de Cucumber en lenguaje natural son la descripción definitiva del comportamiento esperado de la aplicación.
