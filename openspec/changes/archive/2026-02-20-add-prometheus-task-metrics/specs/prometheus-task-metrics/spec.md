## ADDED Requirements

### Requirement: Task Creation Metrics
The system SHALL increment a Prometheus counter metric named `tasks_created_total` each time a task creation domain event is published.

#### Scenario: Task successfully created
- **WHEN** a task is created and the TaskCreated domain event is published
- **THEN** the `tasks_created_total` counter SHALL be incremented by 1

### Requirement: Task Completion Metrics
The system SHALL increment a Prometheus counter metric named `tasks_completed_total` each time a task completion domain event is published.

#### Scenario: Task successfully completed
- **WHEN** a task is marked as completed and the TaskCompleted domain event is published
- **THEN** the `tasks_completed_total` counter SHALL be incremented by 1

### Requirement: Task Completion Ratio Metrics
The system SHALL expose a Prometheus gauge metric named `tasks_completion_ratio` that calculates the ratio of completed tasks to created tasks as a percentage.

#### Scenario: Ratio calculation
- **WHEN** the metrics endpoint is queried
- **THEN** the `tasks_completion_ratio` gauge SHALL return (tasks_completed_total / tasks_created_total) * 100, or 0 if no tasks created

### Requirement: Metrics Exposure
The system SHALL expose all task metrics through the Spring Boot Actuator Prometheus endpoint at `/actuator/prometheus`.

#### Scenario: Metrics endpoint access
- **WHEN** a Prometheus scraper queries `/actuator/prometheus`
- **THEN** the response SHALL include the task-related metrics with proper formatting</content>
<parameter name="filePath">openspec/changes/add-prometheus-task-metrics/specs/prometheus-task-metrics/spec.md