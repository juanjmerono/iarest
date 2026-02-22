# Tech Stack - IA API Rest

## Core Technologies
- **Java 17**: Lenguaje de programación principal para la lógica de negocio y aplicación.
- **Spring Boot 4.0.2**: Framework base para la gestión de dependencias, configuración y servidor web embebido.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto (mvnw).

## Persistence & Security
- **Spring Data JPA**: Abstracción para el acceso a datos mediante el patrón repositorio.
- **HSQLDB In-Memory**: Base de datos ligera en memoria para desarrollo y pruebas rápidas sin dependencias externas.
- **Spring Security (OAuth2)**: Implementación de seguridad como Resource Server utilizando tokens JWT para la autenticación y autorización.

## API & Documentation
- **Spring HATEOAS**: Implementación de hipermedia para el Nivel 3 de madurez REST.
- **Springdoc OpenAPI (Swagger UI)**: Generación automática de documentación técnica de la API para humanos y agentes.

## Quality & Monitoring
- **Cucumber (BDD)**: Pruebas de comportamiento basadas en escenarios en lenguaje natural (Gherkin).
- **JUnit 5**: Pruebas unitarias robustas e integración.
- **JaCoCo**: Generación de informes detallados sobre la cobertura de pruebas de código.
- **Micrometer + Prometheus**: Recolección de métricas operacionales mediante Spring Actuator.
