# AGENTS.md - IA Rest Project

## Project Overview

- **Project name**: demo (IA Rest)
- **Type**: Spring Boot REST API with CQRS pattern
- **Java version**: 17
- **Build tool**: Maven (with mvnw wrapper)
- **Description**: Demonstration project for AI coding agents, implementing a TODO task management API with CQRS, HATEOAS, OpenAPI documentation, and OAuth2 security.

## Technology Stack

- Spring Boot 4.0.2
- Spring Data JPA (HSQLDB in-memory)
- Spring Security OAuth2 Resource Server
- Spring HATEOAS
- Springdoc OpenAPI (Swagger UI)
- Micrometer + Prometheus (metrics)
- Cucumber 7.34.2 (BDD tests)
- JUnit 5 (unit tests)
- JaCoCo (code coverage)

## Build Commands

```bash
# Build the project
./mvnw clean package -DskipTests

# Run the application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run unit tests only (excludes Cucumber)
./mvnw test -Dcucumber.filter.tags="not @integration"

# Run a single test class
./mvnw test -Dtest=TareaTest

# Run a single test method
./mvnw test -Dtest=TareaTest#fechaDebeIncluirHora

# Run Cucumber BDD tests
./mvnw test -Dcucumber.plugin="pretty"

# Run with code coverage report
./mvnw test
# Report available at: target/site/jacoco/index.html

# Clean build
./mvnw clean
```

## Project Structure

```
src/main/java/es/um/example/demo/
├── adapter/
│   ├── persistence/    # JPA repositories, entities
│   └── rest/          # REST controllers, model assemblers
├── application/
│   ├── command/       # Command handlers (CQRS write)
│   ├── query/         # Query handlers (CQRS read)
│   └── dto/           # Data transfer objects
├── config/            # Spring configuration classes
├── domain/
│   ├── model/         # Domain entities, events, enums
│   └── port/         # Repository interfaces (ports)
└── DemoApplication.java
```

## Code Style Guidelines

### Naming Conventions

- **Classes**: PascalCase (e.g., `TodoController`, `TareaRepository`)
- **Methods**: camelCase (e.g., `obtenerTareas`, `completarTarea`)
- **Variables**: camelCase
- **Packages**: lowercase, dot-separated (e.g., `es.um.example.demo.domain.model`)
- **Constants**: UPPER_SNAKE_CASE
- **Test classes**: Same as production + `Test` suffix (e.g., `TareaTest`)
- **Test methods**: Spanish descriptive names (e.g., `fechaDebeIncluirHora`)

### Java Conventions

- **4 spaces** for indentation (no tabs)
- **Max line length**: 120 characters
- **Imports**: Fully qualified, no wildcard imports unless explicit
- **Braces**: K&R style (opening brace on same line)
- **Fields**: Private with getters/setters (no Lombok)
- **Constructors**: Use constructor injection (no @Autowired on fields)
- **Records**: Use for immutable DTOs where appropriate

### Error Handling

- Return `ResponseEntity.notFound().build()` for 404s
- Use `@Valid` on request bodies for validation
- Leverage `@ControllerAdvice` for global exception handling
- Use `Optional` for nullable returns in domain logic

### Annotations Usage

- `@RestController` for REST endpoints
- with specific HTTP methods (` `@RequestMapping`@GetMapping`, `@PostMapping`, `@PatchMapping`)
- `@PreAuthorize` for method-level security
- Use Spring annotations over Jakarta where available
- OpenAPI: Use `@Operation`, `@ApiResponse`, `@Tag`, `@SecurityRequirement`

### REST API Patterns

- Use HATEOAS: Wrap responses in `EntityModel<T>` and `CollectionModel<T>`
- Use model assemblers (`TodoModelAssembler`) to build links
- Consistent response formats via DTOs
- Use proper HTTP status codes (200, 201, 204, 400, 401, 403, 404)

### Testing Guidelines

- **Unit tests**: JUnit 5 with `@Test`, assertions from `org.junit.jupiter.api.Assertions`
- **BDD tests**: Cucumber with Given-When-Then in `.feature` files
- **Test location**: `src/test/java` mirroring `src/main/java` structure
- **Test resources**: `src/test/resources/features/*.feature`
- Use `@AuthenticationPrincipal Jwt jwt` in controller tests for security context

### Security

- OAuth2 Resource Server with JWT
- Use `@PreAuthorize("hasPermission(...)")` for authorization
- Define permissions in `DemoPermissionEvaluator`
- Scope-based: `Tareas:read`, `Tareas:write`

### Database

- HSQLDB in-memory for development/testing
- JPA entities in `adapter.persistence`
- Repository interfaces (ports) in `domain.port`
- Use Spring Data JPA patterns

### Documentation

- OpenAPI/Swagger available at `/swagger-ui.html`
- Actuator metrics at `/actuator/prometheus`
- Use `@Tag`, `@Operation`, `@ApiResponse` for API docs
- Document security requirements per endpoint

## Important Files

- `pom.xml`: Maven configuration, dependencies
- `src/main/resources/application.properties`: App configuration
- `src/test/resources/application-test.properties`: Test configuration
- `src/test/resources/cucumber.properties`: Cucumber settings
