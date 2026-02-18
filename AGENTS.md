# AGENTS.md - IA Rest Project Guidelines

## Project Overview

This is a Spring Boot 4.0.2 REST API project with hexagonal architecture (ports and adapters), DDD patterns, OAuth2/JWT security, and Cucumber BDD testing.

**Tech Stack:**
- Java 17
- Spring Boot 4.0.2
- Maven (wrapper: `./mvnw`)
- Spring Data JPA + HSQLDB (test)
- Spring Security OAuth2 Resource Server
- SpringDoc OpenAPI (Swagger UI)
- Cucumber 7.x for BDD testing
- JUnit 6.x

---

## Build / Lint / Test Commands

### Build & Run
```bash
./mvnw clean install      # Build and install to local repo
./mvnw package            # Build JAR only
./mvnw spring-boot:run   # Run the application
```

### Testing
```bash
./mvnw test                    # Run all tests (unit + integration)
./mvnw verify                  # Run tests + verify
```

**Run a single test class:**
```bash
./mvnw test -Dtest=RunCucumberTest
./mvnw test -Dtest=SomeUnitTestClass
```

**Run a single test method:**
```bash
./mvnw test -Dtest=MyTestClass#myTestMethod
```

**Run only Cucumber BDD tests:**
```bash
./mvnw test -Dtest=RunCucumberTest
```

### Code Quality
```bash
./mvnw compile     # Compile only
./mvnw clean       # Clean build artifacts
```

---

## Code Style Guidelines

### Project Structure (Hexagonal Architecture)

```
src/main/java/es/um/example/demo/
├── adapter/
│   ├── persistence/    # JPA repositories, entities
│   └── rest/           # Controllers, model assemblers
├── application/
│   ├── dto/            # Data transfer objects
│   ├── command/        # Command handlers (if used)
│   └── query/          # Query handlers
├── config/             # Spring configuration classes
├── domain/
│   ├── model/          # Domain entities, value objects
│   └── port/           # Repository interfaces (ports)
└── DemoApplication.java
```

### Naming Conventions

- **Packages:** Lowercase, e.g., `es.um.example.demo.adapter.rest`
- **Classes:** PascalCase, e.g., `TodoController`, `TareaEntity`
- **Methods:** camelCase, e.g., `obtenerTareas()`, `findAll()`
- **Variables:** camelCase, e.g., `queryHandler`, `tareaRepository`
- **Constants:** UPPER_SNAKE_CASE
- **Spanish:** Code uses Spanish naming (e.g., `ObtenerTareasQuery`, `Tarea`, `asunto`)

### Import Order

1. Java/Jakarta EE imports
2. Spring framework imports
3. Third-party libraries (Jackson, Swagger, etc.)
4. Project imports (es.um.example.demo.*)
5. Blank line between groups

Example:
```java
import java.time.LocalDate;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;

import es.um.example.demo.application.dto.TodoResponse;
```

### Formatting

- **Indentation:** 4 spaces (no tabs)
- **Line length:** Max ~120 characters
- **Braces:** Same-line opening braces
- **Blank lines:** Between logical sections (imports, class body, methods)
- **No trailing whitespace**

### Types & Generics

- Use Java 17+ features (records, `List.of()`, pattern matching where appropriate)
- Prefer `List<T>` over raw `ArrayList`
- Use `var` for local variable type inference when type is obvious
- Return immutable collections where possible: `.toList()` instead of `.collect(Collectors.toList())`

### Error Handling

- Use Spring's `@ControllerAdvice` for global exception handling (not currently present - can be added)
- Return proper HTTP status codes (200, 201, 400, 401, 403, 404, 500)
- Use `@ResponseStatus` annotations where appropriate

### REST API Conventions

- Use HATEOAS: `CollectionModel<EntityModel<TodoResponse>>`
- Follow RESTful conventions: `/example/demo/todos` for collection
- Use `@RequestMapping` at class level, HTTP verbs at method level
- Document APIs with OpenAPI annotations (`@Operation`, `@ApiResponses`, `@Tag`)
- Use DTOs for request/response (e.g., `TodoResponse`)

### Security

- OAuth2 Resource Server with JWT
- Use `@PreAuthorize` for method-level security
- Custom permission evaluator: `hasPermission('Resource','action')`
- Swagger endpoints are permitted without auth (`/api-docs/**`, `/swagger-ui/**`)

### Testing Conventions

- **BDD Tests:** Cucumber with Spanish step definitions (`@Dado`, `@Cuando`, `@Entonces`, `@Y`)
- Test location: `src/test/java/es/um/example/demo/steps/`
- Feature files: `src/test/resources/features/`
- Use `@SpringBootTest` with `@AutoConfigureMockMvc` for integration tests
- Test classes extend `GenericStepsDefinition` for shared utilities

Example step definition:
```java
@Dado("un usuario autenticado {string} con permiso {string}")
public void un_usuario_autenticado(String user, String scope) {
    enableUserToken(user, scope);
}
```

### Domain-Driven Design

- **Domain Model:** Pure Java classes in `domain/model/`
- **Ports:** Interfaces in `domain/port/` (e.g., `TareaRepository`)
- **Adapters:** Implementations in `adapter/persistence/` and `adapter/rest/`
- **Application Layer:** Query/Command handlers in `application/`

### OpenAPI / Swagger

- Use `@Tag`, `@Operation`, `@ApiResponses`, `@ApiResponse`
- Include `schema` definitions for DTOs
- Swagger UI available at `/swagger-ui.html`

### Dependencies to Add

When adding new dependencies, follow the existing patterns in `pom.xml`:
- Spring Boot starters for new features
- Keep test scope separate from compile scope
- Use Bill of Materials (BOM) for version management where available

---

## OpenSpec Workflow

This project uses OpenSpec for change management:

```bash
openspec init           # Initialize project proposal
openspec new           # Create new change
openspec apply         # Implement change tasks
openspec verify        # Verify implementation
openspec archive       # Archive completed change
```

---

## VSCode Extensions (Recommended)

- Extension Pack for Java
- Spring Boot Extension Pack
- Cucumber (Gherkin) support

---

## Notes for Agents

- Always run `./mvnw test` before submitting changes
- Keep the hexagonal architecture boundaries clear
- Maintain Spanish naming conventions for domain concepts
- Document new REST endpoints with OpenAPI annotations
- Add Cucumber scenarios for new API features
